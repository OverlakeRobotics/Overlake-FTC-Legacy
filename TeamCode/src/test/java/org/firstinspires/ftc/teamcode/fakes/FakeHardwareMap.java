package org.firstinspires.ftc.teamcode.fakes;

import android.content.Context;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
}
