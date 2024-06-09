package ratio.docker.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import ratio.docker.InfoWrapper.VolumeInfoWrapper;
import ratio.docker.Provider.DockerServiceProvider;


public class VolumesView {

    @FXML
    private TableView<VolumeInfoWrapper> volumeView = new TableView<>();

    @FXML
    private Button refreshButton;

    private final ObservableList<VolumeInfoWrapper> volumeData = FXCollections.observableArrayList();
    private final DockerServiceProvider dockerServiceProvider = new DockerServiceProvider();

    public void initialize() {
        volumeView.setItems(volumeData);
        refreshButton.setOnAction(e -> refreshVolumes());
        refreshVolumes();
    }

    private void refreshVolumes() {
        volumeData.clear();
        volumeData.addAll(dockerServiceProvider.getVolumes());
    }
}