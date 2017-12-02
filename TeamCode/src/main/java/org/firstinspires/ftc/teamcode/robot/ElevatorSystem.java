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
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by jacks on 10/20/2017.
 */

public class ElevatorSystem {
    private ConfigParser config;
    private DcMotor elevator;
    private DigitalChannel touchSensorBottom;
    private DigitalChannel touchSensorTop;
    private ElapsedTime debounceTime = new ElapsedTime();
    Telemetry telemetry;

    int encoderVal;
    int position;

    private boolean debouncing = false;
    private boolean isAtTop = false;
    private boolean isAtBottom = false;

    private int loadPosTicks = 0;
    private int unloadBlock2Ticks = 600;
    private int unloadBlock3Ticks = 1000;

    //works the same for up and down
    private int incrementTicks = 20;
    private int competitionTicks = 100;

    private double negativePower = -0.55;
    private double positivePower = 0.55;


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
            //elevator.setPower(-0.3);
            elevator.setPower(negativePower);

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
            position = loadPosTicks;
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

    //B button
    public void goToUnloadBlock2() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setTargetPosition(encoderVal + unloadBlock2Ticks);
        if(position > unloadBlock2Ticks) {
            elevator.setPower(negativePower);
        } else {
            elevator.setPower(positivePower);

        }

        position = unloadBlock2Ticks;
    }

    //X button
    public void goToUnloadBlock3() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevator.setTargetPosition(encoderVal + unloadBlock3Ticks);
        telemetry.addData("position: " , position);
        telemetry.addData("to: ", unloadBlock3Ticks);
        double power;
        if(position > unloadBlock3Ticks) {
            elevator.setPower(negativePower);
        } else {
            elevator.setPower(positivePower);
        }

        position = unloadBlock3Ticks;
    }

    public void incrementUp() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setTargetPosition(encoderVal + position + incrementTicks);
        elevator.setPower(0.4);
        position = position + incrementTicks;
    }

    public void incrementDown() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setTargetPosition(encoderVal + position - incrementTicks);
        elevator.setPower(-0.4);
        position = position - incrementTicks;
    }

    public void setPositionBlock2() {
        unloadBlock2Ticks = position;
        //use evan write directly into config

    }

    public void setPositionBlock3() {
        unloadBlock3Ticks = position;
        //use evan write directly into config

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
