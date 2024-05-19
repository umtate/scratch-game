package za.scratch.game.service;


import lombok.AllArgsConstructor;
import za.scratch.game.common.models.config.ConfigurationInput;
import za.scratch.game.common.models.matrix.Matrix;
import za.scratch.game.common.models.matrix.MatrixRequest;
import za.scratch.game.common.models.output.OutPutWithLoss;
import za.scratch.game.common.models.output.OutPutWithWin;
import za.scratch.game.common.models.output.Output;
import za.scratch.game.common.models.rewards.Rewards;
import za.scratch.game.common.models.rewards.RewardsRequest;
import za.scratch.game.interfaces.input.ConfigurationInputSpi;
import za.scratch.game.interfaces.matrix.GenerateMatrixSpi;
import za.scratch.game.interfaces.rewards.AssignRewardsSpi;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
public class ScratchGameService {

    private ConfigurationInputSpi configurationInputSpi;
    private GenerateMatrixSpi generateMatrixSpi;
    private AssignRewardsSpi rewardsSpi;

    private Matrix getMatrix(ConfigurationInput config) {
        var matrixRequest = MatrixRequest.builder()
                .columns(config.getColumns())
                .rows(config.getRows())
                .probabilitySymbols(config.getProbabilities())
                .build();

        return generateMatrixSpi.generateMatrix(matrixRequest);
    }

    private Rewards getRewards(Matrix matrix, ConfigurationInput config, Double betAmount) {
        var rewardsRequest = RewardsRequest
                .builder()
                .matrix(matrix)
                .winCombinations(config.getWin_combinations())
                .symbols(config.getSymbols())
                .betAmount(betAmount)
                .build();

        return rewardsSpi.assign(rewardsRequest);
    }

    private Output getOutput(Rewards rewards, Matrix matrix) {
        return Objects.equals(rewards.rewards(), "0.0")
                ?
                OutPutWithLoss.builder()
                        .matrix(matrix.value())
                        .reward(rewards.rewards())
                        .build()
                :
                OutPutWithWin.builder()
                        .matrix(matrix.value())
                        .reward(rewards.rewards())
                        .applied_bonus_symbol(rewards.appliedBonusSymbol())
                        .applied_winning_combinations(rewards.appliedWinningCombinations())
                        .build();

    }
    public Output start(String filePath, Double betAmount) throws Exception {
       requireNonNull(filePath, "GameService: File path cannot be null");
       requireNonNull(betAmount, "GameService: Bet amount cannot be null");

       var config = configurationInputSpi.convertInputToConfiguration(filePath);

       var matrix = getMatrix(config);

       var rewards = getRewards(matrix, config, betAmount);

       return getOutput(rewards, matrix);
    }

}
