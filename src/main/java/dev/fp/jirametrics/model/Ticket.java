package dev.fp.jirametrics.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class Ticket {
    private String id;
    private String summary;
    private String type;
    private LinkedList<State> allStates = new LinkedList<>();

    public Integer getCycleTimeInDays(LinkedList<String> validStateNames) {
        Integer cycleTimeInHours = getCycleTimeInHours(validStateNames);

        if (cycleTimeInHours == null) {
            return null;
        }

        return cycleTimeInHours / 24;
    }

    Integer getCycleTimeInHours(LinkedList<String> validStateNames) {
        if (allStates == null || allStates.isEmpty()) {
            return null;
        }

        Integer cycleTimeInHours = 0;
        boolean checkStates = (validStateNames != null && !validStateNames.isEmpty());

        for (State state : allStates) {

            if (checkStates && validStateNames.contains(state.getName())) {
                cycleTimeInHours += state.getDurationInHours();
            }

        }

        return cycleTimeInHours;
    }
}
