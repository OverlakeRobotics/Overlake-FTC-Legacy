package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by EvanCoulson on 11/15/16.
 */

public class BallLiftSystem {

    private final double LIFT_POWER = 0.42;
    private final double BELT_POWER = 0.6;
    private final double INTAKE_POWER = 0.8;
    private final int ticksPerRotation = 1120;
    private static final int motorGearSize = 1;
    private static final int lifterGearSize = 5;

    private HardwareMap map;
    private DcMotor lifter;
    private DcMotor belt;
    private DcMotor flail;
    public boolean autonomous;

    private boolean debug;

    public BallLiftSystem(HardwareMap map) {
        this.map = map;
        this.lifter = map.dcMotor.get("ballLiftMotor");
        this.belt = map.dcMotor.get("ballBeltMotor");
        this.flail = map.dcMotor.get("ballIntakeMotor");

    }
    public void runFlail(boolean isFoward){
        if(isFoward){
            flail.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else{
            flail.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        flail.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flail.setPower(INTAKE_POWER);
    }
    public void runFlail(double revolutions){
        flail.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        flail.setTargetPosition(flail.getCurrentPosition()+revolutionsToTics(revolutions));
        flail.setPower(INTAKE_POWER);
        
    }

    public void stopFlail()
    {
        flail.setPower(0);
    }

    public void runLift(boolean isFoward) {
        if (isFoward)
            lifter.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            lifter.setDirection(DcMotorSimple.Direction.REVERSE);
        lifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lifter.setPower(LIFT_POWER);
    }

    public void runLift(double revolutions) {
        lifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifter.setTargetPosition(lifter.getCurrentPosition() + revolutionsToTics(revolutions)*(lifterGearSize/motorGearSize));
        lifter.setPower(LIFT_POWER);
    }

    public void stopLift() {
        lifter.setPower(0.0);
    }

    public void runBelt(boolean isFoward) {
        if (isFoward)
            belt.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            belt.setDirection(DcMotorSimple.Direction.REVERSE);
        belt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        belt.setPower(BELT_POWER);
    }

    public void runBelt(double revolutions) {
        belt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        belt.setTargetPosition(belt.getCurrentPosition() + revolutionsToTics(revolutions));
        belt.setPower(BELT_POWER);
    }

    public void stopBelt() {
        belt.setPower(0.0);
    }

    private int revolutionsToTics(double revolutions) {
        return (int) Math.round(revolutions * this.ticksPerRotation);
    }

    public void waitBeltandLiftBusy () {
        while (lifter.isBusy() || belt.isBusy()) {

        }
    }
}
