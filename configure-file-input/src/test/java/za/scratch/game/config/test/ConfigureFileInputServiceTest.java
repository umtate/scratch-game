package za.scratch.game.config.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.scratch.game.common.models.config.ConfigurationInput;
import za.scratch.game.configure.input.ConfigureFileInputService;
import za.scratch.game.interfaces.input.ConfigurationInputSpi;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;


public class ConfigureFileInputServiceTest {

    private final String filePath = "src/test/resources/config.json";

    private final ConfigureFileInputService configureFileInputService  = new ConfigureFileInputService();


    @Test
    void configurationFileInputService_implementsConfigurationInputSpi() {
            assertTrue(ConfigurationInputSpi.class
                    .isAssignableFrom(ConfigureFileInputService.class));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void whenConvertInput_andUrlIsNull_thenThrow() {
        String filePath = null;

        var error = assertThrows(NullPointerException.class,
                () -> configureFileInputService.convertInputToConfiguration(filePath)
        );

        assertTrue(error.getMessage().contains("ConfigureFileInputService: File path cannot be null"));
    }

    @Test
    void whenValidFilePath_thenConvertInput() throws Exception {

        var configurationInput = configureFileInputService.convertInputToConfiguration(filePath);

        assertNotNull(configurationInput);
        assertInstanceOf(ConfigurationInput.class, configurationInput);
    }

    @Test
    void canLoadServiceLoader() {
        var service = ServiceLoader.load(ConfigurationInputSpi.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .findFirst();

        service.ifPresentOrElse(
                s -> assertInstanceOf(ConfigureFileInputService.class, s),
                () -> Assertions.fail("ConfigureFileInputService not in service loader")
        );
    }


}
