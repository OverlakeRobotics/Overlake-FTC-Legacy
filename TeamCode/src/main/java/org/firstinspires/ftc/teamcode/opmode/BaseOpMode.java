package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.Component;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by EvanCoulson on 10/11/17.
 */

public abstract class BaseOpMode extends OpMode {
    Map<String, Component> components;
    ConfigParser config;

    public void initializeComponents(String opModeName) {
        components = new HashMap<String, Component>();
        try {
            config = new ConfigParser(opModeName + ".omc");
        } catch(Exception e) {
            throw new IllegalArgumentException("OP MODE CONFIGURATION NOT FOUND \"" + opModeName + "\"");
        }
        Set<String> keys = config.getKeys();
        for (String key : keys) {
            String componentName = config.getString(key);
            telemetry.addData("[Component] Intializing ", componentName.toUpperCase() + "...");
            components.put(componentName, new Component(hardwareMap, telemetry, componentName));
        }
    }

    public Component getComponent(String componentName) {
        return components.get(componentName);
    }
}
