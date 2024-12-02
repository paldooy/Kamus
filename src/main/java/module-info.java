module org.example.kamus {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.scripting;

    opens org.example.kamus to javafx.fxml;
    exports org.example.kamus;
    exports org.example.kamus.controller;
    opens org.example.kamus.controller to javafx.fxml;
    exports org.example.kamus.model;
    opens org.example.kamus.model to javafx.fxml;
}