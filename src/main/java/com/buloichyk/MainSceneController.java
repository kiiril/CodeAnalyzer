package com.buloichyk;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainSceneController {
    private final List<MethodComplexity> methodComplexityList;
    private final GridPane gridPane = new GridPane();
    private Timeline timeline;

    public MainSceneController() {
        this.methodComplexityList = new ArrayList<>();
    }
    public Scene buildMainScene() {
        TabPane tabPane = new TabPane();
        Tab complexityEvaluatorTab = new Tab("Complexity Evaluator");

        BarChart<Number, String> barChart = makeBarChart();
        GridPane.setConstraints(barChart, 0,0);
        gridPane.getChildren().add(barChart);

        TitledPane titledPane = makeTitledPane();
        GridPane.setConstraints(titledPane, 0, 1);
        // places the TitledPane at the top of the cell
        GridPane.setValignment(titledPane, VPos.TOP);
        gridPane.getChildren().add(titledPane);

        TableView<MethodComplexity> tableView = makeTable();
        GridPane.setConstraints(tableView, 1, 1);
        gridPane.getChildren().add(tableView);

        complexityEvaluatorTab.setClosable(false);
        complexityEvaluatorTab.setContent(gridPane);


        Tab styleCheckerTab = new Tab("Style Checker");
        HBox hBox = new HBox();
        Pane progressCircle = makeProgressCircle();
        ListView<String> listView = makeListView();

        progressCircle.prefWidthProperty().bind(hBox.widthProperty().divide(2));
        listView.prefWidthProperty().bind(hBox.widthProperty().divide(2));

        hBox.getChildren().addAll(progressCircle, listView);

        styleCheckerTab.setContent(hBox);
        styleCheckerTab.setOnSelectionChanged(event -> {
            timeline.play();
        });

        styleCheckerTab.setClosable(false);

        tabPane.getTabs().addAll(complexityEvaluatorTab, styleCheckerTab);

        // allow using css for styling
        Scene mainScene = new Scene(tabPane);
        mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return mainScene;
    }

    public void analyzeCodeBase(Path directory) {
        if (Files.isDirectory(directory)) {
            try (Stream<Path> stream = Files.walk(directory)) {
                List<Path> files = stream
                        .filter(file -> file.toString().endsWith(".java"))
                        .toList();
                System.out.println(files);
                for (Path file: files) {
                    CompilationUnit unit = StaticJavaParser.parse(file);
                    FileVisitor fileVisitor = new FileVisitor();
                    fileVisitor.visit(unit, methodComplexityList);
                }
            } catch (IOException e) {
                System.out.println("Exception");
            }
        }
    }

    public BarChart<Number, String> makeBarChart() {
        // FIXME differentiate methods with same name
        methodComplexityList.sort(Comparator.comparingInt(MethodComplexity::getTotalConditionalStatements).reversed());
        List<MethodComplexity> topList = methodComplexityList.subList(0, 3);
        System.out.println(topList);

        PieChart worst = makePieChart(topList.get(0));
        GridPane.setConstraints(worst, 1, 0);
        gridPane.getChildren().add(worst);

        CategoryAxis methodNames = new CategoryAxis();
        methodNames.setCategories(FXCollections.observableArrayList(topList
                .stream()
                .map(MethodComplexity::getMethodName)
                .collect(Collectors.toList())));


        NumberAxis conditionalsCount = new NumberAxis();
        // FIXME make correct ticks
        conditionalsCount.setTickUnit(1);
        conditionalsCount.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number object) {
                if(object.intValue()!=object.doubleValue())
                    return "";
                return ""+(object.intValue());
            }

            @Override
            public Number fromString(String string) {
                Number val = Double.parseDouble(string);
                return val.intValue();
            }
        });
        conditionalsCount.setMinorTickVisible(false);

        BarChart<Number, String> barChart = new BarChart<>(conditionalsCount, methodNames);
        barChart.setTitle("Top 3 Most Complex Methods");

        XYChart.Series<Number, String> dataSeries = new XYChart.Series<>();
        for (MethodComplexity methodComplexity: topList) {
            XYChart.Data<Number, String> dataEntry = new XYChart.Data<>(methodComplexity.getTotalConditionalStatements(), methodComplexity.getMethodName());

            dataEntry.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Wait while node is available to access it
                    dataEntry.getNode().setOnMouseClicked(mouseEvent -> {
                        // remove pie chart of previous method
                        gridPane.getChildren().removeIf(node -> node instanceof PieChart);
                        PieChart pieChart = makePieChart(methodComplexity);
                        GridPane.setConstraints(pieChart, 1, 0);
                        gridPane.getChildren().add(pieChart);
                    });
                }
            });

            dataSeries.getData().add(dataEntry);
        }
        barChart.getData().add(dataSeries);
        return barChart;
    }

    public PieChart makePieChart(MethodComplexity methodComplexity) {
        PieChart pieChart = new PieChart();

        List<PieChart.Data> dataList = new ArrayList<>();
        if (methodComplexity.getIfStatements() > 0) {
            dataList.add(new PieChart.Data("IF", methodComplexity.getIfStatements()));
        }
        if (methodComplexity.getSwitchStatements() > 0) {
            dataList.add(new PieChart.Data("SWITCH", methodComplexity.getSwitchStatements()));
        }
        if (methodComplexity.getForStatements() > 0) {
            dataList.add(new PieChart.Data("FOR", methodComplexity.getForStatements()));
        }
        if (methodComplexity.getWhileStatements() > 0) {
            dataList.add(new PieChart.Data("WHILE", methodComplexity.getWhileStatements()));
        }

        ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList(dataList);
        pieChart.setData(observableList);

        pieChart.setTitle("Total: " + methodComplexity.getTotalConditionalStatements() + methodComplexity.getMethodName());

        pieChart.setLabelLineLength(15);
        return pieChart;
    }

    public TitledPane makeTitledPane() {
        methodComplexityList.sort(Comparator.comparingInt(MethodComplexity::getTotalConditionalStatements).reversed());
        List<MethodComplexity> topList = methodComplexityList.subList(3, methodComplexityList.size());
        ObservableList<String> options = FXCollections.observableArrayList(
                topList.stream()
                        .map(MethodComplexity::getMethodName)
                        .collect(Collectors.toList()));

        ListView<String> listView = new ListView<>(options);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listView.setOnDragDetected(event -> {
            // Start the drag-and-drop gesture
            Dragboard dragboard = listView.startDragAndDrop(TransferMode.COPY);

            // Put the selected items in the dragboard
            List<String> selectedItems = listView.getSelectionModel().getSelectedItems();
            ClipboardContent content = new ClipboardContent();
            content.put(DataFormat.PLAIN_TEXT, String.join("\n", selectedItems));
            dragboard.setContent(content);

            event.consume();
        });

        TitledPane titledPane = new TitledPane("Others", listView);
        titledPane.setCollapsible(true);
        titledPane.setExpanded(false);

        return titledPane;
    }

    public TableView<MethodComplexity> makeTable() {
        TableView<MethodComplexity> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<MethodComplexity, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("methodName"));
        // specify column width for name as 40%
        nameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
        TableColumn<MethodComplexity, Integer> ifColumn = new TableColumn<>("IF");
        ifColumn.setCellValueFactory(new PropertyValueFactory<>("ifStatements"));
        TableColumn<MethodComplexity, Integer> switchColumn = new TableColumn<>("SWITCH");
        switchColumn.setCellValueFactory(new PropertyValueFactory<>("switchStatements"));
        TableColumn<MethodComplexity, Integer> forColumn = new TableColumn<>("FOR");
        forColumn.setCellValueFactory(new PropertyValueFactory<>("forStatements"));
        TableColumn<MethodComplexity, Integer> whileColumn = new TableColumn<>("WHILE");
        whileColumn.setCellValueFactory(new PropertyValueFactory<>("whileStatements"));

        tableView.setOnDragOver(event -> {
            // Check if the drag-and-drop gesture is over the TableView
            if (event.getGestureSource() != tableView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        tableView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                // Extract the dragged items from the dragboard
                List<String> draggedItems = List.of(dragboard.getString().split("\n"));

                // Add the dragged items to the TableView
                // TODO methods with the same names will be added even if one is chosen
                tableView.getItems().addAll(methodComplexityList.stream().filter(m -> draggedItems.contains(m.getMethodName())).toList());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
            }
        });

        tableView.getColumns().setAll(nameColumn, ifColumn, switchColumn, forColumn, whileColumn);

        return tableView;
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

        System.out.println("Total: " + methodComplexityList.size());
        System.out.println("Satisfied: " + methodComplexityList.stream().filter(MethodComplexity::isCamelCase).count());
        double percentage = methodComplexityList.stream().filter(MethodComplexity::isCamelCase).count() * 1.0 / methodComplexityList.size() * 100;
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

    public ListView<String> makeListView() {
        return new ListView<>(FXCollections.observableArrayList(
                methodComplexityList
                        .stream().filter(m -> !m.isCamelCase())
                        .map(m -> m.getMethodName() + "❗")
                        .collect(Collectors.toList())));
    }

    public void badmethodLOL() {}
    public void ReallyBadOne() {}
}
