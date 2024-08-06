package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ScoreboardServiceTest {

    public static final String HOME_TEAM = "homeTeam";
    public static final String AWAY_TEAM = "awayTeam";

    public static final String HOME_TEAM_2 = "homeTeam2";
    public static final String AWAY_TEAM_2 = "awayTeam2";

    public static final String MATCH_NAME = HOME_TEAM + AWAY_TEAM;
    @Mock
    MatchRepository matchRepository = new MatchRepositoryImpl();

    @InjectMocks
    private ScoreboardService scoreboardService = new ScoreboardServiceImpl(matchRepository);

    @BeforeEach
    void reset() {
        Mockito.reset(matchRepository);


    }

    @Test
    void startCorrectMatch() {

        String result = scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);
        assertThat(result).isNotBlank().isEqualTo(MATCH_NAME);

    }

    @Test
    void startOngoingMatch() {

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        assertThrows(MatchHasBeenAlreadyStartedException.class, () -> scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM));
    }

    @Test
    void updateOngoingMatchScore() {

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        Score newScore = new Score(2, 0);

        scoreboardService.updateScore(HOME_TEAM, newScore);

        Score actualScore = scoreboardService.getActualScore(HOME_TEAM);

        assertThat(actualScore).isNotNull().satisfies(as -> {
            assertThat(as.getHomeTeamScore()).isEqualTo(newScore.getHomeTeamScore());
            assertThat(as.getAwayTeamScore()).isEqualTo(newScore.getAwayTeamScore());
        });

    }

    @Test
    void updateAlreadyFinishedMatch() {

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);
        scoreboardService.finishMatch(HOME_TEAM);

        Score newScore = new Score(2, 0);

        assertThrows(MatchNonExistsException.class, () -> scoreboardService.updateScore(HOME_TEAM, newScore));


    }

    @Test
    void finishOngoingMatch() {

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        scoreboardService.finishMatch(HOME_TEAM);

        List<Match> scoreboard = scoreboardService.showScoreboard();

        assertThat(scoreboard).isNotNull().isEmpty();

    }

    @Test
    void finishAlreadyFinishedMatch() {

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        scoreboardService.finishMatch(HOME_TEAM);

        assertThrows(MatchNonExistsException.class, () -> scoreboardService.finishMatch(HOME_TEAM));

    }

    @Test
    void showEmptyScoreboard() {

        List<Match> scorebaord = scoreboardService.showScoreboard();

        assertThat(scorebaord).isNotNull().isEmpty();
    }

    @Test
    void showScoreboardWithSingleMatch() {

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        List<Match> scorebaord = scoreboardService.showScoreboard();

        assertThat(scorebaord).isNotNull().hasSize(1).allSatisfy(m -> {
            assertThat(m.getHomeTeam()).isEqualTo(HOME_TEAM);
            assertThat(m.getAwayTeam()).isEqualTo(AWAY_TEAM);
            assertThat(m.getScore()).isNotNull().satisfies(s -> {
                assertThat(s.getHomeTeamScore()).isZero();
                assertThat(s.getAwayTeamScore()).isZero();
            });
        });
    }

    @Test
    void showScoreboardWithTwoMatchesTheSameScore() {

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        scoreboardService.startMatch(HOME_TEAM_2, AWAY_TEAM_2);

        List<Match> scorebaord = scoreboardService.showScoreboard();

        assertThat(scorebaord).isNotNull().hasSize(2).first().satisfies(m -> {
            assertThat(m.getHomeTeam()).isEqualTo(HOME_TEAM_2);
            assertThat(m.getAwayTeam()).isEqualTo(AWAY_TEAM_2);
            assertThat(m.getScore()).isNotNull().satisfies(s -> {
                assertThat(s.getHomeTeamScore()).isZero();
                assertThat(s.getAwayTeamScore()).isZero();
            });
        });

        assertThat(scorebaord).isNotNull().hasSize(2).last().satisfies(m -> {
            assertThat(m.getHomeTeam()).isEqualTo(HOME_TEAM);
            assertThat(m.getAwayTeam()).isEqualTo(AWAY_TEAM);
            assertThat(m.getScore()).isNotNull().satisfies(s -> {
                assertThat(s.getHomeTeamScore()).isZero();
                assertThat(s.getAwayTeamScore()).isZero();
            });
        });
    }


    @Test
    void showScoreboardWithTwoMatchesUpdatedScore() {

        scoreboardService.startMatch(HOME_TEAM, AWAY_TEAM);

        scoreboardService.startMatch(HOME_TEAM_2, AWAY_TEAM_2);

        scoreboardService.updateScore(HOME_TEAM, new Score(2,0));

        List<Match> scorebaord = scoreboardService.showScoreboard();

        assertThat(scorebaord).isNotNull().hasSize(2).first().satisfies(m -> {
            assertThat(m.getHomeTeam()).isEqualTo(HOME_TEAM);
            assertThat(m.getAwayTeam()).isEqualTo(AWAY_TEAM);
            assertThat(m.getScore()).isNotNull().satisfies(s -> {
                assertThat(s.getHomeTeamScore()).isEqualTo(2);
                assertThat(s.getAwayTeamScore()).isZero();
            });
        });

        assertThat(scorebaord).isNotNull().hasSize(2).last().satisfies(m -> {
            assertThat(m.getHomeTeam()).isEqualTo(HOME_TEAM_2);
            assertThat(m.getAwayTeam()).isEqualTo(AWAY_TEAM_2);
            assertThat(m.getScore()).isNotNull().satisfies(s -> {
                assertThat(s.getHomeTeamScore()).isZero();
                assertThat(s.getAwayTeamScore()).isZero();
            });
        });
    }


}