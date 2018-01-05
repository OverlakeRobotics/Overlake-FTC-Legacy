package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.util.config.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.teleop.ControllerOpMode;

/**
 * Created by jacks on 11/28/2017.
 */



public class ParallelLiftSystem extends System {
    private DcMotorServo motor;
    private double bottom;
    private double middle;
    private double top;


    public ParallelLiftSystem(OpMode opMode) {
        super(opMode, "ParallelLiftSystem");
        this.motor = new DcMotorServo(this.map, "lifter", "potentiometer");
        bottom = config.getDouble("bottom");
        middle = config.getDouble("middle");
        top  = config.getDouble("top");
    }

    public void loop() {
        motor.loop();
    }

    public void goToBottom() {
        telemetry.addData("current position: ", motor.getCurrentPosition());
        //motor.targetPosition = 0.0;
    }

    public void goToMiddle() {
        telemetry.addData("current position: ", motor.getCurrentPosition());
        //motor.targetPosition = 0.5;
    }
    public void goToTop() {
        telemetry.addData("current position: ", motor.getCurrentPosition());
        //motor.targetPosition = 1.0;
    }
}
