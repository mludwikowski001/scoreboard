package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ScoreboardServiceTest {

    private ScoreboardService scoreboardService = new ScoreboardServiceImpl();
    @Test
    void startCorrectMatch(){

        boolean result = scoreboardService.startMatch("homeTeam", "awayTeam");
        assertThat(result).isTrue();

    }

    @Test
    void startOngoingMatch(){

        assertThrows(MatchHasBeenAlreadyStartedException.class, () ->scoreboardService.startMatch("homeTeam", "awayTeam"));
    }

    @Test
    void updateOngoingMatchScore(){

        scoreboardService.startMatch("homeTeam", "awayTeam");

        Score newScore = new Score(2,0);

        scoreboardService.updateScore("match", newScore);

        Score actualScore = scoreboardService.getActualScore("match");

        assertThat(actualScore).isNotNull().satisfies(as ->{
            assertThat(as.getHomeTeamScore()).isEqualTo(newScore.getHomeTeamScore());
            assertThat(as.getAwayTeamScore()).isEqualTo(newScore.getAwayTeamScore());
        });

    }

    @Test
    void updateAlreadyFinishedMatch(){

        scoreboardService.startMatch("homeTeam", "awayTeam");
        scoreboardService.finishMatch("match");

        Score newScore = new Score(2,0);

        assertThrows(MatchNonExistsException.class, () -> scoreboardService.updateScore("match", newScore));


    }

    @Test
    void finishOngoingMatchMatch(){

        scoreboardService.startMatch("homeTeam", "awayTeam");

        scoreboardService.finishMatch("match");

        Mockito.verify(scoreboardService).finishMatch("match");

    }

    @Test
    void finishAlreadyFinishedMatch(){

        Score newScore = new Score(2,0);

        assertThrows(MatchNonExistsException.class, () -> scoreboardService.finishMatch("match");

    }

}