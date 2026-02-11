package dev.fp.jirametrics.config;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class JiraClientConfig {

    @Bean(destroyMethod = "close")
    public JiraRestClient jiraRestClient(Config config) {
        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        return factory.createWithBasicHttpAuthentication(
                URI.create(config.getJiraURL()),
                config.getUsername(),
                config.getApiToken()
        );
    }
}