package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.*;
import org.firstinspires.ftc.teamcode.robot.systems.System;
import org.firstinspires.ftc.teamcode.util.logger.LoggingService;

/**
 * This is NOT an opmode.
 *
 *
 */
public class IMUSystem extends System
{
    public BNO055IMU imu;
    public BNO055IMU.Parameters parameters;


    /* Constructor */
    public IMUSystem(OpMode opMode){
        super(opMode, "IMUSystem");
        logger.setLoggingServices(LoggingService.FILE);

        this.parameters = new BNO055IMU.Parameters();
        this.parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        this.parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        this.parameters.loggingEnabled = true;
        this.parameters.loggingTag = "BNO055";
        this.parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        this.imu = this.map.get(BNO055IMU.class, "imu");
        this.imu.initialize(parameters);

        // Enable reporting of position using the naive integrator
        imu.startAccelerationIntegration(new Position(), new Velocity(), 50); //TODO: Is the last parameter good?
    }

    public double getHeading()
    {
        Orientation orientation = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
        return orientation.firstAngle;
    }

    public Acceleration getAcceleration() {
        return imu.getLinearAcceleration();
    }

    public Velocity getVelocity() {
        return imu.getVelocity();
    }
}

