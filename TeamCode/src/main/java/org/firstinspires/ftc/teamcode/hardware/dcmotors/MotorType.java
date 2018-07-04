package org.firstinspires.ftc.teamcode.hardware.dcmotors;


public enum MotorType {
    TEST(1),
    NEVEREST20_PULSES(960),
    NEVEREST40_PULSES(1120),
    NEVEREST60_PULSES(1680);

    private int pulses;

    MotorType(int pulses) {
        this.pulses = pulses;
    }

    public int getPulses() {
        return this.pulses;
    }
}
