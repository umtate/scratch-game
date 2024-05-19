package za.scratch.game.common.models.rewards;

import lombok.Builder;
import za.scratch.game.common.models.config.Symbols;
import za.scratch.game.common.models.config.WinCombinations;
import za.scratch.game.common.models.matrix.Matrix;

import java.util.Map;

@Builder
public record RewardsRequest(Map<String, WinCombinations> winCombinations,
                             Matrix matrix,
                             Map<String, Symbols> symbols,
                             Double betAmount) {
}
