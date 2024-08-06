package org.example;

import java.util.List;

public interface ScoreboardService {

    String startMatch(String homeTeam, String AwayTeam);

    void updateScore(String match, Score score);

    void finishMatch(String match);

    Score getActualScore(String match);

    List<Match> showScoreboard();
}
