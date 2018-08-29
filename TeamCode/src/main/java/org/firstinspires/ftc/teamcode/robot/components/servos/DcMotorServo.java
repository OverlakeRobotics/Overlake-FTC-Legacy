package org.firstinspires.ftc.teamcode.robot.components.servos;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.timers.IntervalTimer;
import org.firstinspires.ftc.teamcode.tools.MiniPID;
import org.firstinspires.ftc.teamcode.config.IConfig;
import org.firstinspires.ftc.teamcode.ramp.ExponentialRamp;
import org.firstinspires.ftc.teamcode.ramp.Ramp;
import org.firstinspires.ftc.teamcode.tools.MiniPIDFactory;

public class DcMotorServo
{
    private static final int TIME_INTERVAL = 10000;
    private static final double MINIMUM_POWER = 0.2;
    private static final double ZERO_POWER = 0;
    private static final double MINIMUM_POSITION = 0.1;
    private static final double MOTOR_POWER = 0.5;

    //TODO: Convert to a scale object that takes in a scale factor (slope) and scale offset (y int)
    private static final double VOLTAGE_SCALE_X_1 = 0.0;
    private static final double VOLTAGE_SCALE_X_2 = 3.3;
    private static final double VOLTAGE_SCALE_Y_1 = 0.0;
    private static final double VOLTAGE_SCALE_Y_2 = 1.0;

    private DcMotor motor;
    private AnalogInput armPotentiometer;
    private MiniPID miniPID;
    private IntervalTimer intervalTimer;
    private double targetPosition;
    private Ramp powerAdjustmentRamp;

    public DcMotorServo(DcMotor motor, AnalogInput armPotentiometer, IConfig config)
    {
        this.motor = motor;
        this.armPotentiometer = armPotentiometer;
        this.intervalTimer = new IntervalTimer(TIME_INTERVAL);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.miniPID = MiniPIDFactory.getMiniPIDFromConfig(config);
        powerAdjustmentRamp = new ExponentialRamp(0, 0, 0, 0);
    }

    public void setTargetPosition(double targetPosition) {
        this.targetPosition = targetPosition;
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

    public void loop()
    {
        if (intervalTimer.hasCurrentIntervalPassed())
        {
            intervalTimer.update();
            miniPID.setSetpoint(targetPosition);
            motor.setPower(miniPID.getOutput(getCurrentPosition(), targetPosition));
        }
    }

    public double getCurrentPosition()
    {
        return Range.scale(
            armPotentiometer.getVoltage(),
            VOLTAGE_SCALE_X_1, VOLTAGE_SCALE_X_2,
            VOLTAGE_SCALE_Y_1, VOLTAGE_SCALE_Y_2
        );
    }

    public double getAdjustedPowerFromCurrentPosition(double maxPower)
    {
        Ramp ramp = getPowerAdjustmentRamp(maxPower);
        double distanceToTarget = targetPosition - getCurrentPosition();
        double power = Math.signum(distanceToTarget) * ramp.value(Math.abs(distanceToTarget));
        return Math.abs(power) <= MINIMUM_POSITION ? ZERO_POWER : power;
    }

    private Ramp getPowerAdjustmentRamp(double maxPower) {
        if (shouldUpdateRamp())
            return new ExponentialRamp(MINIMUM_POSITION, MINIMUM_POWER, targetPosition, maxPower);
        else
            return powerAdjustmentRamp;
    }

    private boolean shouldUpdateRamp() {
        return powerAdjustmentRamp == null || powerAdjustmentRamp.x2 != targetPosition;
    }
}