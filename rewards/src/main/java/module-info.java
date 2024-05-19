module scratch.rewards {
    requires transitive scratch.game.interfaces;
    requires static lombok;

    uses za.scratch.game.interfaces.rewards.AssignRewardsSpi;

    provides
            za.scratch.game.interfaces.rewards.AssignRewardsSpi
    with
            za.scratch.game.rewards.AssignRewardsService;
}