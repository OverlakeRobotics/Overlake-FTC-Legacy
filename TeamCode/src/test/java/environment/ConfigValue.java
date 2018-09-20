package environment;

/**
 * Created by EvanCoulson on 9/18/18.
 */

public class ConfigValue
{
    private String key;
    private String value;
    private String type;

    public ConfigValue(String type, String key, String value) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(":%1$s: %2$s %3$s\n", type, key, value);
    }
}
