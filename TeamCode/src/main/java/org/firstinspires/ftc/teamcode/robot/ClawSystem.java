package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

/**
 * Created by jacks on 10/19/2017.
 */

public class ClawSystem {
    ConfigParser config = new ConfigParser("Claw.omc");
    private Servo claw;
    //0.5 is the center point
    //one rotation on small gear is a value of 4.5
    //right trigger
    private double LOAD_POSITION; //0.320;
    //left trigger
    private double RELEASE_POSITION;//0.345;
    //a
    private double REST_POSITION; //0.5;

    private ClawSystem.ServoPositions position;

    public double servoAngle;


    public ClawSystem(HardwareMap map) {

        this.config = new ConfigParser("Claw.omc");

        this.claw = map.servo.get("claw");
        ServoImplEx servo = (ServoImplEx)this.claw;
        ServoControllerEx controller = (ServoControllerEx) servo.getController();
        int portNumber = servo.getPortNumber();
        controller.setServoPwmRange(portNumber, new PwmControl.PwmRange(1000, 2000));
        LOAD_POSITION = config.getDouble("load_position");
        RELEASE_POSITION = config.getDouble("release_position");
        REST_POSITION  = config.getDouble("rest_position");
        //this.colorSensor = map.colorSensor.get("flickerColorDetector");
        this.position = ServoPositions.CLAWREST;
        servoAngle = LOAD_POSITION;
    }
    public enum ServoPositions {
        CLAWLOAD,
        CLAWRELEASE,
        CLAWREST,

    }
    public void goToLoadPosition() {
        servoAngle = LOAD_POSITION;
        claw.setPosition(servoAngle);
        //.5 or .6
        this.position = ServoPositions.CLAWLOAD;
    }

    public void goToReleasePosition() {
        servoAngle = RELEASE_POSITION;
        claw.setPosition(servoAngle);
        this.position = ServoPositions.CLAWRELEASE;
    }

    public void goToRestPosition() {
        servoAngle = REST_POSITION;
        claw.setPosition(servoAngle);
        this.position = ServoPositions.CLAWREST;
    }

    public void incrementServo() {
        servoAngle += 0.01;
        claw.setPosition(servoAngle);
    }

    public void decrementServo() {
        servoAngle -= 0.01;
        claw.setPosition(servoAngle);
    }

    public void setLoadPosition() {
        LOAD_POSITION = claw.getPosition();

    }

    public void setReleasePosition() {
        RELEASE_POSITION = claw.getPosition();
    }






}
