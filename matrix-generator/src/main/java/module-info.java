module matrix.generator {
    requires transitive scratch.game.interfaces;

    uses za.scratch.game.interfaces.matrix.GenerateMatrixSpi;

    provides
            za.scratch.game.interfaces.matrix.GenerateMatrixSpi
    with
            za.scratch.game.matrix.generator.MatrixGeneratorService;

}