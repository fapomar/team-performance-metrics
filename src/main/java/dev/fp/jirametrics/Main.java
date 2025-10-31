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

        // JIRA 'resolution' field (date) cannot be trusted in my Jira instance because it was only setup corretly in 23/04/2024
        String jqlQuery = "project = <MYPROJECT> AND type in (Bug, Story, Task) AND Status=Done AND updated >= YYYY-MM-DD AND updated <= YYYY-MM-DD";

        // Gives flexibility to consider specific states (e.g., maintaining backward compatibility with old boards' states)
        LinkedList<String> validStateNames = new LinkedList<>();
        validStateNames.add("COLUMN NAME");
        validStateNames.add("ANOTHER COLUMN NAME");
        Report report = jiraService.getReport(jqlQuery, validStateNames);

        System.out.println(report);
    }
}
