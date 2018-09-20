package org.firstinspires.ftc.teamcode.robot.systems.base;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.systems.base.LinearSystem;

/**
 * Created by Michael on 3/15/2018.
 */

public abstract class LinearEncoderSystem extends LinearSystem
{
    public LinearEncoderSystem(OpMode opMode, String systemName) {
        super(opMode, systemName);
    }
}
