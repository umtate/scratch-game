package za.scratch.game.rewards.models;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record Winnings(Map<String, List<String>> appliedWinningCombinations,
                       Map<String, Double> winningValue,
                       Map<String, List<Double>> symbolWinnings) {
}
