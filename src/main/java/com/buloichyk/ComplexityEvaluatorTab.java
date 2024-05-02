package com.buloichyk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

/** This class provides a graphical representation of complexity analysis.
* There are 4 components:
*   1. Bar Chart -> displays the top 3 most complex methods
*   2. Pie Chart -> illustrates details of the most complex method (by default) or the selected bar
*   3. Dropdown List -> enables method selection from the list for detailed analysis in the table
*   4. Table -> presents detailed complexity metrics for the selected methods
**/
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
        // place the TitledPane at the top of the cell
        GridPane.setValignment(titledPane, VPos.TOP);
        gridPane.getChildren().add(titledPane);

        TableView<MethodComplexity> tableView = makeTableView();
        GridPane.setConstraints(tableView, 1, 1);
        gridPane.getChildren().add(tableView);

        setContent(gridPane);
        setClosable(false);
    }

    public BarChart<Number, String> makeBarChart() {
        List<MethodComplexity> topList = MethodComplexityListProvider.getTop3MostComplexMethods();

        PieChart best = makePieChart(topList.get(0));
        GridPane.setConstraints(best, 1, 0);
        gridPane.getChildren().add(best);

        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setCategories(FXCollections.observableArrayList(MethodComplexityListProvider.getNamesOfTop3MostComplexMethods()));

        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(1);
        xAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number object) {
                if (object.intValue() != object.doubleValue())
                    return "";
                return "" + (object.intValue());
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

            // wait while node is available to access it
            dataEntry.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    dataEntry.getNode().setOnMouseClicked(mouseEvent -> {
                        // remove the pie chart of previously displayed method
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
        // prepare data for pie chart
        List<PieChart.Data> data = new ArrayList<>();
        String format = "%s (%d)";
        if (methodComplexity.getIfStatements() > 0) {
            int ifStatements = methodComplexity.getIfStatements();
            data.add(new PieChart.Data(String.format(format, "IF", ifStatements), ifStatements));
        }
        if (methodComplexity.getSwitchStatements() > 0) {
            int switchStatements = methodComplexity.getSwitchStatements();
            data.add(new PieChart.Data(String.format(format, "SWITCH", switchStatements), switchStatements));
        }
        if (methodComplexity.getForStatements() > 0) {
            int forStatements = methodComplexity.getForStatements();
            data.add(new PieChart.Data(String.format(format, "FOR", forStatements), forStatements));
        }
        if (methodComplexity.getWhileStatements() > 0) {
            int whileStatements = methodComplexity.getWhileStatements();
            data.add(new PieChart.Data(String.format(format, "WHILE", whileStatements), whileStatements));
        }

        PieChart pieChart = new PieChart(FXCollections.observableArrayList(data));
        pieChart.setTitle(methodComplexity.getMethodName());
        pieChart.setLabelLineLength(15);

        return pieChart;
    }

    public TitledPane makeTitledPane() {
        ObservableList<String> allMethodsNames = FXCollections.observableArrayList(MethodComplexityListProvider.getNamesOfMethodsOrderedByDescendingComplexity());
        ListView<String> listView = new ListView<>(allMethodsNames);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // pack selected data for transfer
        listView.setOnDragDetected(event -> {
            Dragboard dragboard = listView.startDragAndDrop(TransferMode.COPY);
            List<String> selectedItems = listView.getSelectionModel().getSelectedItems();
            ClipboardContent content = new ClipboardContent();
            content.put(DataFormat.PLAIN_TEXT, String.join("\n", selectedItems));
            dragboard.setContent(content);
            event.consume();
        });

        TitledPane titledPane = new TitledPane("All methods", listView);
        titledPane.setCollapsible(true);
        titledPane.setExpanded(false);

        return titledPane;
    }

    public TableView<MethodComplexity> makeTableView() {
        TableView<MethodComplexity> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        // prepare columns for the table
        TableColumn<MethodComplexity, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("methodName"));
        // specify column width for name as 40% of the table width
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
            if (event.getGestureSource() != tableView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        // add data to the table after user drop it
        tableView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                List<String> draggedItems = List.of(dragboard.getString().split("\n"));
                tableView.getItems().addAll(MethodComplexityListProvider.getMethodsFilteredByName(draggedItems));
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // allow selection of multiple rows and delete them using backspace
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
