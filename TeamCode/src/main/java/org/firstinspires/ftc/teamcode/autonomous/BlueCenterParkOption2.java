package org.firstinspires.ftc.teamcode.autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/*
 * Created by EvanCoulson on 10/26/16.
 *
 * _______   __       __  __   ______       ______   ______   ___    __   _________  ______   ______        ______   ________   ______    ___   ___
 /_______/\ /_/\     /_/\/_/\ /_____/\     /_____/\ /_____/\ /__/\ /__/\ /________/\/_____/\ /_____/\      /_____/\ /_______/\ /_____/\  /___/\/__/\
 \::: _  \ \\:\ \    \:\ \:\ \\::::_\/_    \:::__\/ \::::_\/_\::\_\\  \ \\__.::.__\/\::::_\/_\:::_ \ \     \:::_ \ \\::: _  \ \\:::_ \ \ \::.\ \\ \ \
  \::(_)  \/_\:\ \    \:\ \:\ \\:\/___/\    \:\ \  __\:\/___/\\:. `-\  \ \  \::\ \   \:\/___/\\:(_) ) )_    \:(_) \ \\::(_)  \ \\:(_) ) )_\:: \/_) \ \
   \::  _  \ \\:\ \____\:\ \:\ \\::___\/_    \:\ \/_/\\::___\/_\:. _    \ \  \::\ \   \::___\/_\: __ `\ \    \: ___\/ \:: __  \ \\: __ `\ \\:. __  ( (
    \::(_)  \ \\:\/___/\\:\_\:\ \\:\____/\    \:\_\ \ \\:\____/\\. \`-\  \ \  \::\ \   \:\____/\\ \ `\ \ \    \ \ \    \:.\ \  \ \\ \ `\ \ \\: \ )  \ \
     \_______\/ \_____\/ \_____\/ \_____\/     \_____\/ \_____\/ \__\/ \__\/   \__\/    \_____\/ \_\/ \_\/     \_\/     \__\/\__\/ \_\/ \_\/ \__\/\__\/

 */
@Autonomous(name="BluePark2", group="Bot")
public class BlueCenterParkOption2 extends AutonomousOpMode {

    private final double DRIVE_POWER = 0.2;

    @Override
    public void runOpMode() {
        initializeAllDevices();
        waitForStart();
        //runFlail();
        driveToPositionRevs(-2.2, DRIVE_POWER);
        //shoot();
        sleep(500);
        //load();
        sleep(4000);
        //shoot();
        sleep(500);
        driveToPositionRevs(-1.5, DRIVE_POWER);
    }
}