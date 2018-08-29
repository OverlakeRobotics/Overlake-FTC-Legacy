package org.firstinspires.ftc.teamcode.robot.components.servos;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.timers.IntervalTimer;
import org.firstinspires.ftc.teamcode.tools.MiniPID;
import org.firstinspires.ftc.teamcode.config.IConfig;
import org.firstinspires.ftc.teamcode.ramp.ExponentialRamp;
import org.firstinspires.ftc.teamcode.ramp.Ramp;

public class DcMotorServo
{
    private static final int TIME_INTERVAL = 10000;
    private static final double MINIMUM_POWER = 0.2;
    private static final double ZERO_POWER = 0;
    private static final double MINIMUM_POSITION = 0.1;
    private static final double MOTOR_POWER = 0.5;
    private static final double PID_OUTPUT_LIMITS = 1.0;

    private static final double VOLTAGE_SCALE_X_1 = 0.0;
    private static final double VOLTAGE_SCALE_X_2 = 3.3;
    private static final double VOLTAGE_SCALE_Y_1 = 0.0;
    private static final double VOLTAGE_SCALE_Y_2 = 1.0;

    private DcMotor motor;
    private AnalogInput armPotentiometer;
    private MiniPID miniPID;
    private IntervalTimer intervalTimer;

    public DcMotorServo(DcMotor motor, AnalogInput armPotentiometer, IConfig config)
    {
        this.motor = motor;
        this.armPotentiometer = armPotentiometer;
        this.intervalTimer = new IntervalTimer(TIME_INTERVAL);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.miniPID = new MiniPID(config.getDouble("P"), config.getDouble("I"), config.getDouble("D"));
    }

    public void loop(double targetPosition)
    {
        if (intervalTimer.hasCurrentIntervalPassed())
        {
            intervalTimer.update();
            this.motor.setPower(getPIDPower(targetPosition));
        }
    }

    private double getPIDPower(double targetPosition)
    {
        miniPID.setSetpoint(targetPosition);
        miniPID.setOutputLimits(PID_OUTPUT_LIMITS);
        return miniPID.getOutput(getCurrentPosition(), targetPosition);
    }

    public double getCurrentPosition()
    {
        return Range.scale(
                this.armPotentiometer.getVoltage(), 0.0, 3.3, 0.0, 1.0);
    }

    public double getPower()
    {
        return motor.getPower();
    }

    public void runMotor()
    {
        this.motor.setPower(MOTOR_POWER);
    }

    public void runMotorBack()
    {
        this.motor.setPower(-MINIMUM_POWER);
    }

    public void stop()
    {
        this.motor.setPower(ZERO_POWER);
    }

    public double adjustedPower(double currentPos, double targetPos, double maxPower)
    {
        double changeInPosition = targetPos - currentPos;
        Ramp ramp = new ExponentialRamp(MINIMUM_POSITION, MINIMUM_POWER, targetPos, maxPower);
        return getPower(ramp, changeInPosition);
    }

    private double getPower(Ramp ramp, double delta)
    {
        double power = Math.signum(delta) * ramp.value(Math.abs(delta));
        return Math.abs(power) <= MINIMUM_POSITION ? ZERO_POWER : power;
    }
}