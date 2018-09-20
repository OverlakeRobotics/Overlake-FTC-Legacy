package org.firstinspires.ftc.teamcode.robot.systems.physical;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.systems.base.System;

/**
 * Created by jacks on 10/20/2017.
 */

public class ElevatorSystem extends System
{
    private DcMotor elevator;
    private DigitalChannel touchSensorBottom;
    private DigitalChannel touchSensorTop;
    private ElapsedTime debounceTime = new ElapsedTime();

    private int ticksTopToBottom;
    private int topTicks;
    private int bottomTicks;
    private int position;

    private boolean debouncing = false;
    public boolean isAtTop = false;
    public boolean isAtBottom = false;


    private int loadPosTicks;
    private int unloadBlock2Ticks;
    private int unloadBlock3Ticks;
    public int autoInit;

    public int bottomLifterDown;

    //works the same for up and down
    private int incrementTicks = 30;

    private double negativePower = -0.95;
    private double positivePower = 0.95;
    Integer i = 0;
    private int[] positions = new int[3];
    int positionIndex = 0;
    public static final int BOTTOM_INDEX = 0;
    public static final int MIDDLE_INDEX = 1;
    Telemetry.Line liftTelemetryLine;
    Telemetry.Item indexTelemetryItem;
    Telemetry.Item positionTelemetryItem;
    Telemetry.Item ticksTopTelemetryItem;
    Telemetry.Item ticksBotTelemetryItem;

    private OpMode opMode;

    public ElevatorSystem(OpMode mode)
    {
        super(mode, "Elevator");
        this.opMode = mode;
        this.elevator = map.dcMotor.get("elevator");
        this.telemetry.setAutoClear(false);

        this.liftTelemetryLine = this.telemetry.addLine("elevator");
        this.indexTelemetryItem = liftTelemetryLine.addData("index", 0);
        this.positionTelemetryItem = liftTelemetryLine.addData("position", 0);
        this.ticksTopTelemetryItem = liftTelemetryLine.addData("top", 0);
        this.ticksBotTelemetryItem = liftTelemetryLine.addData("bot", 0);

        this.touchSensorBottom = map.get(DigitalChannel.class, "touchBottom");
        this.touchSensorTop = map.get(DigitalChannel.class, "touchTop");
        elevator.setDirection(DcMotor.Direction.REVERSE);
        for (i = 0; i < positions.length; i++)
        {
            positions[i] = config.getInt("Elevator" + i.toString());
        }
        loadPosTicks = config.getInt("load_position");
        unloadBlock2Ticks = config.getInt("block2_position");
        unloadBlock3Ticks = config.getInt("block3_position");
        bottomLifterDown = config.getInt("bottomLifterDown_position"); // A D D  T O  S T U F F
        this.ticksTopToBottom = config.getInt("ticksTopToBottom");
        this.autoInit = config.getInt("autoInit");

    }

    public void elevatorLoop()
    {
        if (Math.abs(position - elevator.getCurrentPosition()) < 20)
        {
            elevator.setPower(0.0);
        }
        //checkForBottom(telemetry);
        checkForTop();
    }

    //for autonomous
    public void goToZero(Telemetry telemetry)
    {

        //On Rev, when configuring use the second input in digital
        touchSensorBottom.setMode(DigitalChannel.Mode.INPUT);

        while (touchSensorBottom.getState() == true)
        {
            telemetry.addData("touch Sensor", touchSensorBottom.getState());
            elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elevator.setPower(negativePower);
        }
        elevator.setPower(0.0);
        bottomTicks = elevator.getCurrentPosition();
        position = 0;
    }

    public void loop()
    {
    }

    private void setTargetPosition(int ticks)
    {
        setTargetPosition(ticks, positivePower);
    }

    private void setTargetPosition(int ticks, double power)
    {
        this.positionTelemetryItem.setValue(ticks);
        this.position = ticks;
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setTargetPosition(bottomTicks + position);
        elevator.setPower(power);
    }

    private void setTargetIndex(int index)
    {
        this.indexTelemetryItem.setValue(index);
        this.positionIndex = index;
        setTargetPosition(this.positions[index]);
    }

    public void positionUp()
    {
        if (positionIndex < positions.length - 1)
        {
            positionIndex++;
            setTargetIndex(positionIndex);
        }
    }

    public void positionDown()
    {
        if (positionIndex >= 1)
        {
            positionIndex--;
            setTargetIndex(positionIndex);
        }
    }

    public void goToPostionSynch(int ticks)
    {
        LinearOpMode lOpMode = (LinearOpMode) this.opMode;
        setTargetPosition(ticks);
        while (elevator.isBusy())
        {
            lOpMode.sleep(10);
        }
        elevator.setPower(0);
    }

    public void goToIndexSynch(int index)
    {
        goToPostionSynch(positions[index]);
    }

    public void goToTopSynch()
    {
        goToTopSynch(positivePower);
    }

    public void goToTopSynch(double power)
    {
        runMotorUp(power);
        while (!isAtTop)
        {
            checkForTop();
        }
    }

    public void goToBottomSynch()
    {
        runMotorDown();
        while (!isAtBottom)
        {
            checkForBottom(telemetry);
        }
    }

    public void runMotorDown(double power)
    {
        if (!isAtBottom)
        {
            elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            //elevator.run(-power);
        }
    }

    public void runMotorDown()
    {
        runMotorDown(positivePower);
    }

    public void initFromAutoInit()
    {
        bottomTicks = elevator.getCurrentPosition() - autoInit;
        topTicks = bottomTicks + ticksTopToBottom;
    }

    public void runMotorDownSynch()
    {
        while (!isAtBottom)
        {
            elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elevator.setPower(negativePower);
        }
    }

    public void runMotorUp()
    {
        runMotorUp(positivePower);
    }

    public void runMotorUp(double power)
    {
        if (!isAtTop)
        {
            elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elevator.setPower(power);
        }
    }

    public void runMotorUpSynch()
    {
        while (!isAtTop)
        {
            elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elevator.setPower(positivePower);
        }
    }

    //B button
    /*public void goToUnloadBlock2() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setTargetPosition(bottomTicks + unloadBlock2Ticks);
        if(position > unloadBlock2Ticks) {
            elevator.run(negativePower);
        } else {
            elevator.run(positivePower);
        }
        position = unloadBlock2Ticks;

    }

    //X button
    public void goToUnloadBlock3() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setTargetPosition(bottomTicks + unloadBlock3Ticks);

        telemetry.addData("position: " , position);
        telemetry.addData("to: ", unloadBlock3Ticks);

        if(position > unloadBlock3Ticks) {
            elevator.run(negativePower);
        } else {
            elevator.run(positivePower);
        }

        position = unloadBlock3Ticks;
    }

    public void goToBottomLifterDown() {
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevator.setTargetPosition(bottomTicks + bottomLifterDown);
        telemetry.addData("position: " , position);
        telemetry.addData("to: ", bottomLifterDown);
        double power;
        if(position > bottomLifterDown) {
            elevator.run(negativePower);
        } else {
            elevator.run(positivePower);
        }

        position = bottomLifterDown;
    }*/

    public void incrementUp()
    {
        setTargetPosition(position + incrementTicks, 0.4);
    }

    public void incrementDown()
    {
        setTargetPosition(position - incrementTicks, 0.4);
    }

    public void setPosition()
    {
        String stringVal = Double.toString(position);
        config.updateKey("Elevator Position: " + i.toString(), stringVal);
        positions[positionIndex] = position;
    }

    public void setPositionLoad()
    {
        int ticks = position;
        String tickString = Integer.toString(ticks);
        config.updateKey("load_position", tickString);
        loadPosTicks = position;

    }

    public void setPositionBlock2()
    {
        int ticks = position;
        String tickString = Integer.toString(ticks);
        config.updateKey("block2_position", tickString);
        unloadBlock2Ticks = position;
    }

    public void setPositionBlock3()
    {
        int ticks = position;
        String tickString = Integer.toString(ticks);
        config.updateKey("block3_position", tickString);
        unloadBlock3Ticks = position;

    }

    public void checkForBottom(Telemetry telemetry)
    {
        //On Rev, when configuring use the second input in digital
        boolean bottomSwitchPushed = !touchSensorBottom.getState();
        if (bottomSwitchPushed && !isAtBottom)
        {
            elevator.setPower(0.0);
            bottomTicks = elevator.getCurrentPosition();
            ticksBotTelemetryItem.setValue(bottomTicks);
            position = loadPosTicks;
            positionIndex = 0;
            isAtBottom = true;

        }
        else if (isAtBottom)
        {
            if (!bottomSwitchPushed && !debouncing)
            {
                debounceTime.reset();
                debouncing = true;

            }
            if (debouncing && debounceTime.milliseconds() > 50)
            {
                isAtBottom = false; //(touchSensorBottom.getState() == false);
                debouncing = false;
            }
        }
    }

    public void checkForTop()
    {       //On Rev, when configuring use the second input in digital
        boolean topSwitchPushed = !touchSensorTop.getState();
        if (topSwitchPushed && !isAtTop)
        {
            elevator.setPower(0.0);
            isAtTop = true;
            positionIndex = positions.length;
            topTicks = elevator.getCurrentPosition();
            bottomTicks = topTicks - ticksTopToBottom;
            ticksTopTelemetryItem.setValue(topTicks);
            ticksBotTelemetryItem.setValue(bottomTicks);
        }
        else if (isAtTop)
        {
            if (!topSwitchPushed && !debouncing)
            {
                debounceTime.reset();
                debouncing = true;
            }
            if (debouncing && debounceTime.milliseconds() > 50)
            {
                isAtTop = false; //(touchSensorBottom.getState() == false);
                debouncing = false;
            }
        }
    }

    public enum ElevatorPosition
    {
        LOAD_POSITION1,
        LOAD_POSITION2,
        UNLOAD_POSITION1,
        STACK_POSITION_UNLOAD,
        UNLOAD_POSITION2,
        UNLOAD_POSITION3,
    }
}
