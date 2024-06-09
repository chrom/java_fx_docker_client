package ratio.docker.InfoWrapper;

import com.github.dockerjava.api.model.Network;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NetworkInfoWrapper {
    private final String id;
    private final String name;
    private final String driver;
    private final String scope;
    private final Boolean enableIPv6;
    private final Boolean internal;
    private final Network.Ipam ipam;
    private final Boolean attachable;
    private final Map<String, String> labels;
    private final Map<String, Network.ContainerNetworkConfig> containers;
    private final Map<String, String> options;

    public NetworkInfoWrapper(Network network) {
        this.id = network.getId();
        this.name = network.getName();
        this.driver = network.getDriver();
        this.options = network.getOptions();
        this.scope = network.getScope();
        this.enableIPv6 = network.getEnableIPv6();
        this.internal = network.getInternal();
        this.ipam = network.getIpam();
        this.attachable = network.isAttachable();
        this.labels = network.getLabels();
        this.containers = network.getContainers();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDriver() {
        return driver;
    }

    public String getOptions() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : options.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public String getScope() {
        return scope;
    }

    public String getEnableIPv6() {
        return enableIPv6.toString();
    }

    public String getInternal() {
        return internal.toString();
    }

    public String getIpam() {
        StringBuilder sb = new StringBuilder();
        sb.append("Driver: ").append(ipam.getDriver()).append("\n");
        sb.append("Options: ").append(ipam.getOptions()).append("\n");
        sb.append("Config: ").append("\n");
        if (ipam.getConfig() != null) {
            for (Network.Ipam.Config c : ipam.getConfig()) {
                sb.append("  Subnet: ").append(c.getSubnet()).append("\n");
                sb.append("  IPRange: ").append(c.getIpRange()).append("\n");
                sb.append("  Gateway: ").append(c.getGateway()).append("\n");
                sb.append("  NetworkID: ").append(c.getNetworkID()).append("\n");
            }
        }
        return sb.toString();
    }

    public String getAttachable() {
        return attachable.toString();
    }

    public String getLabels() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public String getContainers() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Network.ContainerNetworkConfig> entry : containers.entrySet()) {
            sb.append("Container ID: ").append(entry.getKey()).append("\n");
            Network.ContainerNetworkConfig config = entry.getValue();
            sb.append("  Name: ").append(config.getName()).append("\n");
            sb.append("  EndpointID: ").append(config.getEndpointId()).append("\n");
            sb.append("  MacAddress: ").append(config.getMacAddress()).append("\n");
            sb.append("  IPv4Address: ").append(config.getIpv4Address()).append("\n");
            sb.append("  IPv6Address: ").append(config.getIpv6Address()).append("\n");
        }
        return sb.toString();
    }

    public List<FieldEntry> getFieldEntries() {
        return Arrays.asList(
                new FieldEntry("ID", getId()),
                new FieldEntry("Name", getName()),
                new FieldEntry("Driver", getDriver()),
                new FieldEntry("Options", getOptions()),
                new FieldEntry("Scope", getScope()),
                new FieldEntry("Enable IPv6", getEnableIPv6().toString()),
                new FieldEntry("Internal", getInternal().toString()),
                new FieldEntry("IPAM", getIpam()),
                new FieldEntry("Attachable", getAttachable().toString()),
                new FieldEntry("Labels", getLabels()),
                new FieldEntry("Containers", getContainers())
        );
    }
}