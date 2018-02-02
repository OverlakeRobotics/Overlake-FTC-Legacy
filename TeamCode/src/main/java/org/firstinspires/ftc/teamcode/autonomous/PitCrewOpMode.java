package org.firstinspires.ftc.teamcode.autonomous;

/**
 * Created by Steven Abbott on 1/29/2018.
 */

public class PitCrewOpMode extends AutonomousOpMode {

    @Override
    public void runOpMode() {
        initializeAllDevices();

        elevator.runMotorUp();
        parrallelLiftSystem.runMotorDown(-0.1);
        parrallelLiftSystem.goToMiddle();
        elevator.runMotorDown();
        elevator.goToBottomLifterDown();
        neoClaw.goToBottom();
        neoClaw.goToTop();
        neoClaw.goToMiddle();
        neoClaw.goToTop();
        parrallelLiftSystem.goToPark();
        telemetry.addLine("Physical system calibration complete.");
        telemetry.update();
    }
}
