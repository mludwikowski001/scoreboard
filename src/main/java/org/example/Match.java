package org.example;

import lombok.Getter;

import java.time.Instant;
@Getter
public class Match {

    private final String homeTeam;

    private final String awayTeam;

    private final Instant startTime = Instant.now();

    private final Score score  = new Score(0,0);

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
}
