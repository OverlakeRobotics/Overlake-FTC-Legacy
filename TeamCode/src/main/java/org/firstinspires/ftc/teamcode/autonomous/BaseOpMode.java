package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.systems.physical.ClawSystemNoMergeConflictPlease;
import org.firstinspires.ftc.teamcode.robot.systems.physical.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robot.systems.physical.Eye;
import org.firstinspires.ftc.teamcode.robot.systems.physical.IMUSystem;
import org.firstinspires.ftc.teamcode.robot.systems.physical.MecanumDriveSystem;
import org.firstinspires.ftc.teamcode.robot.systems.physical.ParallelLiftSystem;
import org.firstinspires.ftc.teamcode.robot.systems.physical.PixySystem;
import org.firstinspires.ftc.teamcode.config.ConfigParser;

/**
 * Created by EvanCoulson on 10/11/17.
 */

public abstract class BaseOpMode extends LinearOpMode
{
    public ConfigParser config;
    public MecanumDriveSystem driveSystem;
    public IMUSystem imuSystem;
    public Eye eye;
    public ElevatorSystem elevator;
    public PixySystem pixySystem;
    public ClawSystemNoMergeConflictPlease claw;
    public ParallelLiftSystem parallelLiftSystem;

    public BaseOpMode(String opModeName)
    {
        config = new ConfigParser(opModeName + ".omc");
        telemetry.setMsTransmissionInterval(200);
    }

    protected void initSystems()
    {
        this.driveSystem = new MecanumDriveSystem(this);
        this.imuSystem = new IMUSystem(this);
        this.eye = new Eye(this);
        this.elevator = new ElevatorSystem(this);
        this.claw = new ClawSystemNoMergeConflictPlease(this);
        this.parallelLiftSystem = new ParallelLiftSystem(this);
        this.pixySystem = new PixySystem(this, 1);
    }
}
