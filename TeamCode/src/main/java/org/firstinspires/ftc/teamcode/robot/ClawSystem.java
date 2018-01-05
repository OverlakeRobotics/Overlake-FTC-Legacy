package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by jacks on 10/19/2017.
 */

public class ClawSystem {
    public ConfigParser config;
    private Servo claw;
    //0.5 is the center point
    //one rotation on small gear is a value of 4.5
    //right trigger
    private double LOAD_POSITION; //0.320;
    //left trigger
    private double RELEASE_POSITION;//0.345;
    //TODO: this is added to controller 2 right bumber
    private double PINCH_POSITION; //TODO: find this value; implement in below todo

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

        //TODO: For competition please change this to pinch_ position and configure the correct value in the Config app.
        //To change the double value:
        //run Teleop and press and hold LBumper then press RBumper on controller 2.
        //      adjust this position but using the L and R dpad on Controller 2
        //or just add a postion to the config on the controller phone
        PINCH_POSITION  = config.getDouble("pinch_position");

        //this.colorSensor = map.colorSensor.get("flickerColorDetector");
        this.position = ServoPositions.CLAWRELEASE;
        servoAngle = LOAD_POSITION;
    }
    public enum ServoPositions {
        CLAWLOAD,
        CLAWRELEASE,
        CLAWPINCH,

    }
    public void goToLoadPosition() {
        servoAngle = LOAD_POSITION;
        claw.setPosition(servoAngle);
        this.position = ServoPositions.CLAWLOAD;
    }

    public void goToReleasePosition() {
        servoAngle = RELEASE_POSITION;
        claw.setPosition(servoAngle);
        this.position = ServoPositions.CLAWRELEASE;
    }

    public void goToPinchPosition() {
        servoAngle = PINCH_POSITION;
        claw.setPosition(servoAngle);
        this.position = ServoPositions.CLAWPINCH;
    }

    public void goToPosition(int angle) {
        //use only for autonomous
        servoAngle = angle;
        claw.setPosition(servoAngle);

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
        double ticks = claw.getPosition();
        String tickString = Double.toString(ticks);
        LOAD_POSITION = claw.getPosition();
        config.updateKey("load_position", tickString);
    }

    public void setReleasePosition() {
        double releaseTicks = claw.getPosition();
        String tickStrings= Double.toString(releaseTicks);
        RELEASE_POSITION = claw.getPosition();
        config.updateKey("release_position", tickStrings);
    }

    public void setPinchPosition() {
        double releaseTicks = claw.getPosition();
        String tickStrings = Double.toString(releaseTicks);
        PINCH_POSITION = claw.getPosition();
        //TODO: change this in the config too pls
        config.updateKey("pinch_position", tickStrings);
    }






}
