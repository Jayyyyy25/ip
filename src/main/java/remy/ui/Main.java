package remy.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import remy.exception.RemyException;

/**
 * Main class for running the GUI of Remy application
 */
public class Main extends Application {
    private static final String DEFAULT_FILEPATH = "./data/remy.txt";
    private Remy remy;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            remy = new Remy(DEFAULT_FILEPATH);
            fxmlLoader.<MainWindow>getController().setRemy(remy);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
