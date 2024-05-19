package za.scratch.game.interfaces.input;

import za.scratch.game.common.models.config.ConfigurationInput;

public interface ConfigurationInputSpi {

    ConfigurationInput convertInputToConfiguration(String url) throws Exception;

}
