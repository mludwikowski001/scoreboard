package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

        Score newScore = new Score(2,0);

        assertThrows(MatchNonExistsException.class, () -> scoreboardService.updateScore("match", newScore));

        scoreboardService.updateScore("match", newScore);

    }

}