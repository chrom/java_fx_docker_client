package ratio.docker.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class DashboardView {

    @FXML
    private TabPane tabPane;

    @FXML
    private VBox containersViewBox;

    @FXML
    private VBox imagesViewBox;

    public void initialize() {
//        containersViewBox.getChildren().get(2);
//        ContainersView containersView = (ContainersView) containersViewBox.getProperties().get("fx:controller");
//        containersView.setDashboardView(this);
    }

    public void showImageDetails(String imageId) {
        int imagesTabIndex = tabPane.getTabs().indexOf("imagesTab");
        tabPane.getSelectionModel().select(imagesTabIndex);
        ImagesView imagesView = (ImagesView) imagesViewBox.getProperties().get("fx:controller");
        imagesView.setImageDetails(imageId);
    }
}
