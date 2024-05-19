package za.scratch.game.common.models.matrix;

import lombok.Builder;

import java.util.List;

@Builder
public record Matrix(List<List<String>> matrix) { }
