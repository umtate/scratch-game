package za.scratch.game.common.models.config;

import lombok.Data;

import java.util.List;


@Data
public class ProbabilitySymbols {
    private List<StandardSymbols> standard_symbols;
    private BonusSymbols bonus_symbols;
}
