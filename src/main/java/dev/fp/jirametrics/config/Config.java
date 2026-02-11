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
}
