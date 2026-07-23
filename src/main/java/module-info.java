module fr.quentincillierre.hangman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media; // FIXED: Authorizes audio playback features completely
    requires java.base;

    exports fr.quentincillierre.hangman.application;

    opens fr.quentincillierre.hangman.controller to javafx.fxml;
}
