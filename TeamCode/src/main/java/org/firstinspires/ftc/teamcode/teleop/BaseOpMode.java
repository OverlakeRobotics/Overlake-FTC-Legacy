package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.Controller;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by EvanCoulson on 10/11/17.
 */

public abstract class BaseOpMode extends OpMode {
    private ConfigParser config;
    private Controller controller1;
    private Controller controller2;

    public BaseOpMode(String opModeName) {
        this.controller1 = new Controller(gamepad1);
        this.controller2 = new Controller(gamepad2);
        try {
            config = new ConfigParser(opModeName + ".omc");
        } catch (Exception e) {
            throw new IllegalArgumentException("OP MODE CONFIGURATION NOT FOUND \"" + opModeName + "\"");
        }
    }
}
