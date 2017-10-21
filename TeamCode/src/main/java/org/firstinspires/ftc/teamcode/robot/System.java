package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by EvanCoulson on 10/5/17.
 */

public abstract class System {

    private String system;
    private String fileName;

    public HardwareMap map;
    public ConfigParser config;
    public Telemetry telemetry;

    public System(HardwareMap map, Telemetry telemetry, String system) {
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

    public String getFileName() {
        return fileName;
    }

    public String getSystemName() {
        return system;
    }

    public void setPIDCoefficients(String hardwareMapKey, double newP, double newI, double newD) {
//        DcMotorEx motor = (DcMotorEx)map.get(DcMotor.class, hardwareMapKey);
//        PIDCoefficients newPID = new PIDCoefficients(newP, newI, newD);
//        motor.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, newPID);
    }
}
