package org.example;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.PositiveOrZero;
@Value
@AllArgsConstructor
public class Score {
    @PositiveOrZero Integer homeTeamScore;
    @PositiveOrZero Integer awayTeamScore;
}
