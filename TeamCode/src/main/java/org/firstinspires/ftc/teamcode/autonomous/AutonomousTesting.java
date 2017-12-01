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
        driveSystem = new MecanumDriveSystem(this);
        waitForStart();
        telemetry.update();
        driveSystem.driveInchesXY(100, 50, 0.8);
        while(driveSystem.anyMotorsBusy()) {
            this.idle();
        }
        while(!this.isStopRequested()) {
            this.idle();
        }
    }
}
