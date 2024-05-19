module scratch.game.common.models {
    requires lombok;

    exports
            za.scratch.game.common.models.config
    to
            scratch.game.interfaces,
            configure.file.input,
            scratch.game.service,
            matrix.generator,
            scratch.rewards,
            com.google.gson;

    exports
            za.scratch.game.common.models.matrix
    to
            scratch.game.interfaces,
            matrix.generator,
            scratch.rewards,
            scratch.game.service;

    exports
            za.scratch.game.common.models.rewards
    to
            scratch.game.interfaces,
            scratch.rewards,
            scratch.game.service;

    exports
            za.scratch.game.common.models.output
    to
            scratch.game.service;

    opens
            za.scratch.game.common.models.config
    to
            com.google.gson;
}