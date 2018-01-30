package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.teleop.ControllerOpMode;
import org.firstinspires.ftc.teamcode.util.config.*;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.teleop.ControllerOpMode;
import org.firstinspires.ftc.teamcode.util.config.*;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by jacks on 11/28/2017.
 */

public class ParallelLiftSystem {

        private ConfigParser config;
        private DigitalChannel parallelTouch;
        private DcMotor parallelMotor;
        private int bottom = 0;
        private int gameBottom;
        private int middle;
        private int top;
        private int encoderVal;
        private int position;
        private int initPosition;
        private boolean touched = false;
        private boolean isAtBottom = false;
        private boolean debouncing = false;
        private double negativePower = 0.4;
        private double positivePower = -0.4;

        private ElapsedTime debounceTime = new ElapsedTime();

        Telemetry telemetry;

        public ParallelLiftSystem(HardwareMap map, Telemetry telemetry) {
            this.telemetry = telemetry;
            this.config = new org.firstinspires.ftc.teamcode.util.config.ConfigParser("lifter.omc");
            this.parallelMotor = map.dcMotor.get("parallelMotor");
            this.parallelTouch = map.get(DigitalChannel.class, "parallelTouch");
            this.initPosition= config.getInt("init"); // A D D  T O  S T U F F

            middle = config.getInt("middle");
            top = config.getInt("top");
        }


        public void checkForBottom(){
            //On Rev, when configuring use the second input in digital
            parallelTouch.setMode(DigitalChannel.Mode.INPUT);
            boolean bottomSwitchPushed = !parallelTouch.getState();


            if(bottomSwitchPushed && !isAtBottom) {
                parallelMotor.setPower(0.0);
                encoderVal = parallelMotor.getCurrentPosition();
                position = bottom;
                isAtBottom = true;
                touched = true;
            } else if (isAtBottom) {
                if(!bottomSwitchPushed && !debouncing) {
                    debounceTime.reset();
                    debouncing = true;
                }
                if (debouncing && debounceTime.milliseconds() > 50) {
                    isAtBottom =  false; //(touchSensorBottom.getState() == false);
                    debouncing = false;
                }
            }
        }

        public void runMotorDown() {
            telemetry.addData("Is pressed: ", parallelTouch.getState());

            if(!touched) {
                parallelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                parallelMotor.setPower(positivePower);
                position = bottom;
            } else {
                parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                parallelMotor.setTargetPosition(encoderVal + 250);
                parallelMotor.setPower(positivePower);
                position = bottom + 250;
            }

        }



        public void goToMiddle() {
            telemetry.addData("Is pressed: ", parallelTouch.getState());


            parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            parallelMotor.setTargetPosition(encoderVal + middle);
            if(position > middle) {
                parallelMotor.setPower(negativePower);
            } else {
                parallelMotor.setPower(positivePower);

            }
            position = middle;
        }

        public void goToTop() {
        telemetry.addData("Is pressed: ", parallelTouch.getState());


        parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        parallelMotor.setTargetPosition(encoderVal + top);
        if(position > top) {
            parallelMotor.setPower(negativePower);
        } else {
            parallelMotor.setPower(positivePower);

        }
        position = top;
    }

    public void goToInitPosition() {
        telemetry.addData("Is pressed: ", parallelTouch.getState());


        parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        parallelMotor.setTargetPosition(encoderVal + initPosition);
        if(position > top) {
            parallelMotor.setPower(negativePower);
        } else {
            parallelMotor.setPower(positivePower);

        }
        position = top;
    }

        public void isPressed(){
            telemetry.addData("Is Pressed: ", parallelTouch.getState());
        }

    }


