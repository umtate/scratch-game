package za.scratch.game.matrix.generator;

import za.scratch.game.common.models.matrix.Matrix;
import za.scratch.game.common.models.matrix.MatrixRequest;
import za.scratch.game.interfaces.matrix.GenerateMatrixSpi;

import static java.util.Objects.requireNonNull;
import static za.scratch.game.matrix.generator.utils.MatrixUtils.createMatrix;

public class MatrixGeneratorService implements GenerateMatrixSpi {


    @Override
    public Matrix generateMatrix(MatrixRequest matrixRequest) {
        requireNonNull(matrixRequest, "MatrixGeneratorService: matrixRequest cannot be null");

        var blankMatrix = createMatrix(matrixRequest);

        return Matrix.builder().matrix(blankMatrix).build();
    }
}
