package za.scratch.game.common.models.output;

import lombok.Builder;
import za.scratch.game.common.models.matrix.Matrix;

import java.util.List;
import java.util.Map;

@Builder
public record Output(Matrix matrix,
                     String reward,
                     String applied_bonus_symbol,
                     Map<String, List<String>> applied_winning_combinations
) { }
