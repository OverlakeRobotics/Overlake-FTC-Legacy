package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by jacks on 10/20/2017.
 */

public class ElevatorSystem extends System {
    private DcMotor elevator;
    private DigitalChannel touchSensorBottom;
    private DigitalChannel touchSensorTop;
    private ElapsedTime debounceTime = new ElapsedTime();

    int encoderVal;
    int position;

    private boolean debouncing = false;
    private boolean isAtTop = false;
    private boolean isAtBottom = false;

    private int loadPosTicks;
    private int unloadBlock2Ticks;
    private int unloadBlock3Ticks;

    //works the same for up and down
    private int incrementTicks = 30;

    private double negativePower = -0.55;
    private double positivePower = 0.55;


    public ElevatorSystem(OpMode mode) {
        super(mode, "Elevator");

        this.elevator = map.dcMotor.get("elevator");
        this.touchSensorBottom = map.get(DigitalChannel.class, "touchBottom");
        this.touchSensorTop = map.get(DigitalChannel.class, "touchTop");

        loadPosTicks = config.getInt("load_position");
        unloadBlock2Ticks = config.getInt("block2_position");
        unloadBlock3Ticks = config.getInt("block3_position");

        elevator.setDirection(DcMotor.Direction.REVERSE);
        touchSensorBottom.setMode(DigitalChannel.Mode.INPUT);
        touchSensorTop.setMode(DigitalChannel.Mode.INPUT);
    }

    //for autonomous
    public void goToZero(Telemetry telemetry){
        //On Rev, when configuring use the second input in digital
        while(touchSensorBottom.getState()) {
            telemetry.addData("touch Sensor" , touchSensorBottom.getState());
            elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elevator.setPower(negativePower);
        }
        elevator.setPower(0.0);
        encoderVal = elevator.getCurrentPosition();
        position = 0;
    }

    public void goToPosition(int ticks) {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int thisPos = encoderVal + position;
        elevator.setTargetPosition(thisPos + ticks);
        if(position > ticks){
            elevator.setPower(negativePower);
        } else{
            elevator.setPower(positivePower);
        }
    }

    public void runMotorDown() {
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevator.setPower(negativePower);

    }

    public void runMotorUp() {
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevator.setPower(positivePower);

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

    public void setPositionLoad() {
        int ticks = position;
        String tickString = Integer.toString(ticks);
        config.updateKey("load_position", tickString);
        loadPosTicks = position;

    }

    public void setPositionBlock2() {
        int ticks = position;
        String tickString = Integer.toString(ticks);
        config.updateKey("block2_position", tickString);
        unloadBlock2Ticks = position;
    }

    public void setPositionBlock3() {
        int ticks = position;
        String tickString = Integer.toString(ticks);
        config.updateKey("block3_position", tickString);
        unloadBlock3Ticks = position;

    }

    public void checkForBottom(Telemetry telemetry){
        //On Rev, when configuring use the second input in digital
        boolean bottomSwitchPushed = !touchSensorBottom.getState();
        if( bottomSwitchPushed && !isAtBottom) {
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
        boolean topSwitchPushed = !touchSensorTop.getState();
        if (topSwitchPushed && !isAtTop) {
            elevator.setPower(0.0);
            isAtTop = true;
        } else if (isAtTop) {
            if (!debouncing) {
                debounceTime.reset();
                debouncing = true;
            }
            else {
                if (debounceTime.milliseconds() > 50) {
                    isAtTop = false; //(touchSensorBottom.getState() == false);
                    debouncing = false;
                }
            }
        }
    }
}
