package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.robot.ElevatorSystem;

/**
 * Created by Steven Abbott on 1/29/2018.
 */

public class PitCrewOpMode extends AutonomousOpMode {

    ElevatorSystem elevator;
    public static final int TIME = 500;

    PitCrewOpMode() {
        this.elevator = new ElevatorSystem(hardwareMap, telemetry);
    }

    @Override
    public void runOpMode() {
        initializeAllDevices();

        elevator.runMotorUp();                  // calibrate motor on top limit switch
        sleep(TIME);
        parrallelLiftSystem.runMotorDown(-0.1);                     // put lifter arm down slowly (hence the -0.1) to calibrate it
        sleep(TIME);
        parrallelLiftSystem.goToMiddle();                           // Move the lifter to the middle so we don't hit the bottom plate moving into park position
        sleep(TIME);
        elevator.runMotorDown();                                    // double check/ calibrate the elevator on the bottom limit switch
        sleep(TIME);
        elevator.goToBottomLifterDown();                            // Move the elevator to its bottom with the lifter arms down
        sleep(TIME);
        neoClaw.goToBottom();                                       //
        sleep(TIME);
        neoClaw.goToTop();                                          // Make sure the claw is working and the three positions are
        sleep(TIME);
        neoClaw.goToMiddle();                                       // properly set... also it looks cool
        sleep(TIME);
        neoClaw.goToTop();                                          //
        sleep(TIME);
        parrallelLiftSystem.goToPark();                             // move the lifter down beyond its normal operating limits to the park position to fit in 18inches

        telemetry.addLine("Physical system calibration complete.");
        telemetry.update();
    }
}
