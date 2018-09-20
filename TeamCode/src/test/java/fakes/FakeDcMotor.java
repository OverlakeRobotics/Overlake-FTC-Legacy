package fakes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

/**
 * Created by EvanCoulson on 8/26/18.
 */

public class FakeDcMotor implements DcMotor
{
    private double power;
    private int currentPosition;
    private int targetPosition;
    private MotorConfigurationType motorType;
    private Direction direction;
    private RunMode runMode;

    public FakeDcMotor()
    {
        direction = Direction.FORWARD;
        runMode = RunMode.RUN_TO_POSITION;
    }

    @Override
    public MotorConfigurationType getMotorType()
    {
        return motorType;
    }

    @Override
    public void setMotorType(MotorConfigurationType motorType)
    {
        this.motorType = motorType;
    }

    @Override
    public DcMotorController getController()
    {
        return null;
    }

    @Override
    public int getPortNumber()
    {
        return 0;
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior)
    {

    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior()
    {
        return null;
    }

    @Override
    public void setPowerFloat()
    {

    }

    @Override
    public boolean getPowerFloat()
    {
        return false;
    }

    @Override
    public void setTargetPosition(int position)
    {
        currentPosition = position;
        this.targetPosition = position;
    }

    @Override
    public int getTargetPosition()
    {
        return targetPosition;
    }

    @Override
    public boolean isBusy()
    {
        return false;
    }

    @Override
    public int getCurrentPosition()
    {
        return currentPosition;
    }

    @Override
    public void setMode(RunMode mode)
    {
        this.runMode = mode;
    }

    @Override
    public RunMode getMode()
    {
        return runMode;
    }

    @Override
    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    @Override
    public Direction getDirection()
    {
        return direction;
    }

    @Override
    public void setPower(double power)
    {
        this.power = power;
    }

    @Override
    public double getPower()
    {
        return power;
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
}
