package ratio.docker.InfoWrapper;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerMount;
import com.github.dockerjava.api.model.ContainerNetworkSettings;
import com.github.dockerjava.api.model.ContainerPort;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContainerInfoWrapper {
    private final String id;
    private final String image;
    private final String name;
    private final String imageId;
    private final String command;
    private final Long created;
    private final String status;
    private final ContainerPort[] ports;
    private final Map<String, String> labels;
    private final Long sizeRw;
    private final Long sizeRootFs;
    private final ContainerNetworkSettings networkSettings;
    private final String getNetworkMode;
    private final List<ContainerMount> mounts;

    public ContainerInfoWrapper(Container container) {
        this.id = container.getId();
        this.image = container.getImage();
        this.name = (container.getNames() != null && container.getNames().length != 0) ? String.join(" ", container.getNames()) : "Default Name";
        this.imageId = container.getImageId();
        this.command = container.getCommand();
        this.created = container.getCreated();
        this.status = container.getStatus();
        this.ports = container.getPorts();
        this.labels = container.getLabels();
        this.sizeRw = container.getSizeRw();
        this.sizeRootFs = container.getSizeRootFs();
        this.networkSettings = container.getNetworkSettings();
        this.getNetworkMode = container.getHostConfig().getNetworkMode();
        this.mounts = container.getMounts();
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }

    public String getCommand() {
        return command;
    }

    public Long getCreated() {
        return created;
    }

    public String getStatus() {
        return status;
    }

    public String getPorts() {
        if (ports == null) {
            return "";
        }
        return Arrays.stream(ports)
                .map(port -> port.getIp() + ":" + port.getPublicPort() + " | " + port.getType())
                .collect(Collectors.joining(", "));
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public Long getSizeRw() {
        return sizeRw;
    }

    public Long getSizeRootFs() {
        return sizeRootFs;
    }

    public String getNetworkSettings() {
        if (networkSettings == null) {
            return "";
        }
        return networkSettings.getNetworks().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue().toString())
                .collect(Collectors.joining(", "));
    }

    public String getGetNetworkMode() {
        return getNetworkMode;
    }

    public String getMounts() {
        if (mounts == null) {
            return "";
        }
        return mounts.stream()
                .map(mount -> "Source: " + mount.getSource() + ", Destination: " + mount.getDestination() + ", Mode: " + mount.getMode() + ", RW: " + mount.getRw())
                .collect(Collectors.joining(", "));
    }

    public List<FieldEntry> getFieldEntries() {
        return Arrays.asList(
                new FieldEntry("ID", getId()),
                new FieldEntry("Image", getImage()),
                new FieldEntry("Name", getName()),
                new FieldEntry("Image ID", getImageId()),
                new FieldEntry("Command", getCommand()),
                new FieldEntry("Created", String.valueOf(getCreated())),
                new FieldEntry("Status", getStatus()),
                new FieldEntry("Ports", getPorts()),
                new FieldEntry("Labels", getLabels().toString()),
                new FieldEntry("Size RW", String.valueOf(getSizeRw())),
                new FieldEntry("Size Root FS", String.valueOf(getSizeRootFs())),
                new FieldEntry("Network Settings", getNetworkSettings()),
                new FieldEntry("Network Mode", getGetNetworkMode()),
                new FieldEntry("Mounts", getMounts())
        );
    }
}
