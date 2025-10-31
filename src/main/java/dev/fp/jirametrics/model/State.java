package dev.fp.jirametrics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.Hours;

@Setter
@Getter
@AllArgsConstructor
public class State {
    private String name;
    private DateTime inTimestamp;
    private DateTime outTimestamp;

    public Integer getDurationInHours() {
        if (inTimestamp == null || outTimestamp == null) {
            return null;
        }
        return Hours.hoursBetween(inTimestamp, outTimestamp).getHours();
    }
}
