package za.scratch.game.rewards;

import za.scratch.game.common.models.rewards.Rewards;
import za.scratch.game.common.models.rewards.RewardsRequest;
import za.scratch.game.interfaces.rewards.AssignRewardsSpi;
import za.scratch.game.rewards.base.AssignRewardBaseClass;

import static java.util.Objects.requireNonNull;


public class AssignRewardsService extends AssignRewardBaseClass implements AssignRewardsSpi {

    @Override
    public Rewards assign(RewardsRequest rewardsRequest) {
        requireNonNull(rewardsRequest, "AssignRewardsService: rewardsRequest cannot be null");

        var winnings = getWinnings(rewardsRequest);

        var appliedBonusSymbol = getBonusSymbol(rewardsRequest);

        var rewards = getRewards(rewardsRequest, winnings, appliedBonusSymbol);

        return Rewards.builder()
                .appliedWinningCombinations(winnings.appliedWinningCombinations())
                .appliedBonusSymbol(appliedBonusSymbol)
                .rewards(String.valueOf(rewards))
                .build();
    }

}
