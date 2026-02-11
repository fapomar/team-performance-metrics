package dev.fp.jirametrics.model;

import java.util.LinkedList;

/**
 * Report class to generate a CSV report of tickets.
 * Future implementation might contain different types of reports and export types
 */
public class Report {
    private LinkedList<Ticket> tickets;
    private LinkedList<String> validStateNames;

    public Report(LinkedList<Ticket> tickets, LinkedList<String> validStateNames) {
        this.tickets = tickets;
        this.validStateNames = validStateNames;
    }

    public String getCVS() {
        return this.toString();
    }

    @Override
    public String toString() {
        StringBuilder csv = new StringBuilder();
        csv.append("ID, Done, Type, Summary, Cycle Time (days), Story Points\n");
        tickets.forEach(ticket -> {
            csv.append(ticket.getId()).append(",");
            csv.append(ticket.getLastDoneDate()).append(",");
            csv.append(ticket.getType()).append(",");
            csv.append(ticket.getSummary()).append(",");
            csv.append(ticket.getCycleTimeInDays(this.validStateNames)).append(",");
            csv.append(ticket.getEstimate());
            csv.append("\n");
        });
        return csv.toString();
    }

}
