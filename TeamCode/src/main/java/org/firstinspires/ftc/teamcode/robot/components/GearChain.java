package org.firstinspires.ftc.teamcode.robot.components;

/**
 * Created by EvanCoulson on 9/26/17.
 */

public class GearChain {
    //TODO: Extract To Constants File
    public final static int NEVEREST20_PULSES = 960;
    public final static int NEVEREST40_PULSES = 1120;
    public final static int NEVEREST60_PULSES = 1680;

    private double chainRatio;
    private double[] teeth;

    public GearChain(double... teeth) {
        this.teeth = teeth;
        chainRatio = calculateChainRatio();
    }

    private double calculateChainRatio() {
        double chainRatio = 1d;
        for (int i = 1; i < teeth.length; i++) {
            chainRatio *= teeth[i] / teeth[i - 1];
        }
        return chainRatio;
    }

    public int calculateInputRevolutions(int pulses, double revolutions) {
        return (int) Math.round(pulses * revolutions);
    }

    public int calculateOutputRevolutions(int pulses, double revolutions) {
        return (int) Math.round(pulses * revolutions * chainRatio);
    }

    public int calculateOuputTicks(int ticks) {
        return (int) Math.round(ticks * chainRatio);
    }
}