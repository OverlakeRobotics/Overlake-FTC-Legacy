package org.firstinspires.ftc.teamcode.teleop;

/**
 * Created by EvanCoulson on 9/26/17.
 */

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.systems.IMUSystem;
import org.firstinspires.ftc.teamcode.logger.LoggingService;

@TeleOp(name = "TeleOpTest", group = "TeleOp")
public class TeleopTest extends BaseOpMode
{
    private IMUSystem imu;

    public TeleopTest()
    {
        super("TeleOpTest");
    }

    public void init()
    {
        this.initBaseSystems();
        this.imu = new IMUSystem(this);
        logger.setLoggingServices(LoggingService.FILE, LoggingService.TELEMETRY);
    }

    public void loop()
    {
        if (this.imu.getAcceleration() != null && this.imu.getVelocity() != null)
        {
            logger.log(this.imu.getAcceleration().toString());
            logger.log(this.imu.getVelocity().toString());
        }

        float rx = controller1.gamepad.right_stick_x;
        float ry = controller1.gamepad.right_stick_y;
        float lx = controller1.gamepad.left_stick_x;
        float ly = controller1.gamepad.left_stick_y;

        this.driveSystem.driveGodMode(rx, ry, lx, ly, 0.5f);
        telemetry.update();
    }

    @Override
    public void initButtons()
    {

    }
}
