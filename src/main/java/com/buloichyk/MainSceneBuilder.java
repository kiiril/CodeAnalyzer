package com.buloichyk;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;

/**
 * This class is responsible for constructing the main scene of the application,
 * which consists of two tabs. It manages the connection between these tabs.
 **/
public class MainSceneBuilder {
    public Scene build() {
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(new ComplexityEvaluatorTab(), new StyleCheckerTab());
        // divide width of the TabPane between two tabs
        tabPane.tabMaxWidthProperty().bind(tabPane.widthProperty().divide(2));
        tabPane.tabMinWidthProperty().bind(tabPane.widthProperty().divide(2));

        Scene mainScene = new Scene(tabPane);
        // allow using css for styling
        mainScene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        return mainScene;
    }
}
