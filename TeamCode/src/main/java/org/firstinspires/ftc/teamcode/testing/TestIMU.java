package org.overlake.ftc.team_7330.Testing;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.*;

/**
 * Created by Rohan on 10/16/2015.
 */
@TeleOp(name = "TestIMU")
@Disabled
public class TestIMU extends LinearOpMode
{
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    // Our sensors, motors, and other devices go here, along with other long term state
    BNO055IMU imu;
    ElapsedTime elapsed = new ElapsedTime();
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    // Here we have state we use for updating the dashboard. The first of these is important
    // to read only once per update, as its acquisition is expensive. The remainder, though,
    // could probably be read once per item, at only a small loss in display accuracy.
    Orientation orientation;
    Position position;
    Velocity velocity;
    Acceleration acceleration;

    int loopCycles;
    int i2cCycles;
    double ms;

    @Override
    public void runOpMode() throws InterruptedException
    {
        // We are expecting the IMU to be attached to an I2C port on  a core device interface
        // module and named "imu". Retrieve that raw I2cDevice and then wrap it in an object that
        // semantically understands this particular kind of sensor.
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "BNO055";
        //TODO: parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Enable reporting of position using the naive integrator
        imu.startAccelerationIntegration(new Position(), new Velocity(), 50);

        // Set up our dashboard computations
        composeDashboard();

        // Wait until we're told to go
        waitForStart();

        // Loop and update the dashboard
        while (opModeIsActive())
        {
            telemetry.update();
            idle();
        }
    }

    //----------------------------------------------------------------------------------------------
    // dashboard configuration
    //----------------------------------------------------------------------------------------------

    void composeDashboard()
    {
        // The default dashboard update rate is a little to slow for us, so we update faster
        telemetry.setMsTransmissionInterval(200);

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable()
        {
            @Override
            public void run()
            {
                // Acquiring the angles is relatively expensive; we don't want
                // to do that in each of the three items that need that info, as that's
                // three times the necessary expense.
                orientation = imu.getAngularOrientation();
                position = imu.getPosition();
                velocity = imu.getVelocity();
                acceleration = imu.getAcceleration();

                imu.getAcceleration();

                // The rest of this is pretty cheap to acquire, but we may as well do it
                // all while we're gathering the above.

                //TODO: Figure out the new way of doing this.
                // loopCycles = getLoopCount();
                // i2cCycles  = ((II2cDeviceClientUser) imu).getI2cDeviceClient().getI2cCycleCount();
                ms = elapsed.time() * 1000.0;
            }
        });
        telemetry.addLine()
                .addData("loop count: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return loopCycles;
                    }
                })
                .addData("i2c cycle count: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return i2cCycles;
                    }
                });

        telemetry.addLine()
                .addData("loop rate: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatRate(ms / loopCycles);
                    }
                })
                .addData("i2c cycle rate: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatRate(ms / i2cCycles);
                    }
                });

        telemetry.addLine()
                .addData("status: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return decodeCalibration(imu.read8(BNO055IMU.Register.CALIB_STAT));
                    }
                });

        telemetry.addLine()
                .addData("1st: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return orientation.firstAngle;
                    }
                })
                .addData("2nd: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return orientation.secondAngle;
                    }
                })
                .addData("3rd: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return orientation.thirdAngle;
                    }
                });

        telemetry.addLine()
                .addData("x: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatPosition(position.x);
                    }
                })
                .addData("y: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatPosition(position.y);
                    }
                })
                .addData("z: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatPosition(position.z);
                    }
                });

        telemetry.addLine()
                .addData("vx: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatPosition(velocity.xVeloc);
                    }
                })
                .addData("vy: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatPosition(velocity.yVeloc);
                    }
                })
                .addData("vz: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatPosition(velocity.zVeloc);
                    }
                });

        telemetry.addLine()
                .addData("ax: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatPosition(acceleration.xAccel);
                    }
                })
                .addData("ay: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatPosition(acceleration.yAccel);
                    }
                })
                .addData("az: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return formatPosition(acceleration.zAccel);
                    }
                });

        telemetry.addLine()
                .addData("is accelerometer calibrated? ", new Func<Object>()
                {
                    public Object value()
                    {
                        return imu.isAccelerometerCalibrated();
                    }
                });
    }

    String formatAngle(double angle)
    {
        return parameters.angleUnit == BNO055IMU.AngleUnit.DEGREES ? formatDegrees(angle) : formatRadians(angle);
    }

    String formatRadians(double radians)
    {
        return formatDegrees(degreesFromRadians(radians));
    }

    String formatDegrees(double degrees)
    {
        return String.format("%.1f", normalizeDegrees(degrees));
    }

    String formatRate(double cyclesPerSecond)
    {
        return String.format("%.2f", cyclesPerSecond);
    }

    String formatPosition(double coordinate)
    {
        String unit = parameters.accelUnit == BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
                ? "m" : "??";
        return String.format("%.2f%s", coordinate, unit);
    }

    //----------------------------------------------------------------------------------------------
    // Utility
    //----------------------------------------------------------------------------------------------

    /**
     * Normalize the angle into the range [-180,180)
     */
    double normalizeDegrees(double degrees)
    {
        while (degrees >= 180.0) degrees -= 360.0;
        while (degrees < -180.0) degrees += 360.0;
        return degrees;
    }

    double degreesFromRadians(double radians)
    {
        return radians * 180.0 / Math.PI;
    }

    /**
     * Turn a calibration code into something that is reasonable to show in telemetry
     */
    String decodeCalibration(int status)
    {
        StringBuilder result = new StringBuilder();

        result.append(String.format("s%d", (status >> 6) & 0x03));  // SYS calibration status
        result.append(" ");
        result.append(String.format("g%d", (status >> 4) & 0x03));  // GYR calibration status
        result.append(" ");
        result.append(String.format("a%d", (status >> 2) & 0x03));  // ACC calibration status
        result.append(" ");
        result.append(String.format("m%d", (status >> 0) & 0x03));  // MAG calibration status

        return result.toString();
    }
}
