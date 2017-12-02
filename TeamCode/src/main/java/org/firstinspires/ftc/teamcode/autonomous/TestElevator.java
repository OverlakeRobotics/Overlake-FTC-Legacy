package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robot.ElevatorSystem;

/**
 * Created by jacks on 10/20/2017.
 */
@Autonomous(name = "TestElevator", group = "Bot")
@Disabled
public class TestElevator extends LinearOpMode{
    ElevatorSystem elevatorSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        elevatorSystem = new ElevatorSystem(this.hardwareMap, telemetry);
        elevatorSystem.goToZero(telemetry);
    }
}
