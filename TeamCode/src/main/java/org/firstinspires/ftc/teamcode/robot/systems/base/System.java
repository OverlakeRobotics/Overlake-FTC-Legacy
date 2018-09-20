package org.firstinspires.ftc.teamcode.robot.systems.base;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.ConfigParser;
import org.firstinspires.ftc.teamcode.logger.Logger;
import org.firstinspires.ftc.teamcode.logger.LoggingService;

/**
 * Created by EvanCoulson on 10/5/17.
 */

public abstract class System {

    private String system;
    private String fileName;

    protected HardwareMap map;
    public ConfigParser config;
    public Telemetry telemetry;

    public Logger logger;

    public System(OpMode opMode, String systemName) {
        this.map = opMode.hardwareMap;
        this.system = systemName;
        this.fileName = systemName + ".omc";
        this.telemetry = opMode.telemetry;
        this.config = new ConfigParser(fileName);
        this.logger = new Logger(opMode, systemName);
    }

    public void setDefaultLoggingServices(LoggingService... services) {
        LoggingService[] loggingServices = new LoggingService[services.length];
        for (int i = 0; i < services.length; i++) {
            loggingServices[i] = services[i];
        }
        this.logger.setLoggingServices(loggingServices);
    }

    public String getFileName() {
        return fileName;
    }

    public String getSystemName() {
        return system;
    }
}