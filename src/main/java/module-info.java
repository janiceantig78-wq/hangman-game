module fr.quentincillierre.hangman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;

    // Allows JavaFX to load your views and controllers
    exports fr.quentincillierre.hangman.application;
    opens fr.quentincillierre.hangman.controller to javafx.fxml;
}
