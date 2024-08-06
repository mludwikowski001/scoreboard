package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ScoreboardServiceTest {

    public static final String HOME_TEAM = "homeTeam";
    public static final String AWAY_TEAM = "awayTeam";

    public static final String MATCH_NAME = HOME_TEAM+AWAY_TEAM;
    @Mock
    MatchRepository matchRepository = new MatchRepositoryImpl();

    @InjectMocks
    private ScoreboardService scoreboardService = new ScoreboardServiceImpl(matchRepository);

    @BeforeEach
    void reset(){
        Mockito.reset(matchRepository);
    }
    @Test
    void startCorrectMatch(){

        String result = scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);
        assertThat(result).isNotBlank().isEqualTo(MATCH_NAME);

    }

    @Test
    void startOngoingMatch(){

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        assertThrows(MatchHasBeenAlreadyStartedException.class, () ->scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM));
    }

    @Test
    void updateOngoingMatchScore(){

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        Score newScore = new Score(2,0);

        scoreboardService.updateScore(HOME_TEAM, newScore);

        Score actualScore = scoreboardService.getActualScore(HOME_TEAM);

        assertThat(actualScore).isNotNull().satisfies(as ->{
            assertThat(as.getHomeTeamScore()).isEqualTo(newScore.getHomeTeamScore());
            assertThat(as.getAwayTeamScore()).isEqualTo(newScore.getAwayTeamScore());
        });

    }

    @Test
    void updateAlreadyFinishedMatch(){

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);
        scoreboardService.finishMatch(HOME_TEAM);

        Score newScore = new Score(2,0);

        assertThrows(MatchNonExistsException.class, () -> scoreboardService.updateScore(MATCH_NAME, newScore));


    }

    @Test
    void finishOngoingMatch(){

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        scoreboardService.finishMatch(HOME_TEAM);

        Mockito.verify(matchRepository).removeMatch(HOME_TEAM);

    }

    @Test
    void finishAlreadyFinishedMatch(){

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        scoreboardService.finishMatch(HOME_TEAM);

        assertThrows(MatchNonExistsException.class, () -> scoreboardService.finishMatch(MATCH_NAME));

    }

}