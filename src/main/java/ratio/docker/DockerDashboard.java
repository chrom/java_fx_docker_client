package ratio.docker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DockerDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Docker Management Dashboard");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1400, 900);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

