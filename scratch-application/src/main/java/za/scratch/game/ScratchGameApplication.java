package za.scratch.game;

import org.apache.commons.cli.*;
import za.scratch.game.service.ScratchGameService;

import static za.scratch.game.config.ConfigureScratchGameBeans.*;

public class ScratchGameApplication {

    public static void main(String[] args) throws Exception {

        Options options = new Options();
        options.addOption("c", "config", true, "Config file");
        options.addOption("b", "betting-amount", true, "Betting amount");

        var inputSpi = getConfigurationInputSpi();
        var matrixSpi = getGenerateMatrixSpi();
        var rewardsSpi = getAssignRewardsSpi();

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            String configFile = cmd.getOptionValue("config");
            System.out.println("Config file: " + configFile);

            String bettingAmount = cmd.getOptionValue("betting-amount");
            System.out.println("Betting amount: " + bettingAmount);

            var scratchGameService = new ScratchGameService(inputSpi, matrixSpi, rewardsSpi);
            var output = scratchGameService.start(configFile, Double.valueOf(bettingAmount));

            System.out.println(output);

        } catch (ParseException e) {
            System.err.println("Error parsing command line arguments: " + e.getMessage());

            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar <your-jar-file>", options);
        }

    }
}
