package za.scratch.game.service.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.scratch.game.common.models.config.ConfigurationInput;
import za.scratch.game.common.models.matrix.Matrix;
import za.scratch.game.common.models.matrix.MatrixRequest;
import za.scratch.game.common.models.output.Output;
import za.scratch.game.common.models.rewards.Rewards;
import za.scratch.game.common.models.rewards.RewardsRequest;
import za.scratch.game.interfaces.input.ConfigurationInputSpi;
import za.scratch.game.interfaces.matrix.GenerateMatrixSpi;
import za.scratch.game.interfaces.rewards.AssignRewardsSpi;
import za.scratch.game.service.ScratchGameService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScratchServiceTest {
    private static final String FILE_PATH = "user/somefile.txt";
    private static final Double BET_AMOUNT = 100.0;

    @Mock
    private ConfigurationInputSpi configurationInputSpi;

    @Mock
    private GenerateMatrixSpi generateMatrixSpi;

    @Mock
    private AssignRewardsSpi rewardsSpi;

    @InjectMocks
    private ScratchGameService scratchGameService;

    private void mockConfigInputSpi() throws Exception {
        var configInput = mock(ConfigurationInput.class);
        when(configurationInputSpi.convertInputToConfiguration(anyString()))
                .thenReturn(configInput);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void whenStart_andUrlIsNull_throwException() throws Exception {
      String filePath = null;

      var error = assertThrows(NullPointerException.class, () ->
              scratchGameService.start(filePath, BET_AMOUNT));

      assertEquals("GameService: File path cannot be null", error.getMessage());

    }

    @Test
    void whenStart_andUrlIsNotNull_getConfigFromSpi() throws Exception {
        mockConfigInputSpi();
        when(rewardsSpi.assign(any(RewardsRequest.class)))
                .thenReturn(mock(Rewards.class));

        scratchGameService.start(FILE_PATH, BET_AMOUNT);

        verify(configurationInputSpi)
                .convertInputToConfiguration(anyString());
    }

    @Test
    void whenStart_andConfigInputAvailable_getMatrixFromSpi() throws Exception {
        mockConfigInputSpi();

        when(generateMatrixSpi.generateMatrix(any(MatrixRequest.class)))
                .thenReturn(mock(Matrix.class));
        when(rewardsSpi.assign(any(RewardsRequest.class)))
                .thenReturn(mock(Rewards.class));

        scratchGameService.start(FILE_PATH, BET_AMOUNT);

        InOrder inOrder = inOrder(configurationInputSpi, generateMatrixSpi);

        inOrder.verify(configurationInputSpi)
                .convertInputToConfiguration(anyString());
        inOrder.verify(generateMatrixSpi)
                .generateMatrix(any(MatrixRequest.class));
    }

    @Test
    void whenStart_andMatrixIsGenerated_getRewardsFromSpi() throws Exception {
        mockConfigInputSpi();

        when(generateMatrixSpi.generateMatrix(any(MatrixRequest.class)))
                .thenReturn(mock(Matrix.class));
        when(rewardsSpi.assign(any(RewardsRequest.class)))
                .thenReturn(mock(Rewards.class));

        scratchGameService.start(FILE_PATH, BET_AMOUNT);

        InOrder inOrder = inOrder(configurationInputSpi, generateMatrixSpi, rewardsSpi);

        inOrder.verify(configurationInputSpi)
                .convertInputToConfiguration(anyString());
        inOrder.verify(generateMatrixSpi)
                .generateMatrix(any(MatrixRequest.class));
        inOrder.verify(rewardsSpi)
                .assign(any(RewardsRequest.class));
    }

    @Test
    void whenStart_andMatrixIsGenerated_getOutput() throws Exception {
        mockConfigInputSpi();

        when(generateMatrixSpi.generateMatrix(any(MatrixRequest.class)))
                .thenReturn(mock(Matrix.class));

        when(rewardsSpi.assign(any(RewardsRequest.class)))
                .thenReturn(mock(Rewards.class));

        var output = scratchGameService.start(FILE_PATH, BET_AMOUNT);

        assertNotNull(output);
        assertInstanceOf(Output.class, output);
    }


}
