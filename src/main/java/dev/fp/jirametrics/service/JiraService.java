package dev.fp.jirametrics.service;

import dev.fp.jirametrics.model.Report;

import java.util.LinkedList;

public interface JiraService {
    void close() throws Exception;

    Report getReport(String jqlQuery, LinkedList<String> validStateNames) throws Exception;
}
