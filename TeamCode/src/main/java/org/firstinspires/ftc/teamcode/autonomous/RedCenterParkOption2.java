package org.firstinspires.ftc.teamcode.autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="RedPark2", group="Bot")
@Disabled
public class RedCenterParkOption2 extends AutonomousOpMode {

    private final double DRIVE_POWER = 0.2;

    @Override
    public void runOpMode() {
        initializeAllDevices();
        waitForStart();
        //runFlail();
        driveToPositionRevs(-2.2, DRIVE_POWER);
        //shoot();
        //load();
        sleep(4000);
        //shoot();
        sleep(1000);
        driveToPositionRevs(-1.5, DRIVE_POWER);
    }
}