package com.buloichyk;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

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

    public MainSceneController() {
        this.methodComplexityList = new ArrayList<>();
    }
    public Scene buildMainScene() {
        TabPane tabPane = new TabPane();
        Tab complexityEvaluatorTab = new Tab("Complexity Evaluator");
        Tab styleCheckerTab = new Tab("Style Checker");

        BarChart<Number, String> barChart = makeBarChart();
        GridPane.setConstraints(barChart, 0,0);
        gridPane.getChildren().add(barChart);
        TitledPane titledPane = makeTitledPane();

        GridPane.setConstraints(titledPane, 0, 1);
        gridPane.getChildren().add(titledPane);

        TableView<MethodComplexity> tableView = makeTable();
        GridPane.setConstraints(tableView, 1, 1);
        gridPane.getChildren().add(tableView);

        complexityEvaluatorTab.setClosable(false);
        complexityEvaluatorTab.setContent(gridPane);

        tabPane.getTabs().addAll(complexityEvaluatorTab, styleCheckerTab);
        return new Scene(tabPane);
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
//        conditionalsCount.setTickLabelFormatter(new StringConverter<>() {
//            @Override
//            public String toString(Number value) {
//                return value.intValue() + "";
//            }
//
//            @Override
//            public Number fromString(String string) {
//                return Integer.parseInt(string);
//            }
//        });

        BarChart<Number, String> barChart = new BarChart<>(conditionalsCount, methodNames);

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

//        listView.setOnMouseClicked(mouseEvent -> {
//            if (mouseEvent.getClickCount() == 2) {
//                ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
//                tableView.getItems().addAll(methodComplexityList.stream().filter(m -> selectedItems.contains(m.getMethodName())).toList());
//            }
//        });

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

        ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();

        TitledPane titledPane = new TitledPane("Others", listView);
        titledPane.setCollapsible(true);
        titledPane.setExpanded(false);

        return titledPane;
    }

    public TableView<MethodComplexity> makeTable() {
        TableView<MethodComplexity> tableView = new TableView<>(FXCollections.observableArrayList(methodComplexityList));
        TableColumn<MethodComplexity, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("methodName"));
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
}
