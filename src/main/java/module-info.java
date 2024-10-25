module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
    exports com.example.demo2.algorithms;
    opens com.example.demo2.algorithms to javafx.fxml;
}