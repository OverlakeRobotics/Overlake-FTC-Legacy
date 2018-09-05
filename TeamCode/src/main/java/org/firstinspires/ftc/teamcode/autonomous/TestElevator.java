package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.systems.ElevatorSystem;

/**
 * Created by jacks on 10/20/2017.
 */
@Autonomous(name = "TestElevator", group = "Bot")
@Disabled
public class TestElevator extends LinearOpMode
{
    ElevatorSystem elevatorSystem;

    @Override
    public void runOpMode() throws InterruptedException
    {
        waitForStart();

        elevatorSystem = new ElevatorSystem(this);
        elevatorSystem.goToZero(telemetry);
    }
}
