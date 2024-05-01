package com.buloichyk;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new WelcomeSceneBuilder(new MainSceneBuilder()).build(stage));
        stage.setTitle("Code Analyzer");
        stage.show();
    }
}