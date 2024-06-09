package ratio.docker.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import ratio.docker.InfoWrapper.FieldEntry;
import ratio.docker.InfoWrapper.ImageInfoWrapper;
import ratio.docker.Provider.DockerServiceProvider;

import java.util.List;

public class ImagesView {

    @FXML
    private TableView<FieldEntry> detailsArea;

    @FXML
    private TableView<ImageInfoWrapper> tableView;

    @FXML
    private Button refreshButton;

    private final DockerServiceProvider dockerServiceProvider = new DockerServiceProvider();
    private final ObservableList<ImageInfoWrapper> imageData = FXCollections.observableArrayList();

    public void initialize() {
        tableView.setItems(imageData);
        refreshButton.setOnAction(e -> refreshImages());
        tableView.setOnMouseClicked(e -> showImageDetails(e));
        refreshImages();
    }

    private void refreshImages() {
        imageData.clear();
        imageData.addAll(dockerServiceProvider.getImages());
    }

    private void showImageDetails(MouseEvent e) {
        ImageInfoWrapper selectedImage = tableView.getSelectionModel().getSelectedItem();
        if (selectedImage == null) {
            Object source = e.getSource();
            if (source instanceof TableView) {
                TableView tableView = (TableView) source;
                ImageInfoWrapper selectedImageEvent = (ImageInfoWrapper) tableView.getSelectionModel().getSelectedItem();
                detailsArea.setItems(FXCollections.observableArrayList(ImageInfoWrapper.getFieldEntries(
                        dockerServiceProvider.inspectImageDetails(selectedImageEvent.getId())
                )));
            }
        }
        if (selectedImage != null) {
            detailsArea.setItems(FXCollections.observableArrayList(ImageInfoWrapper.getFieldEntries(
                    dockerServiceProvider.inspectImageDetails(selectedImage.getId())
            )));
        }
    }

    public void setImageDetails(String imageId) {
        List<FieldEntry> fieldEntries = ImageInfoWrapper.getFieldEntries(dockerServiceProvider.inspectImageDetails(imageId));
        detailsArea.setItems(FXCollections.observableArrayList(fieldEntries));
    }
}
