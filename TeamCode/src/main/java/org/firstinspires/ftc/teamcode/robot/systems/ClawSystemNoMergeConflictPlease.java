package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.robot.components.DcMotorServo;
import org.firstinspires.ftc.teamcode.teleop.ControllerOpMode;
import org.firstinspires.ftc.teamcode.util.config.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by jacks on 1/8/2018.
 */
public class ClawSystemNoMergeConflictPlease extends System {

    private DcMotorServo motor;

    double bottom;
    double middle;
    double top;
    double position;

    Telemetry telemetry;

    Telemetry.Line liftTelemetryLine;
    Telemetry.Item indexTelemetryItem;
    Telemetry.Item positionTelemetryItem;

    //public Claw2png(OpMode opMode, Telemetry telemetry){
    public ClawSystemNoMergeConflictPlease(OpMode opMode){
        super(opMode, "meMotor");
        this.telemetry = opMode.telemetry;
        this.motor = new DcMotorServo();
        this.motor.init(opMode.hardwareMap, "meMotor", "potentiometer", telemetry);
        this.liftTelemetryLine = this.telemetry.addLine("Claw");
        this.indexTelemetryItem = liftTelemetryLine.addData("position", 0);
        this.positionTelemetryItem = liftTelemetryLine.addData("power", 0);
        bottom = config.getDouble("bottom");
        middle = config.getDouble("middle");
        top  = config.getDouble("top");
        position = 0.45;
    }

    public void loop(){
        this.positionTelemetryItem.setValue(motor.getPower());
        this.indexTelemetryItem.setValue(motor.getCurrentPosition());

        motor.loop(position);

    }
    public void managePosition() {

    }


    public void goToBottom() {

        position = bottom;
    }
    public void closeSynch() {
        while(Math.abs(position -  motor.getCurrentPosition()) > 0.05){
            runMotorBack();
        }
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

