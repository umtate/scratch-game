package za.scratch.game.rewards.models;

import lombok.Builder;
import za.scratch.game.common.models.config.WinCombinations;

import java.util.Map;

@Builder
public record RewardsHolder(Winnings winnings,
                            Map<String, WinCombinations> winCombinations,
                            Double betAmount,
                            String appliedBonusSymbol) {
}
