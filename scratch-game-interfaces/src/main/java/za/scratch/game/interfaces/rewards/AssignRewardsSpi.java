package za.scratch.game.interfaces.rewards;

import za.scratch.game.common.models.rewards.Rewards;
import za.scratch.game.common.models.rewards.RewardsRequest;

public interface AssignRewardsSpi {

    Rewards assign(RewardsRequest rewardsRequest);
}
