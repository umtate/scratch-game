package za.scratch.game.common.models.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Symbols {
    private Double reward_multiplier;
    private String type;
    private Double extra;
    private String impact;
}
