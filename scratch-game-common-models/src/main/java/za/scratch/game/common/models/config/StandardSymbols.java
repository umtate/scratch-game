package za.scratch.game.common.models.config;

import lombok.Data;

import java.util.Map;

@Data
public class StandardSymbols {
        private int column;
        private int row;
        private Map<String, Integer> symbols;

}
