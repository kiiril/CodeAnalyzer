package com.buloichyk;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * This utility class provides methods to retrieve data about all methods in a format suitable for the specific visualization.
 **/
public class MethodComplexityListProvider {
    private static final List<MethodComplexity> methodComplexityList = new ArrayList<>();

    public static void analyzeCodeBase(Path directory) {
        if (Files.isDirectory(directory)) {
            try (Stream<Path> stream = Files.walk(directory)) {
                List<Path> files = stream
                        .filter(file -> file.toString().endsWith(".java"))
                        .toList();
                for (Path file: files) {
                    CompilationUnit unit = StaticJavaParser.parse(file);
                    FileVisitor fileVisitor = new FileVisitor();
                    fileVisitor.visit(unit, methodComplexityList);
                }
            } catch (IOException e) {
                System.out.println("Cannot open the directory: " + e);
            }
        }
    }

    public static List<MethodComplexity> getMethodsOrderedByDescendingComplexity() {
        return methodComplexityList.stream()
                .sorted(Comparator.comparingInt(MethodComplexity::getTotalConditionalStatements).reversed())
                .toList();
    }

    public static List<MethodComplexity> getTop3MostComplexMethods() {
        return getMethodsOrderedByDescendingComplexity().subList(0, 3);
    }

    public static List<MethodComplexity> getMethodsFilteredByName(List<String> methodNames) {
        return methodComplexityList.stream().filter(m -> methodNames.contains(m.getMethodName())).toList();
    }

    public static List<MethodComplexity> getMethodsWithCamelCaseNames() {
        return methodComplexityList.stream().filter(MethodComplexity::isCamelCase).toList();
    }

    public static List<MethodComplexity> getMethodsWithNonCamelCaseNames() {
        return methodComplexityList.stream().filter(m -> !m.isCamelCase()).toList();
    }

    public static List<String> getNamesOfMethodsOrderedByDescendingComplexity() {
        return getMethodNamesFromList(getMethodsOrderedByDescendingComplexity());
    }

    public static List<String> getNamesOfTop3MostComplexMethods() {
        return getMethodNamesFromList(getTop3MostComplexMethods());
    }

    public static int getNumberOfMethodsWithCamelCaseNames() {
        return getMethodsWithCamelCaseNames().size();
    }

    public static double getPercentageOfMethodsWithCamelCaseNames() {
        return (getNumberOfMethodsWithCamelCaseNames() * 100.0) / getMethodComplexityListSize();
    }

    public static int getMethodComplexityListSize() {
        return methodComplexityList.size();
    }

    private static List<String> getMethodNamesFromList(List<MethodComplexity> methodComplexities) {
        return methodComplexities.stream()
                .map(MethodComplexity::getMethodName)
                .toList();
    }

}
