package org.firstinspires.ftc.teamcode.tools;

import junit.framework.Assert;

import org.firstinspires.ftc.teamcode.config.IConfig;
import fakes.FakeConfig;
import org.junit.Test;

/**
 * Created by EvanCoulson on 8/29/18.
 */

public class MiniPIDFactoryTests {
    @Test
    public void MiniPIDFactory_getMiniPIDFromConfig_ReturnsMiniPID() {
        IConfig config = getConfig();
        MiniPID pid = MiniPIDFactory.getMiniPIDFromConfig(config);
        Assert.assertNotNull(pid);
    }

    private IConfig getConfig() {
        FakeConfig config = new FakeConfig();
        config.addConfigItem("P", 0d);
        config.addConfigItem("D", 0d);
        config.addConfigItem("I", 0d);
        config.addConfigItem("OutputLimits", 1.0);
        return config;
    }
}
