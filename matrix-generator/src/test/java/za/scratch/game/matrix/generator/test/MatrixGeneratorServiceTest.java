package za.scratch.game.matrix.generator.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import za.scratch.game.common.models.matrix.Matrix;
import za.scratch.game.common.models.matrix.MatrixRequest;
import za.scratch.game.interfaces.matrix.GenerateMatrixSpi;
import za.scratch.game.matrix.generator.MatrixGeneratorService;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MatrixGeneratorServiceTest {

    @InjectMocks
    private MatrixGeneratorService service;

    @Test
    void matrixGeneratorServiceTest_implementsGenerateMatrixSpiSpi(){
        assertTrue(GenerateMatrixSpi.class
                .isAssignableFrom(MatrixGeneratorService.class));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void whenGenerate_andMatrixRequestIsNull_throwException(){
        MatrixRequest request = null;

        var error = assertThrows(NullPointerException.class,
                () -> service.generateMatrix(request)
        );

        assertEquals("MatrixGeneratorService: matrixRequest cannot be null", error.getMessage());
    }

    @Test
    void whenGenerate_andMatrixRequestIsNotNull_returnMatrix(){
        var matrixRequest = mock(MatrixRequest.class);

        var matrix = service.generateMatrix(matrixRequest);

        assertNotNull(matrix);
        assertInstanceOf(Matrix.class, matrix);
    }

    @Test
    void canLoadServiceLoader() {
        var service = ServiceLoader.load(GenerateMatrixSpi.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .findFirst();

        service.ifPresentOrElse(
                s -> assertInstanceOf(MatrixGeneratorService.class, s),
                () -> Assertions.fail("MatrixGeneratorService not in service loader")
        );
    }

}
