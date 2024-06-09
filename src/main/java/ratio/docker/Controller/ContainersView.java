package ratio.docker.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import ratio.docker.InfoWrapper.ContainerInfoWrapper;
import ratio.docker.InfoWrapper.FieldEntry;
import ratio.docker.Provider.DockerServiceProvider;


public class ContainersView {

    @FXML
    private TableView<ContainerInfoWrapper> tableView;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<FieldEntry> detailsArea;

    @FXML
    private MenuItem stopContainerMenuItem;

    @FXML
    private MenuItem removeContainerMenuItem;

    @FXML
    private MenuItem restartContainerMenuItem;

    @FXML
    private TableColumn<ContainerInfoWrapper, String> statusColumn;

    @FXML
    private MenuItem openRelatedImageMenuItem;

    private DashboardView dashboardView;
    private final DockerServiceProvider containersDockerService = new DockerServiceProvider();
    private final ObservableList<ContainerInfoWrapper> containerData = FXCollections.observableArrayList();

    public void initialize() {
        statusColumn.setCellFactory(column -> new TableCell<ContainerInfoWrapper, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : getItem());
                setGraphic(null);
                if (!isEmpty()) {
                    if (item.contains("Up")) {
                        setTextFill(Color.GREEN);
                    } else if (item.contains("Exited")) {
                        setTextFill(Color.RED);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                }
            }
        });

//        openRelatedImageMenuItem.setOnAction(event -> {
//            ContainerInfoWrapper selectedContainer = tableView.getSelectionModel().getSelectedItem();
//            dashboardView.showImageDetails(selectedContainer.getImageId());
//        });

        refreshButton.setOnAction(e -> refreshContainers());
        stopContainerMenuItem.setOnAction(e -> stopContainer());
        restartContainerMenuItem.setOnAction(e -> restartContainer());
        removeContainerMenuItem.setOnAction(e -> removeContainer());

        tableView.setItems(containerData);
        tableView.setOnMouseClicked(e -> showContainerDetails());
        refreshContainers();
    }


    private void refreshContainers() {
        refreshButton.setDisable(true);
        containerData.clear();
        containerData.addAll(containersDockerService.getContainers());
        refreshButton.setDisable(false);
    }

    private void showContainerDetails() {
        ContainerInfoWrapper selectedContainer = tableView.getSelectionModel().getSelectedItem();

        if (selectedContainer != null) {
            detailsArea.setItems(FXCollections.observableArrayList(selectedContainer.getFieldEntries()));
        }
    }

    private void stopContainer() {
        ContainerInfoWrapper selectedContainer = tableView.getSelectionModel().getSelectedItem();
        if (selectedContainer != null) {
            containersDockerService.stopContainer(selectedContainer);
        }
        refreshContainers();
    }

    private void removeContainer() {
        ContainerInfoWrapper selectedContainer = tableView.getSelectionModel().getSelectedItem();
        if (selectedContainer != null) {
            containersDockerService.removeContainer(selectedContainer);
        }
        refreshContainers();
    }

    public void restartContainer() {
        ContainerInfoWrapper selectedContainer = tableView.getSelectionModel().getSelectedItem();
        if (Boolean.FALSE.equals(containersDockerService.getContainer(selectedContainer).getState().getRunning())) {
            containersDockerService.startContainer(selectedContainer);
        }
        refreshContainers();
    }

    public void setDashboardView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }
}