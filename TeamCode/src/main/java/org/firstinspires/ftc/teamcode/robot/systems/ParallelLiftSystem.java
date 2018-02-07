package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.systems.System;
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

public class ParallelLiftSystem extends System {
        private DigitalChannel parallelTouch;
        private DcMotor parallelMotor;
        private int bottom = 0;
        private int middle;
        private int park;
        private int top;
        private int encoderVal;
        private int position;
        private int initPosition;
        private int incrementTicks = 20;
        private int loadPosition = bottom + 250;
        int[] positions = new int[3];
        private boolean touched = false;
        private boolean isAtBottom = false;
        private boolean debouncing = false;
        private double negativePower = 0.4;
        private double positivePower = -0.4;
        Integer i;
        int positionIndex = 0;
        private ElapsedTime debounceTime = new ElapsedTime();

        Telemetry telemetry;

        public ParallelLiftSystem(OpMode mode) {
            super(mode, "lifter");

            for(i = 0; i < positions.length; i++) {
                positions[i] = config.getInt("ParallelLift" + i.toString());
            }
            this.parallelMotor = map.dcMotor.get("parallelMotor");
            this.parallelTouch = map.get(DigitalChannel.class, "parallelTouch");
            initPosition = config.getInt("init"); // A D D  T O  S T U F F
            park = config.getInt("park"); // A D D  T O  S T U F F
            middle = config.getInt("middle");
            top = config.getInt("top");
        }

        public void parallelLiftLoop() {
            if(Math.abs(position-parallelMotor.getCurrentPosition()) < 20){
                parallelMotor.setPower(0.0);
            }

        }
        public void positionUp() {
            telemetry.addData("PosititionUp called: position is at " , Double.toString(position));

            if(positionIndex < positions.length-1) {
                positionIndex++;
                position = positions[positionIndex];
                telemetry.addData("Set to Position: " , position);
                parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                parallelMotor.setTargetPosition(encoderVal + position);
                parallelMotor.setPower(negativePower);
            }
        }

         public void positionDown() {
             telemetry.addData("PosititionUp called: position is at " , Double.toString(position));
             telemetry.addLine();
             if(positionIndex >= 1) {
                positionIndex--;
                position = positions[positionIndex];
                telemetry.addData("Set to Position: " , position);
                parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                parallelMotor.setTargetPosition(encoderVal + position);
                parallelMotor.setPower(positivePower);
            }
        }

        public void incrementUp() {
            parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            parallelMotor.setTargetPosition(encoderVal + position + incrementTicks);
            telemetry.addData("Current encoder value: ", position + incrementTicks);
            parallelMotor.setPower(0.4);
            position = position + incrementTicks;
        }
        public void incrementDown() {
            parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            parallelMotor.setTargetPosition(encoderVal + position - incrementTicks);
            telemetry.addData("Current encoder value: ", position - incrementTicks);
            parallelMotor.setPower(-0.4);
            position = position - incrementTicks;
        }

        public void setPosition() {
            String stringVal = Double.toString(position);
            config.updateKey("ParallelLift" + i.toString(), stringVal);
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
            runMotorDown(positivePower);
        }

        public void runMotorDown(double power) {
            telemetry.addData("Is pressed: ", parallelTouch.getState());

            if(!touched) {
                parallelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                parallelMotor.setPower(power);
                position = bottom;
            } else {
                parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                parallelMotor.setTargetPosition(encoderVal + 250);
                parallelMotor.setPower(power);
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
        if(position > initPosition) {
            parallelMotor.setPower(negativePower);
        } else {
            parallelMotor.setPower(positivePower);

        }
        position = initPosition;
    }

    public void goToPark() {
        telemetry.addData("Is pressed: ", parallelTouch.getState());

        parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        parallelMotor.setTargetPosition(encoderVal + park);
        if(position > park) {
            parallelMotor.setPower(negativePower);
        } else {
            parallelMotor.setPower(positivePower);

        }
        position = park;
    }

        public void isPressed(){
            telemetry.addData("Is Pressed: ", parallelTouch.getState());
        }

    }


