package org.firstinspires.ftc.teamcode.robot;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

import java.util.Map;
import java.util.Set;

/**
 * Created by EvanCoulson on 10/5/17.
 */

public class Component {

    private String system;
    private String fileName;
    private Set<Button> opModeButtonSet;

    public HardwareMap map;
    public ConfigParser config;
    public Telemetry telemetry;

    public Component(HardwareMap map, Telemetry telemetry, String system) {
        this.map = map;
        this.system = system;
        this.fileName = system + ".omc";
        this.telemetry = telemetry;

        try {
            config = new ConfigParser(fileName);
        } catch(Exception e) {
            throw new IllegalArgumentException("CONFIG FILE NOT FOUND AT \""+fileName+"\"");
        }
    }

    public void setOpModeButtonSet(Set<Button> buttonSet) {
        this.opModeButtonSet = buttonSet;
    }

    public void addButtonToOpMode(Button b) {
        opModeButtonSet.add(b);
    }

    public String getFileName() {
        return fileName;
    }

    public String getSystemName() {
        return system;
    }
}
