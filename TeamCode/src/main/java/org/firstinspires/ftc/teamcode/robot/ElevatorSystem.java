package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by jacks on 10/20/2017.
 */

public class ElevatorSystem {
    private ConfigParser config;
    private DcMotor elevator;
    private DigitalChannel touchSensorBottom;
    private DigitalChannel touchSensorTop;
    int encoderVal;
    int position;
    private int loadPos1Ticks = 0;
    private int loadPos2Ticks = 1200;
    private int unloadStackTicks = 350;
    private int unloadPos1Ticks = 800;

    private int unloadPos2Ticks = 1200;
    private int unloadPos3Ticks = 1400;

    private boolean debouncing = false;
    private ElapsedTime debounceTime = new ElapsedTime();


    boolean isAtTop = false;
    boolean isAtBottom = false;
    private double negativePower = -0.3;
    private double positivePower = 0.3;

    Telemetry telemetry;

    public ElevatorSystem(HardwareMap map, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.elevator = map.dcMotor.get("elevator");
        this.touchSensorBottom = map.get(DigitalChannel.class, "touchBottom");
        this.touchSensorTop = map.get(DigitalChannel.class, "touchTop");
        elevator.setDirection(DcMotor.Direction.REVERSE);

    }

    //for autonomous
    public void goToZero(Telemetry telemetry){

        //On Rev, when configuring use the second input in digital
        touchSensorBottom.setMode(DigitalChannel.Mode.INPUT);

        while(touchSensorBottom.getState()==true) {
            telemetry.addData("touch Sensor" , touchSensorBottom.getState());
            elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elevator.setPower(-0.3);

        }
        elevator.setPower(0.0);
        encoderVal = elevator.getCurrentPosition();
        position = 0;

    }

    public void runMotorDown() {
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevator.setPower(negativePower);

    }

    public void runMotorUp() {
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevator.setPower(positivePower);

    }

    public void checkForBottom(Telemetry telemetry){

        //On Rev, when configuring use the second input in digital
        touchSensorBottom.setMode(DigitalChannel.Mode.INPUT);
        boolean bottomSwitchPushed = (touchSensorBottom.getState() == false);
        if( bottomSwitchPushed&& !isAtBottom) {
            elevator.setPower(0.0);
            encoderVal = elevator.getCurrentPosition();
            position = loadPos1Ticks;
            isAtBottom = true;

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

    public void checkForTop() {       //On Rev, when configuring use the second input in digital
        touchSensorTop.setMode(DigitalChannel.Mode.INPUT);
        boolean topSwitchPushed = (touchSensorTop.getState() == false);
        if( topSwitchPushed&& !isAtTop) {
            elevator.setPower(0.0);
            isAtTop = true;
        } else if (isAtTop) {

            if (!debouncing) {
                debounceTime.reset();
                debouncing = true;
            }
            else {
                if (debounceTime.milliseconds() > 50) {
                    isAtTop =  false; //(touchSensorBottom.getState() == false);
                    debouncing = false;
                }
            }

        }
    }


//dpad up
    public void goToLoadPos2() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevator.setTargetPosition(encoderVal + loadPos2Ticks);
        telemetry.addData("position: " , position);
        telemetry.addData("to: ", loadPos2Ticks);
        if(position > loadPos2Ticks) {

            elevator.setPower(negativePower);
        } else {
            elevator.setPower(positivePower);

        }
        position = loadPos2Ticks;
    }

    //B button
    public void goToUnloadPos1() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setTargetPosition(encoderVal + unloadPos1Ticks);
        if(position > unloadPos1Ticks) {
            elevator.setPower(negativePower);
        } else {
            elevator.setPower(positivePower);

        }

        position = unloadPos1Ticks;
    }

    //X button
    public void goToUnloadPos2() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevator.setTargetPosition(encoderVal + unloadPos2Ticks);
        telemetry.addData("position: " , position);
        telemetry.addData("to: ", loadPos2Ticks);
        double power;
        if(position > unloadPos2Ticks) {
            power = -0.3;
        } else {
            power = 0.3;
        }
        telemetry.addData("power: " , power);
        elevator.setPower(power);


        position = unloadPos2Ticks;
    }

    //y button
    public void goToUnloadPos3() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setTargetPosition(encoderVal + unloadPos3Ticks);
        if(position > unloadPos3Ticks) {
            elevator.setPower(negativePower);
        } else {
            elevator.setPower(positivePower);

        }

        position = unloadPos3Ticks;
    }

    public void goToStackPos() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevator.setTargetPosition(encoderVal + unloadStackTicks);
        if(position > unloadStackTicks) {
            elevator.setPower(positivePower);
        } else {
            elevator.setPower(negativePower);

        }

        position = unloadStackTicks;
    }


    public enum MotorPositions {
        LOAD_POSITION1,
        LOAD_POSITION2,
        UNLOAD_POSITION1,
        STACK_POSITION_UNLOAD,
        UNLOAD_POSITION2,
        UNLOAD_POSITION3,

    }

}
