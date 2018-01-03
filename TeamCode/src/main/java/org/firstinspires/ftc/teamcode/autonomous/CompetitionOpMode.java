package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;
import org.firstinspires.ftc.teamcode.robot.ConfigParser;
import org.firstinspires.ftc.teamcode.robot.PixySystem;

/**
 * Created by lexis on 23-Oct-17.
 */

@Autonomous(name="CompetitionOpMode", group="Bot")
public class CompetitionOpMode extends AutonomousOpMode {
    private int zone;
    private int inchesToReferenceBoxNonAudience;
    private int inchesToReferenceBoxAudience;
    private double powerSetting1;
    private double powerSetting2;
    private int cryptoApproachInches;
    private boolean isBlue;
    private boolean isAudienceSide;
    private ConfigParser config;
    private int b;
    PixySystem pixySystem;

    public CompetitionOpMode() {}

    private void init2() {
        this.config = new ConfigParser("Autonomous.omc");
        this.inchesToReferenceBoxNonAudience = config.getInt("InchesToReferenceBoxNonAudience"); // the length of the first drive forward, ending at the refernce box
        this.inchesToReferenceBoxAudience = config.getInt("InchesToReferenceBoxAudience"); // the length of the first drive forward, ending at the refernce box
        this.zone = config.getInt("zone"); // sets which starting zone you are in
        this.powerSetting1 = config.getDouble("powerSetting1"); // the power setting for driving off the stone
        this.powerSetting2 = config.getDouble("powerSetting2"); // the power setting for everything other than getting off the stone
        this.cryptoApproachInches = config.getInt("cryptoApproachInches"); // the length of the drive forward to place the cube
        this.b = config.getInt("b");
        this.isBlue = config.getBoolean("isBlue");
        this.isAudienceSide = config.getBoolean("isAudienceSide");
        syncConfigZoneBooleansAndInts(this.zone, this.isBlue, this.isAudienceSide);
        this.pixySystem = new PixySystem(this, this.isBlue);
    }



    public void runOpMode() {
        init2();
        initializeAllDevices();
        elevator.goToZero(telemetry);
        double startHeading = imuSystem.getHeading();

        ////
        waitForStart();
        ////

        pixySystem.initPixyStuff();
        pixySystem.doServoStuff();

        grabBlock();
        vuforiaCryptoBox(zone);


        ////
        stop();
        ////
    }

    public void vuforiaCryptoBox(int zone) {
        double inchesPerBox = b;
        int boxNumber = eye.look(); // 0 = left   1 = center   2 = right
        telemetry.addLine("DETERMINED THE PICTURE!!!! YAY. Its picture number " + boxNumber);
        telemetry.update();
        sleep(50);

        // zone 0 is blue non-audience      zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0) { // blue non-audience
            driveToPositionInches(-42, powerSetting1);
            returnToHeading(-90, powerSetting2);
            driveToPositionInches(-(inchesPerBox * boxNumber + inchesToReferenceBoxNonAudience), powerSetting2); // 25
            turn(60, powerSetting2);
            driveToPositionInches(-cryptoApproachInches, powerSetting2);
            placeBlock();
            driveToPositionInches(5, powerSetting2);
        } else if (zone == 1) { // blue audience
            driveToPositionInches(-(inchesPerBox * boxNumber + inchesToReferenceBoxAudience), powerSetting1);
            returnToHeading(60, powerSetting2);
            driveToPositionInches(-cryptoApproachInches, powerSetting2);
            placeBlock();
            driveToPositionInches(5, powerSetting2);
        } else if (zone == 2) { // red non-audience
            driveToPositionInches(-40, powerSetting1);
            returnToHeading(90, powerSetting2);
            driveToPositionInches(-((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxNonAudience), powerSetting2); // 25
            turn(-60, powerSetting2);
            driveToPositionInches(-cryptoApproachInches, powerSetting2);
            placeBlock();
            driveToPositionInches(5, powerSetting2);
        } else if (zone == 3) { // red audience
            driveToPositionInches(-((inchesPerBox * 2) - (boxNumber * inchesPerBox) + inchesToReferenceBoxAudience), powerSetting1);
            returnToHeading(-60, 1);
            driveToPositionInches(-cryptoApproachInches, powerSetting2);
            placeBlock();
            driveToPositionInches(5, powerSetting2);
        } else if (zone == 4) { //this is a test zone to test inches per box
            driveToPositionInches(-inchesPerBox, powerSetting2);
            sleep(2000);
            driveToPositionInches(-inchesPerBox, powerSetting2);
        }
    }

    public void returnToHeading(double heading, double power) {
        double correctionNeeded = heading - imuSystem.getHeading();
        telemetry.addLine("returning to " + heading + " by adjusting by " + correctionNeeded + "currently at " + imuSystem.getHeading());
        telemetry.update();
        turn(correctionNeeded, power);
    }

    public void placeBlock() {
        elevator.goToZero(telemetry);
        claw.goToReleasePosition();
        sleep(1000);
    }

    public void grabBlock() {
        claw.goToLoadPosition();
        sleep(500);
        elevator.goToUnloadBlock3();
        sleep(1000);
    }

    public void correctBoxLeftApproach(int boxNumber) {
        int inchesPerBox = 15;
        telemetry.addLine("driving to box: " + boxNumber + " or inches: " + (-inchesPerBox * boxNumber));
        telemetry.update();
        driveToPositionInches((-inchesPerBox * boxNumber), 0.75);
    }

    public void correctBoxRightApproach(int boxNumber) {
        int inchesPerBox = 11;
        driveToPositionInches(((inchesPerBox * 2) - (boxNumber * inchesPerBox)), 1);
    }
    // zone 0 is blue non-audience      zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
    public void syncConfigZoneBooleansAndInts(int zone, boolean isBlue, boolean isAudienceSide) {
        if (zone > 4) {
            if (isBlue && isAudienceSide) {
                this.zone = 1;
            } else if (isBlue && !isAudienceSide) {
                this.zone = 0;
            } else if (!isBlue && isAudienceSide) {
                this.zone = 3;
            } else if (!isBlue && !isAudienceSide){
                this.zone = 1;
            }
        } else {
            if (zone == 0) {
                this.isBlue = true;
                this.isAudienceSide = false;
            } else if (zone == 1) {
                this.isBlue = true;
                this.isAudienceSide = true;
            } else if (zone == 2) {
                this.isBlue = false;
                this.isAudienceSide = false;
            } else if (zone == 3){
                this.isBlue = false;
                this.isAudienceSide = true;
            }
        }
    }

    public void cryptoBox(int zone) {
        claw.setLoadPosition();
        sleep(2000);
        elevator.goToUnloadBlock2();
        // pic 0 is left     pic 1 is right      pic 2 is center
        // zone 0 is blue non-audience     zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0) {
            driveToPositionInches(-40, 1);
            turn(-90, 1);
            driveToPositionInches(-18, 1);
            turn(90, 1);
            elevator.goToZero(telemetry);
            claw.setReleasePosition();
            sleep(1000);
            driveToPositionInches(-20, 1);


        } else if (zone == 1) {
            driveToPositionInches(-50, 1);
            turn(90, 1);
            elevator.goToZero(telemetry);
            claw.setReleasePosition();
            sleep(2000);
            driveToPositionInches(-13, 1);
        } else if (zone == 2) {
            driveToPositionInches(40, 1);
            turn(-90, 1);
            driveToPositionInches(-18, 1);
            turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.setReleasePosition();
            sleep(1000);
            driveToPositionInches(-18, 1);
        } else {
            driveToPositionInches(-50, 1);
            turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.setReleasePosition();
            sleep(2000);
            driveToPositionInches(-18, 1);
        }
        claw.setReleasePosition();
    }

    /*public void initPixy() {
        pixyCam = hardwareMap.get(PixyCam.class, "pixycam");
        //pixyCam2 = hardwareMap.get(PixyCam.class, "pixycam2");

        //this.rightVertServo = hardwareMap.servo.get("rightvertservo");
        this.leftVertServo = hardwareMap.servo.get("leftvertservo");
        //this.rightHorizServo = hardwareMap.servo.get("righthorizservo");
        this.leftHorizServo = hardwareMap.servo.get("lefthorizservo");
        //this.rightHorizServoCenter = 0.4;
        //this.rightVertServoTop = 1.0;
        //this.rightVertServoBottom = 0.0;
        this.leftRedBlock = pixyCam.GetBiggestBlock(1);
        this.leftBlueBlock = pixyCam.GetBiggestBlock(2);
        telemetry.addData("Red: ", leftRedBlock.toString());
        telemetry.addData("Blue: ", leftBlueBlock.toString());
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
            sleep(1000);
        }
    }*/

}
