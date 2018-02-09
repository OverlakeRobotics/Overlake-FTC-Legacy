package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.tools.MiniPID;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;
import org.firstinspires.ftc.teamcode.util.ramp.ExponentialRamp;
import org.firstinspires.ftc.teamcode.util.ramp.LogarithmicRamp;
import org.firstinspires.ftc.teamcode.util.ramp.Ramp;

import java.io.IOException;
import java.util.Date;

// Combines a DcMotor and a Potentiometer into make a servo
public class DcMotorServo
{
    private DcMotor motor;
    private AnalogInput armPotentiometer;
    public double targetPosition;
    public double power;
    LogarithmicRamp ramp;
    Telemetry telemetry;
    ElapsedTime elapsedTime = new ElapsedTime();
    MiniPID miniPID;
    ConfigParser config;
    Date time;
    DateFormat df;
    FileOutputStream outputStream;
    DataOutputStream dataStream;
    PrintStream printStream;
    String pow;
    long i;
    double P;
    double I;
    double D;

    double finalPow;

    public void init(HardwareMap hardwareMap, String motorName, String potentiometerName, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.motor = hardwareMap.dcMotor.get(motorName);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.armPotentiometer = hardwareMap.analogInput.get(potentiometerName);
        this.targetPosition = this.getCurrentPosition();
        this.time = new Date();
        this.df = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
        try {
            this.printStream = new PrintStream("/sdcard/FIRST/Claw Power " + df.format(time));
        }
        catch (FileNotFoundException e) {
            telemetry.addData("File Not Found: " , e.getLocalizedMessage());
        }

        this.config = new ConfigParser("PID.omc");
        P = config.getDouble("P");
        I = config.getDouble("I");
        D  = config.getDouble("D");
        this.miniPID = new MiniPID(P, I, D);

        this.targetPosition = 0.2;
        this.power = 1.0;
    }

    public double getCurrentPosition()
    {
        return Range.scale(this.armPotentiometer.getVoltage(), 0.0, 3.3, 0.0, 1.0 );
    }

    private long lastTime = 0;


    public void loop(double targetPosition) {
        i = elapsedTime.nanoseconds();
        if(i > lastTime + 10000 ) {
            lastTime = i;

            finalPow = pidPower(targetPosition);
        RobotLog.ee("Claw PID ", targetPosition + ", " + getCurrentPosition() + ", " + finalPow + ", " + miniPID.errorSum + ", " +
                    (getCurrentPosition()-miniPID.lastActual) + ", " + i);
        telemetry.addData("claw power", finalPow);
        this.motor.setPower(finalPow);
        }
            //telemetry.addData("Time: ", i);

    }
    public double getPower() {
        return finalPow;
    }

        /*if(printStream != null) {
            printStream.println(targetPosition + ", " + getCurrentPosition() + ", " + finalPow + ", " + miniPID.errorSum + ", " +
                    (getCurrentPosition()-miniPID.lastActual));
        }*/


    public void runMotor(){
        this.motor.setPower(0.5);
    }
    public void runMotorBack(){
        this.motor.setPower(-0.2);
    }
    public void stop(){
        this.motor.setPower(0);
    }

    public double pidPower(double targetPosition) {
        miniPID.setSetpoint(targetPosition);
        miniPID.setOutputLimits(1.0);
        return miniPID.getOutput(getCurrentPosition(), targetPosition);
    }






    double adjustedPower(double currentPos, double targetPos, double maxPower) {
        //x's are potentiometer
        //x1 is target vale
        //x2 1.0
        //y1 1.0
        //y2 0.01
        double minPower = 0.2;
        double minVoltage = 0.1;
        double maxVoltage = 1.0;
        double delta = targetPos - currentPos;
        //
        Ramp ramp = new ExponentialRamp(minVoltage, minPower, maxVoltage, maxPower);

        double power = getPower(ramp, delta, maxPower);

        if (Math.abs(power) <= minPower) {
            power = 0;
        }

        telemetry.addData("Power: ", power);
        return  power;
    }
    private double getPower(Ramp ramp, double delta, double maxPower) {
        double absDelta = Math.abs(delta);

        return ((delta < 0) ? -1 : 1) * ramp.value(absDelta);
    }


    }