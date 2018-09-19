package fakes;

import com.qualcomm.robotcore.hardware.AnalogInput;

public class FakeAnalogInput extends AnalogInput
{
    private double voltage;

    public FakeAnalogInput()
    {
        super(new FakeAnalogInputController(), -1);
        voltage = 0;
    }

    public void setVoltage(double voltage)
    {
        this.voltage = voltage;
    }

    @Override
    public double getVoltage()
    {
        return this.voltage;
    }
}
