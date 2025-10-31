# Jira Metrics

A small Java tool to generate a CSV report of Jira tickets and their cycle time (in days) for specified Jira issues and states.

This project queries Jira (via the Jira REST Java Client), builds ticket state timelines from issue changelogs, and computes cycle time restricted to a set of "valid" states (e.g., the states that correspond to active work). Output is a CSV with one line per ticket.

## Requirements
- JDK 21+
- Maven 3.x
- Jira API token with permission to read issue changelogs

## Configuration
You'll need to make the following adjustments to the Main.java file:

```java
String jiraURL = "";
String username = "";
String apiToken = "";
```

Tweak the JQL query to select the issues you want to analyse:

```java
String jqlQuery = "";
```

And finally, set the states of your Jira board that map to an "in progress" state. For example:

```java
LinkedList<String> validStateNames = new LinkedList<>();
validStateNames.add("Board Column Name");
```

👉 This is so hacky! Yes, I hear you. It's a quick and dirty tool for internal use. I'd be delighted to accept contributions to make it more flexible. Volunteers?

## Implementation notes and limitations
- The configurations are hard coded in the Main file (see above)
- The output is a CSV with columns: `ID, Type, Summary, Cycle Time (days)`
- Cycle time is computed by summing durations (in hours) of all states whose names appear in the configured `validStateNames` list, and dividing hours by 24 to give days

## Next steps
- Externalise configuration (e.g., via a properties file or command line arguments)
- Expand this application to gather team metrics from Github (e.g., PR throughput)