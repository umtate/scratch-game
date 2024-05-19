package za.scratch.game.common.models.matrix;

import lombok.Builder;
import za.scratch.game.common.models.config.ProbabilitySymbols;

@Builder
public record MatrixRequest(Integer columns,
                            Integer rows,
                            ProbabilitySymbols probabilitySymbols) { }
