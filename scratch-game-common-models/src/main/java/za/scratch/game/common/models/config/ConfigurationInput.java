package za.scratch.game.common.models.config;


import lombok.Data;

import java.util.Map;

@Data
public class ConfigurationInput {
    private Integer columns;
    private Integer rows;
    private Map<String, Symbols> symbols;
    private ProbabilitySymbols probabilities;
    private Map<String, WinCombinations> win_combinations;
}
