package environment;

/**
 * Created by EvanCoulson on 9/18/18.
 */

public class ConfigValue
{
    private String key;
    private String value;
    private String type;

    public ConfigValue(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(":{0}: {1} {2}\n", type, key, value);
    }
}
