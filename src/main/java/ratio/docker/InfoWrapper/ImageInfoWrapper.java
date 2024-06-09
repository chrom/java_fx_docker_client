package ratio.docker.InfoWrapper;

import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImageInfoWrapper {
    private Long created;

    private String id;

    private String parentId;

    private String[] repoTags;

    private String[] repoDigests;

    private Long size;

    private Long virtualSize;

    private Long sharedSize;

    private Map<String, String> labels;

    private Integer containers;


    public ImageInfoWrapper(Image image) {
        this.created = image.getCreated();
        this.id = image.getId();
        this.parentId = image.getParentId();
        this.repoTags = image.getRepoTags();
        this.repoDigests = image.getRepoDigests();
        this.size = image.getSize();
        this.virtualSize = image.getVirtualSize();
        this.sharedSize = image.getSharedSize();
        this.labels = image.getLabels();
        this.containers = image.getContainers();
    }

    public String getCreated() {
        if (created == null) {
            return "-";
        }
        Instant instant = Instant.ofEpochSecond(created);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getRepoTags() {
        return (repoTags != null) ? Arrays.toString(repoTags) : "-";
    }

    public String getRepoDigests() {
        return (repoDigests != null) ? Arrays.toString(repoDigests) : "-";
    }

    public String getSize() {
        return size.toString();
    }

    public String getVirtualSize() {
        return (virtualSize != null) ? virtualSize.toString() : "-";
    }

    public String getSharedSize() {
        return sharedSize.toString();
    }

    public String getLabels() {
        if (labels == null) {
            return "-";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            sb.append("\n  ").append(entry.getKey()).append(" = ").append(entry.getValue());
        }
        sb.append("\n}");

        return sb.toString();
    }

    public String getContainers() {
        return containers.toString();
    }

    public static List<FieldEntry> getFieldEntries(InspectImageResponse imageResponse) {
        String repoTags = (imageResponse.getRepoTags() != null) ? String.join(", ", imageResponse.getRepoTags()) : "-";
        String repoDigests = (imageResponse.getRepoDigests() != null) ? String.join(", ", imageResponse.getRepoDigests()) : "-";
        String created = (imageResponse.getCreated() != null) ? imageResponse.getCreated() : "-";
        String id = (imageResponse.getId() != null) ? imageResponse.getId() : "-";
        String parentId = (imageResponse.getParent() != null) ? imageResponse.getParent() : "-";
        String dockerVersion = (imageResponse.getDockerVersion() != null) ? imageResponse.getDockerVersion() : "-";
        String os = (imageResponse.getOs() != null) ? imageResponse.getOs() : "-";
        String osVersion = (imageResponse.getOsVersion() != null) ? imageResponse.getOsVersion() : "-";
        String size = (imageResponse.getSize() != null) ? imageResponse.getSize().toString() : "-";
        String virtualSize = (imageResponse.getVirtualSize() != null) ? imageResponse.getVirtualSize().toString() : "-";
        String graphDriver = (imageResponse.getGraphDriver() != null) ? imageResponse.getGraphDriver().toString() : "-";
        String rootFS = (imageResponse.getRootFS() != null) ? imageResponse.getRootFS().toString() : "-";

        return Arrays.asList(
                new FieldEntry("ID", id),
                new FieldEntry("Parent ID", parentId),
                new FieldEntry("Repository Tags", repoTags),
                new FieldEntry("Repository Digests", repoDigests),
                new FieldEntry("Size", size),
                new FieldEntry("Virtual Size", virtualSize),
                new FieldEntry("Created", created),
                new FieldEntry("Docker Version", dockerVersion),
                new FieldEntry("OS", os),
                new FieldEntry("OS Version", osVersion),
                new FieldEntry("Graph Driver", graphDriver),
                new FieldEntry("Root FS", rootFS)
        );
    }
}
