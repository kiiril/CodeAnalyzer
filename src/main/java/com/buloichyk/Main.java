package com.buloichyk;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.nio.file.Path;
public class Main extends Application {
    private final MainSceneController mainSceneController = new MainSceneController();
    private Stage stage;
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setScene(buildWelcomeScene());
        stage.setTitle("Code analyzer");
        stage.show();
    }

    public Scene buildWelcomeScene() {
        VBox vBox = new VBox();
        Label label = new Label("Welcome!");
        Button chooseDirectoryButton = new Button("Choose directory");
        chooseDirectoryButton.setOnAction(action -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose file");
            Path directory = directoryChooser.showDialog(stage).toPath();
            mainSceneController.analyzeCodeBase(directory);
            stage.setScene(mainSceneController.buildMainScene());
        });
        vBox.getChildren().addAll(label, chooseDirectoryButton);
        return new Scene(vBox);
    }
}