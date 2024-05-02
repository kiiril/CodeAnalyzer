package com.buloichyk;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
* This class provides a graphical representation of code style analysis.
* There are 2 main components:
*   1. Autofill Circle -> represents the percentage of methods that follow CamelCase naming convention
*   2. List -> displays names of methods that do not follow CamelCase naming convention
**/
public class StyleCheckerTab extends Tab {
    private Timeline timeline;
    public StyleCheckerTab() {
        super("Style Checker");
        HBox hBox = new HBox();
        
        Pane complianceCircle = makeComplianceCircle();
        ListView<String> listView = makeListView();

        // distribute tab width between components
        complianceCircle.prefWidthProperty().bind(hBox.widthProperty().divide(2));
        listView.prefWidthProperty().bind(hBox.widthProperty().divide(2));

        hBox.getChildren().addAll(complianceCircle, listView);

        setContent(hBox);
        // play the animation when the user selects the tab
        setOnSelectionChanged(event -> timeline.play());
        setClosable(false);
    }

    public Pane makeComplianceCircle() {
        Pane pane = new Pane();
        Arc arc = new Arc(260, 350, 150, 150, 90, 180);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.valueOf("#F7941D"));
        arc.setStrokeWidth(15);
        arc.setFill(null);
        arc.lengthProperty().set(0);

        double percentage = MethodComplexityListProvider.getPercentageOfMethodsWithCamelCaseNames();
        double arcLength = percentage * 360 / 100;

        // animation of fulfillment
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(arc.lengthProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(arc.lengthProperty(), arcLength))
        );

        Label percentageLabel = new Label(String.format("%.2f", percentage) + "%");
        percentageLabel.setFont(new Font(24));

        // position the label at the center of the arc
        percentageLabel.layoutXProperty().bind(arc.centerXProperty().subtract(percentageLabel.widthProperty().divide(2)));
        percentageLabel.layoutYProperty().bind(arc.centerYProperty().subtract(percentageLabel.heightProperty().divide(2)));

        Label title = new Label("CamelCase Compliance");
        title.setFont(new Font(30));

        title.layoutXProperty().bind(arc.centerXProperty().subtract(title.widthProperty().divide(2)));
        double offset = 30;
        title.layoutYProperty().bind(arc.centerYProperty().subtract(arc.radiusYProperty()).subtract(title.heightProperty()).subtract(offset));

        pane.getChildren().addAll(arc, percentageLabel, title);

        return pane;
    }

    public ListView<String> makeListView() {
        return new ListView<>(FXCollections.observableArrayList(
                MethodComplexityListProvider.getMethodsWithNonCamelCaseNames().stream()
                        .map(m -> m.getMethodName() + "❗")
                        .toList()));
    }
}
