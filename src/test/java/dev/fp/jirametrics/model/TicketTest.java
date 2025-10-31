package dev.fp.jirametrics.model;

import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TicketTest {

    private LinkedList<String> validStates;

    @BeforeEach
    public void setUp() {
        validStates = new LinkedList<>();
        validStates.add("E-CODING");
        validStates.add("E-PULLREQUESTED");
        validStates.add("E-QAING");
    }

    private Ticket generateMockTicket() {
        State coding = new State("E-CODING", DateTime.parse("2025-03-17T09:39:06.835Z"), DateTime.parse("2025-03-19T07:15:33.070Z"));
        State pr = new State("E-PULLREQUESTED", DateTime.parse("2025-03-19T07:15:33.070Z"), DateTime.parse("2025-03-19T18:25:33.789Z"));
        State qa = new State("E-QAING", DateTime.parse("2025-03-19T18:25:33.789Z"), DateTime.parse("2025-03-20T05:04:35.606Z"));

        Ticket ticket = new Ticket();

        LinkedList<State> allStates = new LinkedList<>();
        allStates.add(coding);
        allStates.add(pr);
        allStates.add(qa);

        ticket.setAllStates(allStates);

        return ticket;
    }

    @Test
    public void testGetCycleTimeInDays_ValidStates() {
        Ticket ticket = this.generateMockTicket();
        assertEquals(2, ticket.getCycleTimeInDays(validStates));
    }

    @Test
    public void testGetCycleTimeInHours_ValidStates() {
        Ticket ticket = this.generateMockTicket();
        assertEquals(66, ticket.getCycleTimeInHours(validStates));
    }

    @Test
    public void testGetCycleTimeInDays_NoValidStates() {
        Ticket ticket = new Ticket();
        assertNull(ticket.getCycleTimeInDays(null));
    }

    @Test
    public void testGetCycleTimeInHours_NoValidStates() {
        Ticket ticket = new Ticket();
        assertNull(ticket.getCycleTimeInHours(null));
    }

}