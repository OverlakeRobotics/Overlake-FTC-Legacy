package org.firstinspires.ftc.teamcode.teleop;

/**
 * Created by EvanCoulson on 9/26/17.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.ConfigParser;


@TeleOp(name="TeleOpConfig", group="TeleOp")
public class TeleopConfig extends OpMode {

    private ConfigParser c;
    public void init() {
        c = new ConfigParser("test.omc");
        telemetry.addData("all data", c.toString());
        telemetry.addData("i", c.getInt("i"));
    }

    public void loop() {

    }
}
