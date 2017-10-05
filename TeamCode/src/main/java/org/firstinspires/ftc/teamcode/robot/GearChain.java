package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by EvanCoulson on 9/26/17.
 */

public class GearChain {
    private Gear input;
    private double chainRatio;

    public GearChain(Telemetry telemetry, double... allTeeth) {
        input = new Gear(allTeeth[0]);
        Gear current = input;
        for (int i = 1; i < allTeeth.length; i++) {
            Gear next = new Gear(allTeeth[i]);
            current.next = next;
            current = next;
        }
        chainRatio = calculateChainRatio(telemetry);
    }

    public int calculateInputRevolutions(int pulses, double revolutions) {
        return (int) Math.round(pulses * revolutions);
    }

    public int calculateOutputRevolutions(int pulses, double revolutions) {
        return (int) Math.round(pulses * revolutions * chainRatio);
    }

    private double calculateChainRatio(Telemetry telemetry) {
        double ratio = 1d;
        Gear current = input;
        while (current.next != null) {
            telemetry.addData("teeth1", current.teeth);
            telemetry.addData("teeth2", current.next.teeth);
            telemetry.addData("ratio", current.getRatio());

            ratio *= current.getRatio();
            current = current.next;
        }
        telemetry.addData("ratio", ratio);
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