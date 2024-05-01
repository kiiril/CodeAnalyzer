package com.buloichyk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class ComplexityEvaluatorTab extends Tab {
    private final GridPane gridPane;
    public ComplexityEvaluatorTab() {
        super("Complexity Evaluator");
        this.gridPane = new GridPane();

        BarChart<Number, String> barChart = makeBarChart();
        GridPane.setConstraints(barChart, 0,0);
        gridPane.getChildren().add(barChart);

        TitledPane titledPane = makeTitledPane();
        GridPane.setConstraints(titledPane, 0, 1);
        // places the TitledPane at the top of the cell
        GridPane.setValignment(titledPane, VPos.TOP);
        gridPane.getChildren().add(titledPane);

        TableView<MethodComplexity> tableView = makeTableView();
        GridPane.setConstraints(tableView, 1, 1);
        gridPane.getChildren().add(tableView);

        setContent(gridPane);
        setClosable(false);
    }

    public BarChart<Number, String> makeBarChart() {
        // FIXME differentiate methods with same name
        List<MethodComplexity> topList = MethodComplexityListProvider.getTop3MostComplexMethods();
        System.out.println(topList);

        PieChart worst = makePieChart(topList.get(0));
        GridPane.setConstraints(worst, 1, 0);
        gridPane.getChildren().add(worst);

        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setCategories(FXCollections.observableArrayList(MethodComplexityListProvider.getNamesOfTop3MostComplexMethods()));

        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(1);
        xAxis.setTickLabelFormatter(new StringConverter<>() {
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
        xAxis.setMinorTickVisible(false);

        BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
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
        ObservableList<String> options = FXCollections.observableArrayList(MethodComplexityListProvider.getNamesOfRemainingMethodsAfterTop3());

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

    public TableView<MethodComplexity> makeTableView() {
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
                tableView.getItems().addAll(MethodComplexityListProvider.getMethodsFilteredByName(draggedItems));
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
