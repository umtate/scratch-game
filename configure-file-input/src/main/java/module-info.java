import za.scratch.game.interfaces.input.ConfigurationInputSpi;

module configure.file.input {
    requires scratch.game.interfaces;

    requires com.google.gson;
    requires lombok;

    uses ConfigurationInputSpi;

    provides
            ConfigurationInputSpi
    with
            za.scratch.game.configure.input.ConfigureFileInputService;
}