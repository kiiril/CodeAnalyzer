package com.buloichyk;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

public class MainSceneBuilder {
    public Scene build() {
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(new ComplexityEvaluatorTab(), new StyleCheckerTab());
        // Bind the tabMaxWidth and tabMinWidth to a portion of the TabPane's width
        tabPane.tabMaxWidthProperty().bind(tabPane.widthProperty().divide(2));
        tabPane.tabMinWidthProperty().bind(tabPane.widthProperty().divide(2));

//        HBox hbox = new HBox(tabPane);
//        // Set margins for the HBox to create space on the left and right
//        hbox.setPadding(new Insets(0, 20, 0, 20)); // 20 pixels of padding on the left and right

        // allow using css for styling
        Scene mainScene = new Scene(tabPane);
        mainScene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        return mainScene;
    }

    public void badmethodLOL() {}
    public void ReallyBadOne() {}
}
