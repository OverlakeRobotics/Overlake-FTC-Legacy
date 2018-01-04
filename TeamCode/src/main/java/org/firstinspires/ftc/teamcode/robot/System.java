package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by EvanCoulson on 10/5/17.
 */

enum LoggingService {
    TELEMETRY,
    FILE,
}

public abstract class System {
    private LoggingService[] loggingServices;
    private String system;
    private String fileName;

    public HardwareMap map;
    public ConfigParser config;
    public Telemetry telemetry;

    public System(OpMode opMode, String system) {
        this.loggingServices = new LoggingService[] { LoggingService.FILE };
        this.map = opMode.hardwareMap;
        this.system = system;
        this.fileName = system + ".omc";
        this.telemetry = opMode.telemetry;


        try {
            config = new ConfigParser(fileName);
        } catch(Exception e) {
            throw new IllegalArgumentException("CONFIG FILE NOT FOUND AT \""+fileName+"\"");
        }
    }

    public void setDefaultServices(LoggingService... services) {
        this.loggingServices = new LoggingService[services.length];
        for (int i = 0; i < services.length; i++) {
            loggingServices[i] = services[i];
        }
    }

    public void log(String level, String data) {
        log(level, data, this.loggingServices);
    }

    public void log(String level, String data, LoggingService[] loggingServices) {
        for (LoggingService service : this.loggingServices) {
            switch (service) {
                case FILE:
                    logFile(level, data);
                    break;
                case TELEMETRY:
                    logTelemetry(level, data);
                    break;
            }
        }
    }

    private void logFile(String level, String data) {

    }

    private void logTelemetry(String level, String data) {
        telemetry.addData();
    }


    public String getFileName() {
        return fileName;
    }

    public String getSystemName() {
        return system;
    }
}
