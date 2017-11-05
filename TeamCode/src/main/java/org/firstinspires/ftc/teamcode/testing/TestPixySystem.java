package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;

/**
 * Created by lexis on 23-Oct-17.
 */

@Autonomous(name="TextPixySystem", group="Bot")
public class TestPixySystem extends LinearOpMode {

    private PixyCam pixyCam;
    private PixyCam pixyCam2;

    private double rightHorizServoCenter;
    private double rightVertServoTop;
    private double rightVertServoBottom;
    private double leftHorizServoCenter;
    private double leftVertServoTop;
    private double leftVertServoBottom;

    private PixyCam.Block leftRedBlock;
    private PixyCam.Block leftBlueBlock;
    private PixyCam.Block rightRedBlock;
    private PixyCam.Block rightBlueBlock;

    Servo rightVertServo;
    Servo leftVertServo;
    Servo rightHorizServo;
    Servo leftHorizServo;
    boolean teamColorIsBlue;

    @Override
    public void runOpMode() {
        pixyCam = hardwareMap.get(PixyCam.class, "pixycam");
        //pixyCam2 = hardwareMap.get(PixyCam.class, "pixycam2");

        this.teamColorIsBlue = false;

        //this.rightVertServo = hardwareMap.servo.get("rightvertservo");
        this.leftVertServo = hardwareMap.servo.get("leftvertservo");
        //this.rightHorizServo = hardwareMap.servo.get("righthorizservo");
        this.leftHorizServo = hardwareMap.servo.get("lefthorizservo");
        //this.rightHorizServoCenter = 0.4;
        //this.rightVertServoTop = 1.0;
        //this.rightVertServoBottom = 0.0;
        this.leftRedBlock = pixyCam.GetBiggestBlock(1);
        this.leftBlueBlock = pixyCam.GetBiggestBlock(2);
        telemetry.addData("Red :", leftRedBlock.toString());
        telemetry.addData("Blue :", leftBlueBlock.toString());
        telemetry.update();
        sleep(1000);
        leftVertServo.setPosition(1);
        sleep(1000);
        waitForStart();

        if (teamColorIsBlue == true) {
            if (this.leftRedBlock.x < this.leftBlueBlock.x) {  // if red is further left than blue
                leftHorizServo.setPosition(0.8); // then move the servo left
                sleep(1000);
                leftHorizServo.setPosition(0.5);
            } else {
                leftHorizServo.setPosition(0.2);
                sleep(1000);
                leftHorizServo.setPosition(0.5); // move the servo right -- also, if no values are found for x, it will go right
            }
            sleep(1000);
            leftVertServo.setPosition(0);
            sleep(1000);
        } else {
            if (leftRedBlock.x < leftBlueBlock.x) {  // if red is further left than blue
                leftHorizServo.setPosition(0.2); // then move the servo left
                sleep(1000);
                leftHorizServo.setPosition(0.5);
            } else {
                leftHorizServo.setPosition(0.8);
                sleep(1000);
                leftHorizServo.setPosition(0.5); // move the servo right -- also, if no values are found for x, it will go right
            }
            sleep(1000);
            leftVertServo.setPosition(0);
            sleep(1000);









            /*
            this.rightRedBlock = pixyCam2.GetBiggestBlock(1);
            this.rightBlueBlock = pixyCam2.GetBiggestBlock(2);
            telemetry.addData("Red :", rightRedBlock.toString());
            telemetry.addData("Blue :", rightBlueBlock.toString());
            telemetry.update();
            sleep(1000);
            rightVertServo.setPosition(rightVertServoBottom);
            sleep(1000);
            if (rightRedBlock.x < rightBlueBlock.x) {
                rightHorizServo.setPosition(rightHorizServoCenter + 0.2);
                sleep(1000);
                rightHorizServo.setPosition(rightHorizServoCenter);
            } else {
                rightHorizServo.setPosition(rightHorizServoCenter - 0.2);
                sleep(1000);
                rightHorizServo.setPosition(rightHorizServoCenter);
            }
            sleep(1000);
            rightVertServo.setPosition(rightVertServoTop);
            sleep(1000); */
        }
        while (!isStopRequested())
        {

        }
    }

}
