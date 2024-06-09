module ratio.docker {
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
    requires com.github.dockerjava.api;
    requires com.github.dockerjava.core;
    requires com.github.dockerjava.transport;
    requires com.github.dockerjava.transport.okhttp;
    requires annotations;

    // Ensure the packages are opened for reflection by FXML
    opens ratio.docker to javafx.fxml;
    opens ratio.docker.Controller to javafx.fxml;
    opens ratio.docker.InfoWrapper to javafx.base;

    exports ratio.docker;
    exports ratio.docker.Controller;
}
