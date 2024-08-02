package org.example;

import java.util.List;

public class ScoreboardServiceImpl implements ScoreboardService{
    @Override
    public boolean startMatch(String homeTeam, String AwayTeam) {
        return false;
    }

    @Override
    public void updateScore(String match, Score score) {

    }

    @Override
    public void finishMatch(String match) {

    }

    @Override
    public List<Match> getMatchesSummary() {
        return null;
    }

    @Override
    public Score getActualScore(String match) {
        return null;
    }

}
