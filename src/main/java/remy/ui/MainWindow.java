package remy.ui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Remy remy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image remyImage = new Image(this.getClass().getResourceAsStream("/images/Remy.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Remy instance */
    public void setRemy(Remy r) {
        remy = r;
        String welcomeMessage = remy.getWelcome();
        dialogContainer.getChildren().addAll(
                DialogBox.getRemyDialog(welcomeMessage, remyImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Remy's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = remy.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getRemyDialog(response, remyImage)
        );
        if (input.trim().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
        userInput.clear();
    }
}


