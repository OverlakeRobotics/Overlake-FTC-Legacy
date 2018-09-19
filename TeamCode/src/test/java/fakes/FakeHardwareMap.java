package fakes;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

public class FakeHardwareMap extends HardwareMap
{
    public FakeHardwareMap()
    {
        super(new FakeContext());
    }

    public void addFakeDcMotor(String name, DcMotor fakeDcMotor)
    {
        this.dcMotor.put(name, fakeDcMotor);
    }

    public void addFakeAnalogInput(String name, AnalogInput fakeAnalogInput)
    {
        this.analogInput.put(name, fakeAnalogInput);
    }

    public void addFakeIMU(String name, I2cDeviceSynch fakeIMU)
    {
        this.i2cDeviceSynch.put(name, fakeIMU);
    }
}
