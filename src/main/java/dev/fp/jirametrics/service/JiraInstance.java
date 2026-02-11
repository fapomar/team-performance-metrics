package dev.fp.jirametrics.service;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.ChangelogGroup;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import dev.fp.jirametrics.model.Report;
import dev.fp.jirametrics.model.State;
import dev.fp.jirametrics.model.Ticket;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;

@AllArgsConstructor
public class JiraInstance implements JiraService, AutoCloseable {
    private JiraRestClient client;

    public JiraInstance(String jiraURL, String username, String apiToken) {
        URI jiraURI = URI.create(jiraURL);
        this.client = new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(jiraURI, username, apiToken);
    }

    @Override
    public void close() throws IOException {
        System.out.println("Closing Jira client");
        client.close();
    }

    private Ticket transform(Issue jiraIssue) {

        System.out.printf("Processing %s%n", jiraIssue.getKey());

        // 1. Process basic issue info
        Ticket ticket = new Ticket();
        ticket.setId(jiraIssue.getKey());
        ticket.setSummary(jiraIssue.getSummary().replaceAll(",", ""));
        ticket.setType(jiraIssue.getIssueType().getName());

        // WARNING: Story Points is a custom field; remove the following line if not present in your Jira Instance
        ticket.setEstimate((Double)jiraIssue.getFieldByName("Story Points").getValue());

        // 2. Process "transitions" so we can create "states" later
        LinkedList<JiraInstance.Transition> transitions = new LinkedList<>();

        try {
            Issue detailedIssue = client.getIssueClient().getIssue(jiraIssue.getKey(), Collections.singleton(IssueRestClient.Expandos.CHANGELOG)).get();
            Iterable<ChangelogGroup> changelog = detailedIssue.getChangelog();

            if (changelog != null) {

                changelog.forEach(group -> {

                    group.getItems().forEach(item -> {

                        // Each changelog transition contains FromState, Time, ToState
                        // We're only interested in one type of transition ("status")
                        if (item.getField().equals("status")) {

                            String fromState = item.getFromString();
                            String toState = item.getToString();
                            DateTime timestamp = group.getCreated();

                            Transition transition = new Transition(fromState, toState, timestamp);

                            transitions.add(transition);
                        }
                    });
                });
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        // 3. Process "transitions" to create "allStates"
        LinkedList<State> allStates = new LinkedList<>();

        // sorts the transitions by timestamp (ascending); needed because Jira changelog is in descending order
        transitions.sort((t1, t2) -> t1.getTimestamp().compareTo(t2.getTimestamp()));

        transitions.forEach(transition -> {

            boolean isLast = transitions.getLast().equals(transition);

            if (!isLast) {
                Transition nextTransition = transitions.get(transitions.indexOf(transition) + 1);

                String stateName = transition.getToState();
                DateTime inTimestamp = transition.getTimestamp();
                DateTime outTimestamp = nextTransition.getTimestamp();

                State state = new State(stateName, inTimestamp, outTimestamp);

                allStates.add(state);
            }

            // Hack alert! The only way to establish "done date" is to retrieve the timestamp of last state transition
            if (isLast) {
                DateTime timestamp = transition.getTimestamp();
                ticket.setLastDoneDate(timestamp.toString("yyyy-MM-dd"));
            }

        });

        ticket.setAllStates(allStates);

        return ticket;
    }

    public Report getReport(String query, LinkedList<String> validStateNames) throws Exception {
        LinkedList<Ticket> tickets = new LinkedList<>();

        SearchResult issues = this.client.getSearchClient().searchJql(query).get();

        issues.getIssues().forEach(jiraIssue -> {
            Ticket ticket = transform(jiraIssue);
            tickets.add(ticket);
        });

        Report report = new Report(tickets, validStateNames);

        return report;
    }

    /**
     * Anonymous Inner class used to manipulate Jira annoying states!
     **/
    @Setter
    @Getter
    @AllArgsConstructor
    class Transition {
        private String fromState;
        private String toState;
        private DateTime timestamp;

    }

}
