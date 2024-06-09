package ratio.docker.InfoWrapper;

public class FieldEntry {
    private final String key;
    private final String value;

    public FieldEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
