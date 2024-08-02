package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ScoreboardServiceTest {

    private ScoreboardService scoreboardService = new ScoreboardServiceImpl();
    @Test
    void startCorrectMatch(){

        boolean result = scoreboardService.startMatch("homeTeam", "awayTeam");
        assertThat(result).isTrue();

    }

}