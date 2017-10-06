package org.firstinspires.ftc.teamcode.robot;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by EvanCoulson on 10/5/17.
 */

public class System {

    private String system;
    private String fileName;
    public HardwareMap map;
    public ConfigParser config;

    public System(HardwareMap map, String system) {
        this.map = map;
        this.system = system;
        this.fileName = system + ".omc";

        try {
            config = new ConfigParser(fileName);
        } catch(Exception e) {

        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getSystemName() {
        return system;
    }

}
