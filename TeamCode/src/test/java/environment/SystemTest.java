package environment;

import android.os.Environment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by EvanCoulson on 9/18/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Environment.class})
public abstract class SystemTest
{
    private final String ConfigDirectoryPath = "/Android/data/com.overlake.ftc.configapp/files";

    @Rule
    public TemporaryFolder storageDirectory = new TemporaryFolder();
    private File nonExistentDirectory;
    private File existentDirectory;

    private HashMap<String, File> configFileMapping;

    public SystemTest() {
        this.configFileMapping = new HashMap<String, File>();
    }

    @Before
    public void setupFileSystem() throws IOException
    {
        nonExistentDirectory = Mockito.mock(File.class);
        Mockito.when(nonExistentDirectory.exists()).thenReturn(false);
        existentDirectory = storageDirectory.getRoot();
        PowerMockito.mockStatic(Environment.class);
        Mockito.when(Environment.getExternalStorageDirectory()).thenReturn(existentDirectory);
    }

    public void initializeConfig(String name) throws IOException
    {
        File configFile = createConfigFile(name);
        configFile.createNewFile();
        configFileMapping.put(name, configFile);
    }

    private File createConfigFile(String name) {
        File root = new File(existentDirectory.getAbsolutePath() + ConfigDirectoryPath, "configurations");
        root.mkdirs();
        return new File(root.getPath() + "/" + name + ".omc");
    }

    public void addConfigValues(String name, ConfigValue... configValues) throws IOException
    {
        File configFile = configFileMapping.get(name);
        FileWriter writer = new FileWriter(configFile);
        for (ConfigValue configValue: configValues)
        {
            writer.append(configValue.toString());
        }
        writer.flush();
        writer.close();
    }
}
