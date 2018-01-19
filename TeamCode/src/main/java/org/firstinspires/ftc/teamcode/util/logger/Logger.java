package org.firstinspires.ftc.teamcode.util.logger;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by EvanCoulson on 1/5/18.
 */

public class Logger {
    private LoggingService[] loggingServices;
    private FileLogger fileLogger;
    private Telemetry telemetry;
    private String system;

    public Logger(OpMode mode, String system) {
        this.loggingServices = new LoggingService[] { LoggingService.TELEMETRY };
        this.fileLogger = new FileLogger(system);
        this.telemetry = mode.telemetry;
        this.system = system;
    }

    public void setLoggingServices(LoggingService... services) {
        this.loggingServices = services;
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
}
