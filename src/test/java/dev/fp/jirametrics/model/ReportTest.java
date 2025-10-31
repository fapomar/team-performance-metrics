package dev.fp.jirametrics.model;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportTest {

    @Test
    public void testGetCVS_EmptyTickets() {
        LinkedList<Ticket> tickets = new LinkedList<>();
        Report report = new Report(tickets, null);

        String expectedCSV = "ID, Type, Summary, Cycle Time (days)\n";
        assertEquals(expectedCSV, report.getCVS());
    }

    @Test
    public void testGetCVS_WithTickets() {
        State coding = new State("E-CODING", DateTime.parse("2025-03-17T09:39:06.835Z"), DateTime.parse("2025-03-19T07:15:33.070Z"));
        State pr = new State("E-PULLREQUESTED", DateTime.parse("2025-03-19T07:15:33.070Z"), DateTime.parse("2025-03-19T18:25:33.789Z"));
        State qa = new State("E-QAING", DateTime.parse("2025-03-19T18:25:33.789Z"), DateTime.parse("2025-03-20T19:04:35.606Z"));

        Ticket ticket1 = new Ticket();
        ticket1.setId("1");
        ticket1.setType("Bug");
        ticket1.setSummary("Fix issue");

        LinkedList<State> allStates1 = new LinkedList<State>();
        allStates1.add(coding);
        allStates1.add(pr);

        ticket1.setAllStates(allStates1);

        Ticket ticket2 = new Ticket();
        ticket2.setId("2");
        ticket2.setType("Story");
        ticket2.setSummary("Add new feature");

        LinkedList<State> allStates2 = new LinkedList<State>();
        allStates2.add(coding);
        allStates2.add(pr);
        allStates2.add(qa);

        ticket2.setAllStates(allStates2);

        LinkedList<Ticket> tickets = new LinkedList<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        LinkedList<String> validStateNames = new LinkedList<>();
        validStateNames.add("E-CODING");
        validStateNames.add("E-PULLREQUESTED");
        validStateNames.add("E-QAING");

        Report report = new Report(tickets, validStateNames);

        String expectedCSV = "ID, Type, Summary, Cycle Time (days)\n" +
                "1,Bug,Fix issue,2\n" +
                "2,Story,Add new feature,3\n";
        assertEquals(expectedCSV, report.getCVS());
    }

}
