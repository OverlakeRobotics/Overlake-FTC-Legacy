package org.firstinspires.ftc.teamcode.tools;

import org.firstinspires.ftc.teamcode.config.IConfig;

/**
 * Created by EvanCoulson on 8/29/18.
 */

public class MiniPIDFactory {
    public static MiniPID getMiniPIDFromConfig(IConfig config) {
        MiniPID miniPID = new MiniPID(config.getDouble("P"), config.getDouble("I"), config.getDouble("D"));
        miniPID.setOutputLimits(config.getDouble("OutputLimits"));
        return miniPID;
    }
}
