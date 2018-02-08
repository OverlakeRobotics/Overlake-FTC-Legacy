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
        public static final int bottomIndex = 0;
        public static final int middleIndex = 1;
        public static final int topIndex = 2;
        private boolean touched = false;
        private boolean isAtBottom = false;
        private boolean debouncing = false;
        private double negativePower = 0.4;
        private double positivePower = -0.4;
        int positionIndex = 0;
        private ElapsedTime debounceTime = new ElapsedTime();

        Telemetry.Line liftTelemetryLine;
        Telemetry.Item indexTelemetryItem;
        Telemetry.Item positionTelemetryItem;
        //public ParallelLiftSystem(HardwareMap map, Telemetry telemetry) {

        public ParallelLiftSystem(OpMode mode) {
            super(mode, "lifter");
            this.telemetry.setAutoClear(false);
            this.liftTelemetryLine = this.telemetry.addLine("liftlift");

            this.liftTelemetryLine = this.telemetry.addLine("lift");
            this.indexTelemetryItem = liftTelemetryLine.addData("index", 0);
            this.positionTelemetryItem = liftTelemetryLine.addData("position", 0);
            this.config = new org.firstinspires.ftc.teamcode.util.config.ConfigParser("lifter.omc");
            for(Integer i = 0; i < positions.length; i++) {
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

        private void setTargetPosition(int ticks) {
            this.positionTelemetryItem.setValue(ticks);
            this.position = ticks;
            parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            parallelMotor.setTargetPosition(encoderVal + position);
            parallelMotor.setPower(positivePower);
        }

        private void setTargetIndex(int index) {
            this.indexTelemetryItem.setValue(index);
            this.positionIndex = index;
            setTargetPosition(this.positions[index]);
        }

        public void positionUp() {
            if(positionIndex < positions.length-1) {
                positionIndex++;
                setTargetIndex(positionIndex);
            }
        }

         public void positionDown() {
             if(positionIndex >= 1) {
                positionIndex--;
                setTargetIndex(positionIndex);
            }
        }

        public void incrementUp() {
            setTargetPosition(position + incrementTicks);
            position = position + incrementTicks;
        }
        public void incrementDown() {
            setTargetPosition(position - incrementTicks);
            position = position - incrementTicks;

        }

        public void setPosition() {
            String stringVal = Double.toString(position);
            config.updateKey("ParallelLift" + Integer.toString(positionIndex), stringVal);
            positions[positionIndex] = position;
        }

        public void checkForBottom(){
            //On Rev, when configuring use the second input in digital
            parallelTouch.setMode(DigitalChannel.Mode.INPUT);
            boolean bottomSwitchPushed = !parallelTouch.getState();


            if(bottomSwitchPushed && !isAtBottom) {
                parallelMotor.setPower(0.0);
                encoderVal = parallelMotor.getCurrentPosition();
                position = bottom;
                positionIndex = 0;
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
            // TODO: telemetry.addData("Is pressed: ", parallelTouch.getState());

            // TODO: What is this trying to do?
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
            // TODO: telemetry.addData("Is pressed: ", parallelTouch.getState());


            setTargetPosition(middle); //TODO: This really should be setTargetIndex(middleIndex);
        }

        public void goToTop() {
            // TODO: telemetry.addData("Is pressed: ", parallelTouch.getState());

            setTargetPosition(top); //TODO: This really should be setTargetIndex(topIndex);
        }

    public void goToInitPosition() {
        // TODO: telemetry.addData("Is pressed: ", parallelTouch.getState());

        setTargetPosition(initPosition); //TODO: This really should be setTargetIndex(initIndex);
    }

    public void goToPark() {
        // TODO: telemetry.addData("Is pressed: ", parallelTouch.getState());

        setTargetPosition(park); //TODO: This really should be setTargetIndex(parkIndex);
    }

        public void isPressed(){
            telemetry.addData("Is Pressed: ", parallelTouch.getState());
        }

}


