package org.firstinspires.ftc.teamcode.robot.components.motors;

import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;

public interface IGearedMotor {
    void run(double power);
    boolean isBusy();
    void setDirection(Direction direction);
    Direction getDirection();
    void setRunMode(RunMode runMode);
    RunMode getRunMode();
    void setTargetPosition(int target);
    int getTargetPosition();
    int getCurrentPosition();
    double getPower();
    void setOutputGearTargetTicks(int ticks);
    void setOutputGearTargetRevolutions(double revolutions);
    void setInputGearTargetTicks(int ticks);
    void setInputGearTargetRevolutions(double revolutions);
    int getDistanceToTargetPosition();
}
