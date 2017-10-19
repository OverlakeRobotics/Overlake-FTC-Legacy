package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;


public class PixySystem {
    private HardwareMap hwMap;

    private PixyCam pixyCam;
    private PixyCam.Block block1 = pixyCam.GetBiggestBlock(1);
    private PixyCam.Block block2 = pixyCam.GetBiggestBlock(2);

    boolean isBlue;
    boolean found;
    boolean isRed;

    I2cDeviceSynchImpl pixyCamReader;
    I2cAddr pixyCamAddress = I2cAddr.create8bit(0x1);

    public PixySystem(HardwareMap hwMap) {
        this.hwMap = hwMap;
        pixyCam = hwMap.get(PixyCam.class, "pixycam");

    }

    public void pixyRun {
        if(isBlue) {
    
        }
    }
    public boolean isBlue() {

        return true;
    }

}
