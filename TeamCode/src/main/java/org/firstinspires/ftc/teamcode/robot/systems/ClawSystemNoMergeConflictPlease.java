package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.components.servos.DcMotorServo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.ConfigParser;

/**
 * Created by jacks on 1/8/2018.
 */
public class ClawSystemNoMergeConflictPlease extends System
{

    private DcMotorServo motorServo;

    double bottom;
    double middle;
    double top;
    double position;

    Telemetry telemetry;

    Telemetry.Line liftTelemetryLine;
    Telemetry.Item indexTelemetryItem;
    Telemetry.Item positionTelemetryItem;

    public ClawSystemNoMergeConflictPlease(OpMode opMode)
    {
        super(opMode, "meMotor");
        this.telemetry = opMode.telemetry;
        DcMotor motor = opMode.hardwareMap.dcMotor.get("meMotor");
        AnalogInput armPotentiometer = opMode.hardwareMap.analogInput.get("potentiometer");
        this.motorServo = new DcMotorServo(motor, armPotentiometer, new ConfigParser("PID.omc"));
        this.liftTelemetryLine = this.telemetry.addLine("Claw");
        this.indexTelemetryItem = liftTelemetryLine.addData("position", 0);
        this.positionTelemetryItem = liftTelemetryLine.addData("power", 0);
        bottom = config.getDouble("bottom");
        middle = config.getDouble("middle");
        top = config.getDouble("top");
        position = 0.45;
    }

    public void loop()
    {
        this.positionTelemetryItem.setValue(motorServo.getPower());
        this.indexTelemetryItem.setValue(motorServo.getCurrentPosition());

        motorServo.setTargetPosition(position);
        motorServo.loop();
    }

    public void managePosition()
    {

    }

    public void runMotorBackUpALotProbablyDeleteLater()
    {
        runMotor();
    }

    public void goToBottom()
    {
        position = bottom;
    }

    public void closeSynch(LinearOpMode o)
    {
        runMotorBack();
        o.sleep(1000);
    }

    public void openSynch(LinearOpMode o)
    {
        motorServo.runMotor();
        o.sleep(1000);
    }

    public void goToMiddle()
    {
        position = middle;
    }

    public void goToTop()
    {
        position = top;
    }

    public void runMotor()
    {
        motorServo.runMotor();
    }

    public void runMotorBack()
    {
        motorServo.runMotorBack();
    }

    public void stop()
    {
        motorServo.stop();
    }
}

