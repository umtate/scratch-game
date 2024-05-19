package za.scratch.game.rewards.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import za.scratch.game.common.models.config.Symbols;
import za.scratch.game.common.models.config.WinCombinations;
import za.scratch.game.common.models.matrix.Matrix;
import za.scratch.game.common.models.rewards.Rewards;
import za.scratch.game.common.models.rewards.RewardsRequest;
import za.scratch.game.interfaces.rewards.AssignRewardsSpi;
import za.scratch.game.rewards.AssignRewardsService;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class AssignRewardsServiceTest {
    private static final Double BET_AMOUNT = 250.0;
    private static final Double REWARD_MULTIPLIER = 2.0;
    private static final String TYPE = "standard";

    @InjectMocks
    AssignRewardsService service;

    @Test
    void assignRewardsServiceTest_implementsAssignRewardsSpi() {
        assertTrue(AssignRewardsSpi.class
                .isAssignableFrom(AssignRewardsService.class));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void whenAssign_andAssignRewardsRequestIsNull_throwException(){
        RewardsRequest request = null;

        var error = assertThrows(NullPointerException.class,
                () -> service.assign(request));

        assertEquals("AssignRewardsService: rewardsRequest cannot be null", error.getMessage());
    }

    @Test
    void whenAssign_andAssignRewardsRequestIsNotNull_returnRewards(){
        List<List<String>> values = List.of(List.of("A","C","D"), List.of("E","F","F"), List.of("D","E","C"));
        var matrix = Matrix.builder().value(values).build();
        var symbols = Symbols.builder()
                .reward_multiplier(REWARD_MULTIPLIER).type(TYPE).build();
        var winCombination = WinCombinations.builder()
                .reward_multiplier(REWARD_MULTIPLIER).when("same_symbols").count(3).group("same_symbols").build();
        var request = RewardsRequest.builder()
                .betAmount(BET_AMOUNT)
                .matrix(matrix)
                .symbols(Map.of("A", symbols, "B", symbols))
                .winCombinations(Map.of("same_symbol_3_times",winCombination))
                         .build();

        var rewards = service.assign(request);

        assertNotNull(rewards);
        assertInstanceOf(Rewards.class, rewards);
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
