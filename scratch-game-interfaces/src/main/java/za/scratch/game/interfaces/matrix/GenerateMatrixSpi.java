package za.scratch.game.interfaces.matrix;

import za.scratch.game.common.models.matrix.Matrix;
import za.scratch.game.common.models.matrix.MatrixRequest;

public interface GenerateMatrixSpi {

    Matrix generateMatrix(MatrixRequest request);
}
