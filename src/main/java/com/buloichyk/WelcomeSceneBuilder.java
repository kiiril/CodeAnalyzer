package com.buloichyk;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.nio.file.Path;

public class WelcomeSceneBuilder {
    private final MainSceneBuilder mainSceneController;

    public WelcomeSceneBuilder(MainSceneBuilder mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    public Scene build(Stage stage) {
        VBox vBox = new VBox();
        Label title = new Label("Welcome to Code Analyzer!");
        title.getStyleClass().add("title");
        VBox.setMargin(title, new Insets(100, 30, 40, 30));

        Text description = new Text("Small application that analyze your code on complexity and detect parts \nthat are not follow common naming conventions.");
        description.setTextAlignment(TextAlignment.CENTER);
        description.getStyleClass().add("description");
        VBox.setMargin(description, new Insets(0, 0, 40, 0));

        Button chooseDirectoryButton = new Button("Choose directory");
        chooseDirectoryButton.setOnAction(action -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose directory");
            Path directory = directoryChooser.showDialog(stage).toPath();
            MethodComplexityListProvider.analyzeCodeBase(directory);
            stage.setScene(mainSceneController.build());
        });
        VBox.setMargin(chooseDirectoryButton, new Insets(0, 0, 100, 0));
        chooseDirectoryButton.getStyleClass().add("button");

        vBox.getChildren().addAll(title, description, chooseDirectoryButton);
        vBox.setAlignment(Pos.CENTER);
        Scene welcomeScene = new Scene(vBox);
        welcomeScene.getStylesheets().add(getClass().getResource("welcome.css").toExternalForm());
        return welcomeScene;
    }
}
