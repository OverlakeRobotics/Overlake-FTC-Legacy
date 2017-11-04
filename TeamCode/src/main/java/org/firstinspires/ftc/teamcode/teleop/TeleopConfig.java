package org.firstinspires.ftc.teamcode.teleop;

/**
 * Created by EvanCoulson on 9/26/17.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Button;
import org.firstinspires.ftc.teamcode.robot.ConfigParser;
import org.firstinspires.ftc.teamcode.robot.Controller;
import org.firstinspires.ftc.teamcode.robot.FlickerSystem;

import java.util.concurrent.Callable;


@TeleOp(name="TeleOpConfig", group="TeleOp")
public class TeleopConfig extends OpMode {

    private ConfigParser c;
    private Controller controller1;

    public void init() {
        c = new ConfigParser("test.omc");
        telemetry.addData("all data", c.toString());
        telemetry.addData("i", c.getInt("i"));
        controller1.addButton(new Button() {
            @Override
            public boolean isPressed() {
                return gamepad1.a;
            }

            @Override
            public void pressedHandler() {
                telemetry.addData("pressed", "dogs");
            }

            @Override
            public void releasedHandler() {
                telemetry.addData("released", "dogs");
            }
        });
    }

    public void loop() {
        controller1.handle();
    }
}
