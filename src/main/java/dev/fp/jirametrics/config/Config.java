package dev.fp.jirametrics.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@ConfigurationProperties(prefix="app")
@Getter
@Setter
public class Config {
    private String jiraURL;
    private String username;
    private String apiToken;
    private String jql;
    private LinkedList<String> validStateNames;
//
//    public Config(String jiraURL, String username, String apiToken, String jql, LinkedList<String> validStateNames) {
//        this.jiraURL = jiraURL;
//        this.username = username;
//        this.apiToken = apiToken;
//        this.jql = jql;
//        this.validStateNames = validStateNames;
//    }
//
//    public String getJiraURL() {
//        return jiraURL;
//    }
//
//    public void setJiraURL(String jiraURL) {
//        this.jiraURL = jiraURL;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getApiToken() {
//        return apiToken;
//    }
//
//    public void setApiToken(String apiToken) {
//        this.apiToken = apiToken;
//    }
//
//    public String getJql() {
//        return jql;
//    }
//
//    public void setJql(String jql) {
//        this.jql = jql;
//    }
//
//    public LinkedList<String> getValidStateNames() {
//        return validStateNames;
//    }
//
//    public void setValidStateNames(LinkedList<String> validStateNames) {
//        this.validStateNames = validStateNames;
//    }
}
