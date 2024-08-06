package org.example;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ScoreboardServiceImpl implements ScoreboardService {

    private final MatchRepository matchRepository;

    @Override
    public String startMatch(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        checkMatchIsNotStartedAlready(homeTeam, awayTeam);
        matchRepository.addMatch(match);
        return match.getName();
    }

    private void checkMatchIsNotStartedAlready(String homeTeam, String awayTeam) {


        if (matchRepository.getMatchByTeam(homeTeam).or(() -> matchRepository.getMatchByTeam(awayTeam)).isPresent()) {
            throw new MatchHasBeenAlreadyStartedException();
        }
    }

    @Override
    public void updateScore(String matchName, Score score) {

        Match match = matchRepository.getMatchByTeam(matchName).orElseThrow(MatchNonExistsException::new);
        match.updateScore(score);
        matchRepository.updateMatch(match);
    }

    @Override
    public void finishMatch(String match) {
        matchRepository.removeMatch(match);

    }

    @Override
    public List<Match> getMatchesSummary() {
        return null;
    }

    @Override
    public Score getActualScore(String match) {
        return matchRepository.getMatchByTeam(match).stream().findAny().map(Match::getScore).orElseThrow(MatchNonExistsException::new);
    }

}
