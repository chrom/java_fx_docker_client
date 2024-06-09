package ratio.docker.InfoWrapper;

import com.github.dockerjava.api.command.InspectVolumeResponse;

import java.util.Map;
import java.util.stream.Collectors;

public class VolumeInfoWrapper {
    private final String name;
    private final String driver;
    private final String mountpoint;
    private final Map<String, String> labels;
    private final Map<String, String> options;

    public VolumeInfoWrapper(InspectVolumeResponse volume) {
        this.name = volume.getName();
        this.driver = volume.getDriver();
        this.mountpoint = volume.getMountpoint();
        this.labels = volume.getLabels();
        this.options = volume.getOptions();
    }

    public String getName() {
        return name;
    }

    public String getDriver() {
        return driver;
    }

    public String getMountpoint() {
        return mountpoint;
    }

public String getLabels() {
    if (this.labels == null) {
        return ""; // or return a default message
    }
    return labels.entrySet().stream()
        .map(entry -> entry.getKey() + ": " + entry.getValue())
        .collect(Collectors.joining(", "));
}

public String getOptions() {
    if (this.options == null) {
        return ""; // or return a default message
    }
    return options.entrySet().stream()
            .map(entry -> entry.getKey() + ": " + entry.getValue())
            .collect(Collectors.joining(", "));
}
}
