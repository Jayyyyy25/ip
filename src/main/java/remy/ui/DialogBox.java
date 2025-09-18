package remy.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        Circle clip = new Circle(
                displayPicture.getFitWidth() / 2,
                displayPicture.getFitHeight() / 2,
                Math.min(displayPicture.getFitWidth(), displayPicture.getFitHeight()) / 2
        );
        displayPicture.setClip(clip);
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getRemyDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    /**
     * Displays the given message with a typing effect, revealing one character at a time with a specified delay.
     *
     * @param message The message to be displayed with the typing effect.
     * @param delayMillis The delay in milliseconds between each character reveal.
     */
    public void showTypingEffect(String message, int delayMillis) {
        dialog.setText(""); // clear label first

        Timeline timeline = new Timeline();
        final int[] index = {0};

        KeyFrame keyFrame = new KeyFrame(Duration.millis(delayMillis), e -> {
            if (index[0] < message.length()) {
                dialog.setText(dialog.getText() + message.charAt(index[0]));
                index[0]++;
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(message.length());
        timeline.play();
    }
}

