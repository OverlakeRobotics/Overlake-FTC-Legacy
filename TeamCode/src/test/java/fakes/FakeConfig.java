package fakes;

import org.firstinspires.ftc.teamcode.config.IConfig;

import java.util.HashMap;

public class FakeConfig implements IConfig
{
    private HashMap<String, Object> config;

    public FakeConfig()
    {
        this.config = new HashMap<>();
    }

    @Override
    public int getInt(String key)
    {
        EnsureConfigContainsKey(key);
        return (int) config.get(key);
    }

    private void EnsureConfigContainsKey(String key)
    {
        if (!config.containsKey(key))
        {
            throw new IllegalArgumentException("Config does not contain key: " + key);
        }
    }

    @Override
    public float getFloat(String key)
    {
        EnsureConfigContainsKey(key);
        return (float) config.get(key);
    }

    @Override
    public double getDouble(String key)
    {
        EnsureConfigContainsKey(key);
        return (double) config.get(key);
    }

    @Override
    public double getChar(String key)
    {
        EnsureConfigContainsKey(key);
        return (char) config.get(key);
    }

    @Override
    public byte getByte(String key)
    {
        EnsureConfigContainsKey(key);
        return (byte) config.get(key);
    }

    @Override
    public long getLong(String key)
    {
        EnsureConfigContainsKey(key);
        return (long) config.get(key);
    }

    @Override
    public short getShort(String key)
    {
        EnsureConfigContainsKey(key);
        return (short) config.get(key);
    }

    @Override
    public boolean getBoolean(String key)
    {
        EnsureConfigContainsKey(key);
        return (boolean) config.get(key);
    }

    @Override
    public String getString(String key)
    {
        EnsureConfigContainsKey(key);
        return (String) config.get(key);
    }

    public void addConfigItem(String key, Object value)
    {
        config.put(key, value);
    }
}
