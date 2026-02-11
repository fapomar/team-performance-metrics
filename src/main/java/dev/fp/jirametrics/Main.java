package dev.fp.jirametrics;

import dev.fp.jirametrics.service.JiraService;
import lombok.SneakyThrows;
import dev.fp.jirametrics.service.JiraInstance;
import dev.fp.jirametrics.model.Report;

import java.util.LinkedList;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        String jiraURL = "";
        String username = "";
        String apiToken = "";

        JiraService jiraService = new JiraInstance(jiraURL, username, apiToken);

        // WARNING! First date inclusive, second date exclusive (JIRA quirk)
        String jqlQuery = "type in (Bug, Story, Task) AND Status=Done AND resolved >= YYYY-MM-DD AND resolved <= YYYY-MM-DD ORDER BY updatedDate";

        // Gives flexibility to consider specific states (e.g., maintaining backward compatibility with old boards' states)
        LinkedList<String> validStateNames = new LinkedList<>();
        validStateNames.add("In Progress");
        Report report = jiraService.getReport(jqlQuery, validStateNames);

        System.out.println(report);
    }
}
