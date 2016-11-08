package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

/**
 * Created by jacks on 11/7/2016.
 */

public class FlickerSystem {
    private DcMotor flicker;
    private Servo loadServo;
    private ServoPositions position;

    public FlickerSystem(HardwareMap map) {
        this.flicker = map.dcMotor.get("flicker");
        this.loadServo = map.servo.get("flickerLoad");
        this.position = ServoPositions.FLICKERLOAD;
    }

    public void shoot() {
        if (!flicker.isBusy()) {
            setShootPosition();
            flicker.setTargetPosition(flicker.getCurrentPosition() + 1120);
            flicker.setDirection(DcMotor.Direction.FORWARD);
            flicker.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            flicker.setPower(0.8);
            setLoadPosition();
        }
    }

    public void setLoadPosition() {
        loadServo.setDirection(Servo.Direction.FORWARD);
        loadServo.setPosition(45);
        this.position = ServoPositions.FLICKERLOAD;
    }

    public void setShootPosition() {
        loadServo.setDirection(Servo.Direction.REVERSE);
        loadServo.setPosition(315);
        this.position = ServoPositions.FLICKERLOAD;
    }

    // Possibly Unnessecary.
    public void toggleLoadPosition() {
        if (position == ServoPositions.FLICKERLOAD) { //Toggle to flicker shoot
            loadServo.setDirection(Servo.Direction.REVERSE);
            loadServo.setPosition(315); //(In Degrees, also placeholer)
            position = ServoPositions.FLICKERSHOOT;
        } else { //Toggle to flicker load
            loadServo.setDirection(Servo.Direction.FORWARD);
            loadServo.setPosition(45); //(In Degrees, also placeholer)
            position = ServoPositions.FLICKERLOAD;
        }
    }

    private enum ServoPositions {
        FLICKERLOAD,
        FLICKERSHOOT
    }

}
