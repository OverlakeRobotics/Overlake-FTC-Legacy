package org.firstinspires.ftc.teamcode.robot;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;
import org.firstinspires.ftc.teamcode.util.logger.FileLogger;

/**
 * Created by EvanCoulson on 10/5/17.
 */

enum LoggingService {
    TELEMETRY,
    FILE,
    LOGCAT,
}

public abstract class System {
    private LoggingService[] loggingServices;
    private FileLogger fileLogger;
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
        this.fileLogger = new FileLogger(this.system);
        this.telemetry = opMode.telemetry;
        this.config = new ConfigParser(fileName);
    }

    public void setDefaultServices(LoggingService... services) {
        this.loggingServices = new LoggingService[services.length];
        for (int i = 0; i < services.length; i++) {
            loggingServices[i] = services[i];
        }
    }

    public void log(String data) {
        log(data, this.loggingServices);
    }

    public void log(String data, LoggingService[] loggingServices) {
        for (LoggingService service : this.loggingServices) {
            switch (service) {
                case FILE:
                    this.fileLogger.log(system, data);
                    break;
                case TELEMETRY:
                    this.telemetry.addData(system, data);
                    break;
                case LOGCAT:
                    Log.i(system, data);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown Logging Serivce " + service);
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getSystemName() {
        return system;
    }
}
