package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.autonomous.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;

@Autonomous(name="PixyServos", group="Bot")
public class PixySystem {
    private HardwareMap hwMap;

    private PixyCam pixyCam;
    private PixyCam.Block redBlock;
    private PixyCam.Block blueBlock;

    Servo rightVertServo;
    Servo leftVertServo;
    Servo rightHorizServo;
    Servo leftHorizServo;
    boolean teamColorIsBlue;

    I2cDeviceSynchImpl pixyCamReader;
    I2cAddr pixyCamAddress = I2cAddr.create8bit(0x1);

    public PixySystem(HardwareMap hwMap) {
        this.hwMap = hwMap;
        this.pixyCam = hwMap.get(PixyCam.class, "pixycam");
        this.rightVertServo = hwMap.servo.get("rightvertservo");
        this.leftVertServo = hwMap.servo.get("leftvertservo");
        this.rightHorizServo = hwMap.servo.get("righthorizservo");
        this.leftHorizServo = hwMap.servo.get("lefthorizservo");

        this.teamColorIsBlue = true; // TO DO: Use config app values
    }

    public void rotateStuff() {
        redBlock = pixyCam.GetBiggestBlock(1);
        blueBlock = pixyCam.GetBiggestBlock(2);

        if (teamColorIsBlue) {
            leftVertServo.setPosition(0.4);
            if (redBlock.x < blueBlock.x) {  // if red is further left than blue
                leftHorizServo.setPosition(0.3); // then move the servo left
            } else {
                leftHorizServo.setPosition(0.7); // move the servo right -- also, if no values are found for x, it will go right
            }
        } else {
            rightVertServo.setPosition(0.4);
            if (redBlock.x > blueBlock.x) {
                rightHorizServo.setPosition(0.3);
            } else {
                rightHorizServo.setPosition(0.7);
            }

        }
    }
}
