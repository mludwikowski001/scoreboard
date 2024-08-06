package org.example;

import java.util.Optional;

public interface MatchRepository {

    void addMatch(Match match);

    void updateMatch(Match match);

    void removeMatch(String match);

    Optional<Match> getMatchByTeam(String team);




}
