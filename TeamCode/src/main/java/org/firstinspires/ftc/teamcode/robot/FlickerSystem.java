package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorAdafruitRGB;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by jacks on 11/7/2016.
 *
 *
 */

//              ___                   ___           ___           ___           ___           ___                    ___                       ___                       ___           ___
//             /  /\                  /  /|         /  /\         /__/|         /  /\         /  /\                  /  /\          ___        /  /\          ___        /  /\         /__/\
//            /  /:/_                /  /:/        /  /:/        |  |:|        /  /:/_       /  /::\                /  /:/_        /__/|      /  /:/_        /  /\      /  /:/_       |  |::\
//           /  /:/ /\  ___         /  /:/        /  /:/         |  |:|       /  /:/ /\     /  /:/\:\              /  /:/ /\      |  |:|     /  /:/ /\      /  /:/     /  /:/ /\      |  |:|:\
//          /  /:/ /:/ /__/\       /  /::\ __    /  /:/  ___   __|  |:|      /  /:/ /:/_   /  /:/~/:/             /  /:/ /::\     |  |:|    /  /:/ /::\    /  /:/     /  /:/ /:/_   __|__|:|\:\
//         /__/:/ /:/  \  \:\     /  / /\:\  /\ /__/:/  /  /\ /__/\_|:|____ /__/:/ /:/ /\ /__/:/ /:/___          /__/:/ /:/\:\  __|__|:|   /__/:/ /:/\:\  /  /::\    /__/:/ /:/ /\ /__/::::| \:\
//         \  \:\/:/   \  \:\ __ /__/ / \:\/ /  \  \:\ /  /:/ \  \:\/:::::/ \  \:\/:/ /:/ \  \:\/:::::/          \  \:\/:/~/:/ /__/::::\   \  \:\/:/~/:/ /__/:/\:\   \  \:\/:/ /:/ \  \:\~~\__\/
//         \  \::/     \  \:\/:| \__\/__\: /    \  \:\  /:/   \  \::/~~~~   \  \::/ /:/   \  \::/~~~~            \  \::/ /:/     ~\~~\:\   \  \::/ /:/  \__\/  \:\   \  \::/ /:/   \  \:\
//         \  \:\      \  \:: /     /  /: /     \  \:\/:/     \  \:\        \  \:\/:/     \  \:\                 \__\/ /:/        \  \:\   \__\/ /:/        \  \:\   \  \:\/:/     \  \:\
//         \  \:\      \  \ /      /__/  /      \  \::/       \  \:\        \  \::/       \  \:\                  /__/:/          \__\/     /__/:/          \__\/    \  \::/       \  \:\
//         \__\/       \__\/       \__\/        \__\/         \__\/         \__\/         \__\/                  \__\/                     \__\/                     \__\/         \__\/

public class FlickerSystem {
    //ticks per rotation for a 60 to 1 motor
    private final int ticksPerRotation = 1120 * 3 / 2;
    private final double FLICKER_POWER = 0.8;
    private final double SHOOT_POSITION = 0.5;
    private final double LOAD_POSITION = 0.44;

    private final int BASE_RED = 1;
    private final int BASE_GREEN = 1;
    private final int BASE_BLUE = 1;

    private DcMotor flicker;
    private Servo loadWing;
    private ColorSensor colorSensor;
    private ServoPositions position;

    public FlickerSystem(HardwareMap map) {
        this.flicker = map.dcMotor.get("flicker");
        this.loadWing = map.servo.get("flickerLoad");
        //this.colorSensor = map.colorSensor.get("flickerColorDetector");
        this.position = ServoPositions.FLICKERLOAD;
        flicker.setDirection(DcMotor.Direction.REVERSE);
    }

    public void shoot() {
        flicker.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        flicker.setTargetPosition(flicker.getCurrentPosition() + revolutionsToTics(1));
        flicker.setPower(FLICKER_POWER);
    }

    public void start() {
        flicker.setPower(FLICKER_POWER);
    }

    public void stop() {
        flicker.setPower(0.0);
    }

    public void setLoadPosition() {
        loadWing.setPosition(SHOOT_POSITION);
        //.5 or .6
        this.position = ServoPositions.FLICKERLOAD;
    }

    public void setShootPosition() {
        loadWing.setPosition(LOAD_POSITION);
        this.position = ServoPositions.FLICKERSHOOT;
    }

    public void togglePosition() {
        if (this.position == ServoPositions.FLICKERLOAD) {
            setShootPosition();
        } else {
            setLoadPosition();
        }
    }

    public boolean isBallLoaded() {
        /*
        return colorSensor.red() == BASE_RED && colorSensor.green() == BASE_GREEN && colorSensor.blue() == BASE_BLUE || position == ServoPositions.FLICKERSHOOT;
         */
        return true;
    }

    private int revolutionsToTics(double revolutions) {
        return (int) Math.round(revolutions * this.ticksPerRotation);
    }

    public boolean isBusy() {
        return flicker.isBusy();
    }

    public enum ServoPositions {
        FLICKERLOAD,
        FLICKERSHOOT
    }
}
