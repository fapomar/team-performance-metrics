package dev.fp.jirametrics.model;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StateTest {

    @Test
    public void testGetDurationInHours_ValidTimestamps() {
        DateTime inTimestamp = DateTime.parse("2025-03-17T09:39:06.835Z");
        DateTime outTimestamp = DateTime.parse("2025-03-19T07:15:33.070Z");
        State state = new State("E-CODING", inTimestamp, outTimestamp);

        Integer expectedHours = 45; // Calculated manually
        assertEquals(expectedHours, state.getDurationInHours());
    }

    @Test
    public void testGetDurationInHours_NullInTimestamp() {
        DateTime outTimestamp = DateTime.parse("2025-03-19T07:15:33.070Z");
        State state = new State("E-CODING", null, outTimestamp);

        assertNull(state.getDurationInHours());
    }

    @Test
    public void testGetDurationInHours_NullOutTimestamp() {
        DateTime inTimestamp = DateTime.parse("2025-03-17T09:39:06.835Z");
        State state = new State("E-CODING", inTimestamp, null);

        assertNull(state.getDurationInHours());
    }

    @Test
    public void testGetDurationInHours_NullTimestamps() {
        State state = new State("E-CODING", null, null);

        assertNull(state.getDurationInHours());
    }
}