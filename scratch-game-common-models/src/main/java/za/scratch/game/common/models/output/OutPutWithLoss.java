package za.scratch.game.common.models.output;

import lombok.Builder;

import java.util.List;

@Builder
public record OutPutWithLoss(List<List<String>> matrix,
                             String reward) implements Output {
}
