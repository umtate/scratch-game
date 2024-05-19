package za.scratch.game.rewards.base;

import za.scratch.game.common.models.rewards.RewardsRequest;
import za.scratch.game.rewards.models.RewardsHolder;
import za.scratch.game.rewards.models.Winnings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static za.scratch.game.rewards.base.AssignRewardsUtils.*;

public class AssignRewardBaseClass {

    private static final String APPLIED_BONUS_MISS = "MISS";

    protected String getBonusSymbol(RewardsRequest rewardsRequest) {
        var flatMatrixList = rewardsRequest.matrix().value()
                .stream().flatMap(List::stream)
                .toList();

        return filterForBonusSymbol(rewardsRequest.symbols(), flatMatrixList);
    }


    protected Winnings getWinnings(RewardsRequest rewardsRequest) {
        Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
        Map<String, Double> winningValue = new HashMap<>();
        Map<String, List<Double>> symbolValues = new HashMap<>();

        addNormalWinnings(rewardsRequest, winningValue, symbolValues, appliedWinningCombinations);
        addHorizontalWinnings(rewardsRequest, symbolValues, appliedWinningCombinations);
        addVerticalWinnings(rewardsRequest,symbolValues,appliedWinningCombinations);

        return Winnings.builder()
                .winningValue(winningValue)
                .appliedWinningCombinations(appliedWinningCombinations)
                .symbolWinnings(symbolValues)
                .build();
    }

    protected double getRewards(RewardsRequest rewardsRequest, Winnings winnings, String appliedBonusSymbol) {
        var rewardsHolder = RewardsHolder
                .builder()
                .winnings(winnings)
                .winCombinations(rewardsRequest.winCombinations())
                .betAmount(rewardsRequest.betAmount())
                .build();


        var rewards = calculateRewards(rewardsHolder);
        rewards = appliedBonusSymbol.equals(APPLIED_BONUS_MISS) ? rewards :
                factorBonus(appliedBonusSymbol, rewards, rewardsRequest.symbols());

        return rewards;
    }
}
