package org.firstinspires.ftc.teamcode.robot;

/**
 * Created by EvanCoulson on 9/26/17.
 */

public class GearChain {
    public final static int NEVEREST20_PULSES = 960;
    public final static int NEVEREST40_PULSES = 1120;
    public final static int NEVEREST60_PULSES = 1680;

    private Gear input;
    private double chainRatio;

    public GearChain(double... allTeeth) {
        input = new Gear(allTeeth[0]);
        Gear current = input;
        for (int i = 1; i < allTeeth.length; i++) {
            Gear next = new Gear(allTeeth[i]);
            current.next = next;
            current = next;
        }
        chainRatio = calculateChainRatio();
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

    private double calculateChainRatio() {
        double ratio = 1d;
        Gear current = input;
        while (current.next != null) {
            ratio *= current.getRatio();
            current = current.next;
        }
        return ratio;
    }

    private class Gear {
        private double teeth;
        private Gear next;

        public Gear(double teeth) {
            this(teeth, null);
        }

        public Gear(double teeth, Gear next) {
            this.next = next;
            this.teeth = teeth;
        }

        public double getRatio() {
            return next.teeth / this.teeth;
        }
    }
}