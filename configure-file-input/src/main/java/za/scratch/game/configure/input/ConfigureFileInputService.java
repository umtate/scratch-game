package za.scratch.game.configure.input;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NoArgsConstructor;
import za.scratch.game.common.models.config.ConfigurationInput;
import za.scratch.game.interfaces.input.ConfigurationInputSpi;


import java.io.FileNotFoundException;
import java.io.FileReader;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor
public class ConfigureFileInputService implements ConfigurationInputSpi {

    @Override
    public ConfigurationInput convertInputToConfiguration(String filePath) throws FileNotFoundException {
        requireNonNull(filePath, "ConfigureFileInputService: File path cannot be null");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(new FileReader(filePath), ConfigurationInput.class);
    }

}
