package com.buloichyk.quodanatask;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

        complexityEvaluatorTab.setClosable(false);
        complexityEvaluatorTab.setContent(gridPane);

        tabPane.getTabs().addAll(complexityEvaluatorTab, styleCheckerTab);
        return new Scene(tabPane, 600, 600);
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
                    // TODO make appropriate
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
        ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList(
                new PieChart.Data("IF", methodComplexity.getIfStatements()),
                new PieChart.Data("SWITCH", methodComplexity.getSwitchStatements()),
                new PieChart.Data("FOR", methodComplexity.getForStatements()),
                new PieChart.Data("WHILE", methodComplexity.getWhileStatements())
        );
        pieChart.setData(observableList);
        return pieChart;
    }
}
