package za.scratch.game.common.models.config;

import lombok.Data;

@Data
public class WinCombinations {
    private Double reward_multiplier;
    private String when;
    private Integer count;
    private String group;
}
