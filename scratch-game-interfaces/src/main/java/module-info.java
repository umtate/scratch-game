module scratch.game.interfaces {
    requires transitive scratch.game.common.models;

    exports
            za.scratch.game.interfaces.input
    to
            configure.file.input,
            scratch.game.service;

    exports
            za.scratch.game.interfaces.matrix
    to
            matrix.generator,
            scratch.game.service;

    exports
            za.scratch.game.interfaces.rewards
    to
            scratch.rewards,
            scratch.game.service;
}