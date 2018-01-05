package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.controller.Controller;
import org.firstinspires.ftc.teamcode.robot.systems.MecanumDriveSystem;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by EvanCoulson on 10/11/17.
 */

public abstract class BaseOpMode extends OpMode {
    protected ConfigParser config;
    protected Controller controller1;
    protected Controller controller2;
    protected MecanumDriveSystem driveSystem;

    public BaseOpMode(String opModeName) {
        this.controller1 = new Controller(gamepad1);
        this.controller2 = new Controller(gamepad2);
        this.driveSystem = new MecanumDriveSystem(this);
        config = new ConfigParser(opModeName + ".omc");
    }
}
