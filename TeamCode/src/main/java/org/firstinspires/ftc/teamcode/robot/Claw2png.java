package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.teleop.ControllerOpMode;
import org.firstinspires.ftc.teamcode.util.config.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by jacks on 1/8/2018.
 */
public class Claw2png {

    private DcMotorServo motor;

    double bottom;
    double middle;
    double top;
    double position;

    ConfigParser config;

    Telemetry telemetry;

    public Claw2png(OpMode opMode, Telemetry telemetry){
        this.telemetry = opMode.telemetry;
        this.config = new org.firstinspires.ftc.teamcode.util.config.ConfigParser("meMotor.omc");
        this.motor = new DcMotorServo();
        this.motor.init(opMode.hardwareMap, "meMotor", "potentiometer", telemetry);
        bottom = config.getDouble("bottom");
        middle = config.getDouble("middle");
        top  = config.getDouble("top");
        position = 0.2;
    }

    public void loop(){
        telemetry.addData("current position: ", motor.getCurrentPosition());
        telemetry.addData("target position: ", position);
        motor.loop(position);
    }
    public void managePosition() {

    }

    public void goToBottom() {
        position = bottom;
    }

    public void goToMiddle() {
        position = middle;
    }
    public void goToTop() {
        position = top;
    }
    public void runMotor(){
        motor.runMotor();
    }
    public void runMotorBack(){
        motor.runMotorBack();
    }
    public void stop() {
        motor.stop();

    }
}

