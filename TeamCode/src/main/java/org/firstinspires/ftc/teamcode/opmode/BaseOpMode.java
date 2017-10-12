package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.Button;
import org.firstinspires.ftc.teamcode.robot.Component;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by EvanCoulson on 10/11/17.
 */

public abstract class BaseOpMode extends OpMode {
    private Map<String, Component> components;
    private Set<Button> buttonSet;
    private ConfigParser config;

    public void initializeComponents(String opModeName) {
        components = new HashMap<String, Component>();
        buttonSet = new HashSet<Button>();
        try {
            config = new ConfigParser(opModeName + ".omc");
        } catch(Exception e) {
            throw new IllegalArgumentException("OP MODE CONFIGURATION NOT FOUND \"" + opModeName + "\"");
        }
        Set<String> keys = config.getKeys();
        for (String key : keys) {
            String componentName = config.getString(key);
            telemetry.addData("[Component] Intializing ", componentName.toUpperCase() + "...");
            Component component = new Component(hardwareMap, telemetry, componentName);
            component.setOpModeButtonSet(buttonSet);
            components.put(componentName, component);
        }
    }

    public Component getComponent(String componentName) {
        return components.get(componentName);
    }

    public void handleButtons() {
        for (Button button : buttonSet) {
            button.testAndHandle();
        }
    }
}
