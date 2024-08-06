package org.example;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
@Getter
public class Match {
    @NotBlank
    @Size(min=1)
    private final String homeTeam;

    @NotBlank
    @Size(min=1)
    private final String awayTeam;

    private final Instant startTime = Instant.now();

    private Score score  = new Score(0,0);

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public void updateScore(Score score){
        this.score=score;
    }

    public String getName(){
        return homeTeam+awayTeam;
    }

    public int getTotalScore(){
        return score.getHomeTeamScore() + score.getAwayTeamScore();
    }
}
