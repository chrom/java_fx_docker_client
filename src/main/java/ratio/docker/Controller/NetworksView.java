package ratio.docker.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import ratio.docker.InfoWrapper.FieldEntry;
import ratio.docker.InfoWrapper.NetworkInfoWrapper;
import ratio.docker.Provider.DockerServiceProvider;


public class NetworksView {

    @FXML
    private TableView<FieldEntry> detailsArea;

    @FXML
    private TableView<NetworkInfoWrapper> tableView = new TableView<>();

    @FXML
    private Button refreshButton;

    private final DockerServiceProvider dockerServiceProvider = new DockerServiceProvider();
    private final ObservableList<NetworkInfoWrapper> networkData = FXCollections.observableArrayList();


    public void initialize() {
        tableView.setItems(networkData);
        tableView.setOnMouseClicked(e -> showNetworkDetails());
        refreshButton.setOnAction(e -> refreshNetworks());
        refreshNetworks();
    }

    private void refreshNetworks() {
        networkData.clear();
        networkData.addAll(dockerServiceProvider.getNetworks());
    }

    private void showNetworkDetails() {
        NetworkInfoWrapper selectedNetwork = tableView.getSelectionModel().getSelectedItem();
        if (selectedNetwork != null) {
            detailsArea.setItems(FXCollections.observableArrayList(selectedNetwork.getFieldEntries()));
        }
    }
}
