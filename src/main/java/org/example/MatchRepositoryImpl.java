package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class MatchRepositoryImpl implements MatchRepository{

    private final List<Match> matchList = new ArrayList<>();
    @Override
    public void addMatch(Match match) {

        matchList.add(match);
    }

    @Override
    public void updateMatch(Match match) {
        matchList.removeIf(m ->m.getName().equalsIgnoreCase(match.getName()));
        addMatch(match);

    }

    @Override
    public void removeMatch(String team) {
        Match match = getMatchByTeam(team).orElseThrow(MatchNonExistsException::new);
        matchList.remove(match);

    }

    @Override
    public Optional<Match> getMatchByTeam(String team) {
        return matchList.stream().filter(match -> match.getHomeTeam().equalsIgnoreCase(team) || match.getAwayTeam().equalsIgnoreCase(team)).findAny();
    }

    @Override
    public List<Match> showMatchesOrderedByTotalScoreAndStartTime() {
        return matchList.stream().sorted((Comparator.comparing(Match::getTotalScore).reversed()).thenComparing(Comparator.comparing(Match::getStartTime).reversed())).toList();
    }


}
