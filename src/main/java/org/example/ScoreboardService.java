package org.example;

import java.util.List;

public interface ScoreboardService {

    boolean startMatch(String homeTeam, String AwayTeam);

    void updateScore(String match, Score score);

    void finishMatch(String match);

    List<Match> getMatchesSummary();

    Score getActualScore(String match);
}
