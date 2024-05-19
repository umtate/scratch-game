package za.scratch.game.common.models.rewards;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record Rewards(String rewards,
                      String appliedBonusSymbol,
                      Map<String, List<String>> appliedWinningCombinations) {
}
