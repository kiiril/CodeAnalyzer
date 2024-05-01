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

import java.util.stream.Collectors;

public class StyleCheckerTab extends Tab {
    private Timeline timeline;

    public StyleCheckerTab() {
        super("Style Checker");
        HBox hBox = new HBox();
        Pane progressCircle = makeProgressCircle();
        ListView<String> listView = makeListView();

        progressCircle.prefWidthProperty().bind(hBox.widthProperty().divide(2));
        listView.prefWidthProperty().bind(hBox.widthProperty().divide(2));

        hBox.getChildren().addAll(progressCircle, listView);

        setContent(hBox);
        setOnSelectionChanged(event -> {
            timeline.play();
        });
        setClosable(false);
    }

    public Pane makeProgressCircle() {
        Pane pane = new Pane();
        // Create a circle representing the progress bar
        Arc arc = new Arc(260, 350, 150, 150, 90, 180);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.ORANGE);
        arc.setStrokeWidth(15);
        arc.setFill(null);
        arc.lengthProperty().set(0);

        System.out.println("Total: " + MethodComplexityListProvider.getMethodComplexityListSize());
        System.out.println("Satisfied: " + MethodComplexityListProvider.getCountOfMethodsWithCamelCaseNames());
        double percentage = MethodComplexityListProvider.getPercentageOfMethodsWithCamelCaseNames();
        double coverage = percentage * 360 / 100;
        System.out.println("Coverage: " + coverage);
//         Create a timeline animation to fill the arc
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(arc.lengthProperty(), 0)),
                new KeyFrame(Duration.seconds(2 ), new KeyValue(arc.lengthProperty(), coverage))
        );

        Label label = new Label(String.format("%.2f", percentage) + "%");
        label.setFont(new Font(24));

//        label.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
//            // Calculate label position based on the center of the arc
//            double labelX = arc.getCenterX() - newBounds.getWidth() / 2;
//            double labelY = arc.getCenterY() - newBounds.getHeight() / 2;
//
//            // Set label position
//            label.setLayoutX(labelX);
//            label.setLayoutY(labelY);
//        });

        // Position the label at the center of the arc
        label.layoutXProperty().bind(arc.centerXProperty().subtract(label.widthProperty().divide(2)));
        label.layoutYProperty().bind(arc.centerYProperty().subtract(label.heightProperty().divide(2)));


        Label title = new Label("CamelCase Compliance");
        title.setFont(new Font(30));
        // Bind the label's layout X coordinate to the arc's centerX, subtracting half of the label's width
        title.layoutXProperty().bind(arc.centerXProperty().subtract(title.widthProperty().divide(2)));

        // Bind the label's layout Y coordinate to the arc's centerY, subtracting the arc's radiusY, the label's height, and an additional offset
        double offset = 30; // Adjust this value to control the vertical spacing between the arc and the label
        title.layoutYProperty().bind(arc.centerYProperty().subtract(arc.radiusYProperty()).subtract(title.heightProperty()).subtract(offset));

        pane.getChildren().addAll(arc, label, title);

        return pane;
    }

    // FIXME change this method
    public ListView<String> makeListView() {
        return new ListView<>(FXCollections.observableArrayList(
                MethodComplexityListProvider.getMethodsWithNonCamelCaseNames().stream()
                        .map(m -> m.getMethodName() + "❗")
                        .toList()));
    }
}
