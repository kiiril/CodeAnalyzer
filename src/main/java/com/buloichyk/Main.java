package com.buloichyk;

import javafx.application.Application;
import javafx.stage.Stage;

/**
* This is the main class where the JavaFX application starts.
**/
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new WelcomeSceneBuilder(new MainSceneBuilder()).build(stage));
        stage.setTitle("Code Analyzer");
        stage.show();
    }
}