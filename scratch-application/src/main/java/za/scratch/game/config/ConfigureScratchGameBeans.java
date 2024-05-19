package za.scratch.game.config;

import lombok.NoArgsConstructor;
import za.scratch.game.interfaces.input.ConfigurationInputSpi;
import za.scratch.game.interfaces.matrix.GenerateMatrixSpi;
import za.scratch.game.interfaces.rewards.AssignRewardsSpi;

import java.util.ServiceLoader;

@NoArgsConstructor
public class ConfigureScratchGameBeans {

    public static ConfigurationInputSpi getConfigurationInputSpi() {
        return ServiceLoader.load(ConfigurationInputSpi .class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .findFirst()
                .orElseThrow();
    }

    public static GenerateMatrixSpi getGenerateMatrixSpi() {
        return ServiceLoader.load(GenerateMatrixSpi.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .findFirst()
                .orElseThrow();
    }

    public static AssignRewardsSpi getAssignRewardsSpi() {
        return ServiceLoader.load(AssignRewardsSpi.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .findFirst()
                .orElseThrow();
    }

}
