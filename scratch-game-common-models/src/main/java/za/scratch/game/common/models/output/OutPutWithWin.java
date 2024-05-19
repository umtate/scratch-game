package za.scratch.game.common.models.output;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record OutPutWithWin(List<List<String>> matrix,
                            String reward,
                            String applied_bonus_symbol,
                            Map<String, List<String>> applied_winning_combinations) implements Output {
}
