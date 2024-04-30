module com.buloichyk.quodanatask {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.javaparser.symbolsolver.core;
    requires com.github.javaparser.core;

    opens com.buloichyk to javafx.fxml;
    exports com.buloichyk;
}