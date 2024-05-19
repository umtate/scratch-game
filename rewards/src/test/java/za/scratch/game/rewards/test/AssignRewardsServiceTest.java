package za.scratch.game.rewards.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.scratch.game.interfaces.rewards.AssignRewardsSpi;
import za.scratch.game.rewards.AssignRewardsService;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

public class AssignRewardsServiceTest {


    @Test
    void assignRewardsServiceTest_implementsAssignRewardsSpi() {
        assertTrue(AssignRewardsSpi.class
                .isAssignableFrom(AssignRewardsService.class));
    }

    @Test
    void assignRewardsServiceTest_canLoadServiceLoader() {
        var service = ServiceLoader.load(AssignRewardsSpi.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .findFirst();

        service.ifPresentOrElse(
                s -> assertInstanceOf(AssignRewardsService.class, s),
                () -> Assertions.fail("AssignRewardsService not in service loader")
        );

    }
}
