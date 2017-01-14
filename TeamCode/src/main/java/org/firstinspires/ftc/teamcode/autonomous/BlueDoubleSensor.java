package org.firstinspires.ftc.teamcode.autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="BlueAutonomous", group="Bot")
public class BlueDoubleSensor extends VuforiaBaseOpMode {

    private final double DRIVE_POWER = 0.2;

    @Override
    public void runOpMode() {
        initializeAllDevices();
        waitForStart();
        runFlail();
        driveToPositionRevs(-1.5, DRIVE_POWER);
        shoot();
        sleep(500);
        load();
        sleep(1500);
        shoot();
        sleep(500);
        turn(-35, 0.3);
        sleep(500);
        driveToPositionRevs(-2.0, DRIVE_POWER);
        sleep(500);
        turn(35, 0.3);

    }
}