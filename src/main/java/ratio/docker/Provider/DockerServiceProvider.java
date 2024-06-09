package ratio.docker.Provider;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.command.InspectNetworkCmd;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.core.DockerClientImpl;
import org.jetbrains.annotations.NotNull;
import ratio.docker.InfoWrapper.ContainerInfoWrapper;
import com.github.dockerjava.okhttp.OkDockerHttpClient;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import ratio.docker.InfoWrapper.ImageInfoWrapper;
import ratio.docker.InfoWrapper.NetworkInfoWrapper;
import ratio.docker.InfoWrapper.VolumeInfoWrapper;

public class DockerServiceProvider implements Closeable {

    private final DockerClient dockerClient;

    public DockerServiceProvider() {
        Properties properties = new Properties();
        try {
            String path = Objects.requireNonNull(getClass().getClassLoader().getResource("ratio/docker/")).getPath();
            properties.load(new FileInputStream(path + "docker-java.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load docker-java.properties", e);
        }

        DockerClientConfig config = DefaultDockerClientConfig.
                createDefaultConfigBuilder()
                .withDockerHost(properties.getProperty("DOCKER_HOST"))
                .withDockerTlsVerify(properties.getProperty("DOCKER_TLS_VERIFY").equals("1"))
                .withDockerCertPath(properties.getProperty("DOCKER_CERT_PATH"))
                .withRegistryUsername(properties.getProperty("registry.username"))
                .withRegistryPassword(properties.getProperty("registry.password"))
                .build();

        URI dockerHostUri;
        try {
            dockerHostUri = new URI(properties.getProperty("DOCKER_HOST"));
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid DOCKER_HOST URI", e);
        }
        OkDockerHttpClient httpClient = new OkDockerHttpClient.Builder()
                .dockerHost(dockerHostUri)
                .sslConfig(config.getSSLConfig())
                .build();

        this.dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }

    public List<VolumeInfoWrapper> getVolumes() {
        List<InspectVolumeResponse> volumes = dockerClient.listVolumesCmd().exec().getVolumes();
        return volumes.stream()
                .map(VolumeInfoWrapper::new)
                .collect(Collectors.toList());
    }

    public List<NetworkInfoWrapper> getNetworks() {
        List<Network> networks = dockerClient.listNetworksCmd().exec();
        return networks.stream()
                .map(NetworkInfoWrapper::new)
                .collect(Collectors.toList());
    }

    public List<ImageInfoWrapper> getImages() {
        List<Image> images = dockerClient.listImagesCmd().exec();
        return images.stream()
                .map(ImageInfoWrapper::new)
                .collect(Collectors.toList());
    }

    public List<ContainerInfoWrapper> getContainers() {
        List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();
        return containers.stream()
                .map(ContainerInfoWrapper::new)
                .collect(Collectors.toList());
    }

    public void stopContainer(@NotNull ContainerInfoWrapper containerInfo) {
        dockerClient.stopContainerCmd(containerInfo.getId()).exec();
    }

    public void removeContainer(@NotNull ContainerInfoWrapper containerInfo) {
        dockerClient.removeContainerCmd(containerInfo.getId()).exec();
    }

    public void startContainer(@NotNull ContainerInfoWrapper containerInfo) {
        dockerClient.startContainerCmd(containerInfo.getId()).exec();
    }

    public InspectContainerResponse getContainer(@NotNull ContainerInfoWrapper containerInfo) {
        return dockerClient.inspectContainerCmd(containerInfo.getId()).exec();
    }

    public InspectImageResponse inspectImageDetails(String imageId) {
        try {
            return dockerClient.inspectImageCmd(imageId).exec();
        } catch (NotFoundException e) {
            return null;
        }
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        this.dockerClient.close();
    }
}