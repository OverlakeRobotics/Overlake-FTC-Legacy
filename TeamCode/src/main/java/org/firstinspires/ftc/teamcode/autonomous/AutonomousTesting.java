package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.MecanumDriveSystem;

/**
 * Created by EvanCoulson on 10/23/17.
 */

@Autonomous(name="AutonomousTest", group="Bot")
public class AutonomousTesting extends LinearOpMode {
    private MecanumDriveSystem driveSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        driveSystem = new MecanumDriveSystem(hardwareMap, telemetry);
        telemetry.addData("here", "here");
        telemetry.update();
        driveSystem.driveInchesXY(50, 0, 0.5);
        sleep(100000);
//        telemetry.addData("sleep", "");
//        sleep(5000);
//        telemetry.addData("resume", "");
//        driveSystem.driveInchesXY(5,5,0.8);
    }
}
