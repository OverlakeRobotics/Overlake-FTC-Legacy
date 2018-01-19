package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.controller.Controller;
import org.firstinspires.ftc.teamcode.robot.systems.MecanumDriveSystem;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;
import org.firstinspires.ftc.teamcode.util.logger.Logger;
import org.firstinspires.ftc.teamcode.util.logger.LoggingService;

/**
 * Created by EvanCoulson on 10/11/17.
 */

public abstract class BaseOpMode extends OpMode {
    protected final ConfigParser config;
    protected final Controller controller1;
    protected final Controller controller2;
    protected final MecanumDriveSystem driveSystem;

    public BaseOpMode(String opModeName) {
        Logger logger = new Logger(this, "baseOp");
        logger.setLoggingServices(LoggingService.FILE);
        this.controller1 = new Controller(gamepad1);
        this.controller2 = new Controller(gamepad2);
        this.driveSystem = new MecanumDriveSystem(this);
        logger.log("here 1");
        this.config = new ConfigParser(opModeName + ".omc");
        logger.log("here 2");

        telemetry.setMsTransmissionInterval(200);
    }
}
