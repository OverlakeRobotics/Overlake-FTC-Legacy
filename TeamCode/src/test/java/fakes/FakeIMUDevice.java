package fakes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.I2cWaitControl;
import com.qualcomm.robotcore.hardware.TimestampedData;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.MagneticFlux;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.Temperature;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class FakeIMUDevice extends I2cDeviceSynchDevice implements BNO055IMU, I2cDeviceSynch
{
    public FakeIMUDevice()
    {
        super(new I2cDeviceSynchSimple()
        {
            @Override
            public byte read8(int ireg)
            {
                return 0;
            }

            @Override
            public byte[] read(int ireg, int creg)
            {
                return new byte[0];
            }

            @Override
            public TimestampedData readTimeStamped(int ireg, int creg)
            {
                return null;
            }

            @Override
            public void write8(int ireg, int bVal)
            {

            }

            @Override
            public void write(int ireg, byte[] data)
            {

            }

            @Override
            public void write8(int ireg, int bVal, I2cWaitControl waitControl)
            {

            }

            @Override
            public void write(int ireg, byte[] data, I2cWaitControl waitControl)
            {

            }

            @Override
            public void waitForWriteCompletions(I2cWaitControl waitControl)
            {

            }

            @Override
            public void enableWriteCoalescing(boolean enable)
            {

            }

            @Override
            public boolean isWriteCoalescingEnabled()
            {
                return false;
            }

            @Override
            public boolean isArmed()
            {
                return false;
            }

            @Override
            public void setI2cAddr(I2cAddr i2cAddr)
            {

            }

            @Override
            public I2cAddr getI2cAddr()
            {
                return null;
            }

            @Override
            public void setLogging(boolean enabled)
            {

            }

            @Override
            public boolean getLogging()
            {
                return false;
            }

            @Override
            public void setLoggingTag(String loggingTag)
            {

            }

            @Override
            public String getLoggingTag()
            {
                return null;
            }

            @Override
            public void setUserConfiguredName(@Nullable String name)
            {

            }

            @Nullable
            @Override
            public String getUserConfiguredName()
            {
                return null;
            }

            @Override
            public Manufacturer getManufacturer()
            {
                return null;
            }

            @Override
            public String getDeviceName()
            {
                return null;
            }

            @Override
            public String getConnectionInfo()
            {
                return null;
            }

            @Override
            public int getVersion()
            {
                return 0;
            }

            @Override
            public void resetDeviceConfigurationForOpMode()
            {

            }

            @Override
            public void close()
            {

            }

            @Override
            public void setHealthStatus(HealthStatus status)
            {

            }

            @Override
            public HealthStatus getHealthStatus()
            {
                return null;
            }

            @Override
            public void setI2cAddress(I2cAddr newAddress)
            {

            }

            @Override
            public I2cAddr getI2cAddress()
            {
                return null;
            }
        }, false);
    }

    @Override
    public boolean initialize(@NonNull Parameters parameters)
    {
        return false;
    }

    @NonNull
    @Override
    public Parameters getParameters()
    {
        return null;
    }

    @Override
    public Orientation getAngularOrientation()
    {
        return new Orientation();
    }

    @Override
    public Orientation getAngularOrientation(AxesReference reference, AxesOrder order, org.firstinspires.ftc.robotcore.external.navigation.AngleUnit angleUnit)
    {
        return null;
    }

    @Override
    public Acceleration getOverallAcceleration()
    {
        return null;
    }

    @Override
    public AngularVelocity getAngularVelocity()
    {
        return null;
    }

    @Override
    public Acceleration getLinearAcceleration()
    {
        return null;
    }

    @Override
    public Acceleration getGravity()
    {
        return null;
    }

    @Override
    public Temperature getTemperature()
    {
        return null;
    }

    @Override
    public MagneticFlux getMagneticFieldStrength()
    {
        return null;
    }

    @Override
    public Quaternion getQuaternionOrientation()
    {
        return null;
    }

    @Override
    public Position getPosition()
    {
        return null;
    }

    @Override
    public Velocity getVelocity()
    {
        return null;
    }

    @Override
    public Acceleration getAcceleration()
    {
        return null;
    }

    @Override
    public void startAccelerationIntegration(Position initialPosition, Velocity initialVelocity, int msPollInterval)
    {

    }

    @Override
    public void stopAccelerationIntegration()
    {

    }

    @Override
    public SystemStatus getSystemStatus()
    {
        return null;
    }

    @Override
    public SystemError getSystemError()
    {
        return null;
    }

    @Override
    public CalibrationStatus getCalibrationStatus()
    {
        return null;
    }

    @Override
    public boolean isSystemCalibrated()
    {
        return false;
    }

    @Override
    public boolean isGyroCalibrated()
    {
        return false;
    }

    @Override
    public boolean isAccelerometerCalibrated()
    {
        return false;
    }

    @Override
    public boolean isMagnetometerCalibrated()
    {
        return false;
    }

    @Override
    public CalibrationData readCalibrationData()
    {
        return null;
    }

    @Override
    public void writeCalibrationData(CalibrationData data)
    {

    }

    @Override
    public byte read8(Register register)
    {
        return 0;
    }

    @Override
    public byte[] read(Register register, int cb)
    {
        return new byte[0];
    }

    @Override
    public void write8(Register register, int bVal)
    {

    }

    @Override
    public void write(Register register, byte[] data)
    {

    }

    @Override
    public void setReadWindow(ReadWindow window)
    {

    }

    @Override
    public ReadWindow getReadWindow()
    {
        return null;
    }

    @Override
    public void ensureReadWindow(ReadWindow windowNeeded, ReadWindow windowToSet)
    {

    }

    @Override
    public TimestampedData readTimeStamped(int ireg, int creg, ReadWindow readWindowNeeded, ReadWindow readWindowSet)
    {
        return null;
    }

    @Override
    public void setHeartbeatInterval(int ms)
    {

    }

    @Override
    public int getHeartbeatInterval()
    {
        return 0;
    }

    @Override
    public void setHeartbeatAction(HeartbeatAction action)
    {

    }

    @Override
    public HeartbeatAction getHeartbeatAction()
    {
        return null;
    }

    @Override
    public void disengage()
    {

    }

    @Override
    protected boolean doInitialize()
    {
        return false;
    }

    @Override
    public void engage()
    {

    }

    @Override
    public boolean isEngaged()
    {
        return false;
    }

    @Override
    public void setHealthStatus(HealthStatus status)
    {

    }

    @Override
    public HealthStatus getHealthStatus()
    {
        return null;
    }

    @Override
    public void setI2cAddress(I2cAddr newAddress)
    {

    }

    @Override
    public I2cAddr getI2cAddress()
    {
        return null;
    }

    @Override
    public byte read8(int ireg)
    {
        return 0;
    }

    @Override
    public byte[] read(int ireg, int creg)
    {
        return new byte[0];
    }

    @Override
    public TimestampedData readTimeStamped(int ireg, int creg)
    {
        return null;
    }

    @Override
    public void write8(int ireg, int bVal)
    {

    }

    @Override
    public void write(int ireg, byte[] data)
    {

    }

    @Override
    public void write8(int ireg, int bVal, I2cWaitControl waitControl)
    {

    }

    @Override
    public void write(int ireg, byte[] data, I2cWaitControl waitControl)
    {

    }

    @Override
    public void waitForWriteCompletions(I2cWaitControl waitControl)
    {

    }

    @Override
    public void enableWriteCoalescing(boolean enable)
    {

    }

    @Override
    public boolean isWriteCoalescingEnabled()
    {
        return false;
    }

    @Override
    public boolean isArmed()
    {
        return false;
    }

    @Override
    public void setI2cAddr(I2cAddr i2cAddr)
    {

    }

    @Override
    public I2cAddr getI2cAddr()
    {
        return null;
    }

    @Override
    public void setLogging(boolean enabled)
    {

    }

    @Override
    public boolean getLogging()
    {
        return false;
    }

    @Override
    public void setLoggingTag(String loggingTag)
    {

    }

    @Override
    public String getLoggingTag()
    {
        return null;
    }

    @Override
    public void setUserConfiguredName(@Nullable String name)
    {

    }

    @Nullable
    @Override
    public String getUserConfiguredName()
    {
        return null;
    }

    @Override
    public Manufacturer getManufacturer()
    {
        return null;
    }

    @Override
    public String getDeviceName()
    {
        return null;
    }
}
