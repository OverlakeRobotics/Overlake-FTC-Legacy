package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.teleop.ControllerOpMode;
import org.firstinspires.ftc.teamcode.util.config.*;

/**
 * Created by jacks on 11/28/2017.
 */

public class ParellelLiftSystem {

   /* public org.firstinspires.ftc.teamcode.util.config.ConfigParser config;

    private DcMotorServo motor;

    private double bottom;

    private double middle;

    private double top;

    Telemetry telemetry;

    public ParellelLiftSystem(ControllerOpMode opMode) {
        this.telemetry = telemetry;
        this.config = new org.firstinspires.ftc.teamcode.util.config.ConfigParser("Lifter.omc");
        this.motor = new DcMotorServo();
        this.motor.init(opMode.hardwareMap, "lifter", "potentiometer");
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
    }*/
}
