package za.scratch.game.matrix.generator;

import za.scratch.game.common.models.matrix.Matrix;
import za.scratch.game.common.models.matrix.MatrixRequest;
import za.scratch.game.interfaces.matrix.GenerateMatrixSpi;
import za.scratch.game.matrix.generator.base.MatrixGeneratorBaseClass;

import static java.util.Objects.requireNonNull;

public class MatrixGeneratorService extends MatrixGeneratorBaseClass implements GenerateMatrixSpi {

    @Override
    public Matrix generateMatrix(MatrixRequest matrixRequest) {
        requireNonNull(matrixRequest, "MatrixGeneratorService: matrixRequest cannot be null");

        var generatedMatrix = createMatrix(matrixRequest);

        return Matrix.builder().value(generatedMatrix).build();
    }
}
