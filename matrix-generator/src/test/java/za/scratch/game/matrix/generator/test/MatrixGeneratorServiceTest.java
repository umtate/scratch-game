package za.scratch.game.matrix.generator.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.scratch.game.interfaces.matrix.GenerateMatrixSpi;
import za.scratch.game.matrix.generator.MatrixGeneratorService;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixGeneratorServiceTest {

    @Test
    void matrixGeneratorServiceTest_implementsGenerateMatrixSpiSpi(){
        assertTrue(GenerateMatrixSpi.class
                .isAssignableFrom(MatrixGeneratorService.class));
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
