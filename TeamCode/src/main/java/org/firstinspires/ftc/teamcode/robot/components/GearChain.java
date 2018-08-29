package org.firstinspires.ftc.teamcode.robot.components;

import org.firstinspires.ftc.teamcode.hardware.dcmotors.MotorType;

/**
 * Created by EvanCoulson on 9/26/17.
 */

public class GearChain
{
    private double chainRatio;
    private double[] teeth;
    private MotorType motorType;

    public GearChain(MotorType motorType, double... teeth)
    {
        this.teeth = teeth;
        this.motorType = motorType;
        chainRatio = calculateChainRatio();
    }

    private double calculateChainRatio()
    {
        double chainRatio = 1d;
        for (int i = 1; i < teeth.length; i++)
        {
            chainRatio *= teeth[i] / teeth[i - 1];
        }
        return chainRatio;
    }

    public int calculateInputRevolutionTicks(double revolutions)
    {
        return (int) Math.round(motorType.getPulses() * revolutions);
    }

    public int calculateOutputRevolutionTicks(double revolutions)
    {
        return (int) Math.round(motorType.getPulses() * revolutions * chainRatio);
    }

    public int calculateOuputTicks(int ticks)
    {
        return (int) Math.round(ticks * chainRatio);
    }

    public double getChainRatio()
    {
        return chainRatio;
    }
}