package dev.fp.jirametrics;

import dev.fp.jirametrics.config.Config;
import dev.fp.jirametrics.model.Report;
import dev.fp.jirametrics.service.JiraInstance;
import dev.fp.jirametrics.service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JiraMetricsRunner implements CommandLineRunner {
    @Autowired
    private Config config;

    public static void main(String[] args) {
        SpringApplication.run(JiraMetricsRunner.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        JiraService jiraService = new JiraInstance(config.getJiraURL(), config.getUsername(), config.getApiToken());
        Report report = jiraService.getReport(config.getJql(), config.getValidStateNames());
        System.out.println(report);
    }
}
