package org.firstinspires.ftc.teamcode.robot.components;

/**
 * Created by EvanCoulson on 1/9/18.
 */

public class Gear {
    private double teeth;
    public Gear next;

    public Gear(double teeth) {
        this(teeth, null);
    }

    public Gear(double teeth, Gear next) {
        this.next = next;
        this.teeth = teeth;
    }

    public double getRatio() {
        if (next == null) {
            return 1;
        }
        return next.teeth / this.teeth;
    }
}
