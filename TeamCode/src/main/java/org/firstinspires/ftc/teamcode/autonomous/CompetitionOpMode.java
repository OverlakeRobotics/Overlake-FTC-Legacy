package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.systems.PixySystem;

/**
 * Created by lexis on 23-Oct-17.
 */

@Autonomous(name="CompetitionOpMode", group="Bot")
public class CompetitionOpMode extends BaseOpMode {
    private int zone;
    private int inchesToReferenceBoxNonAudience;
    private int inchesToReferenceBoxAudience;
    private double powerSetting1;
    private double powerSetting2;
    private int cryptoApproachInches;
    private boolean isBlue;
    private boolean isAudienceSide;
    private int b;
    PixySystem pixySystem;

    public CompetitionOpMode() {
        super("Autonomous");
    }

    private void init2() {
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
        this.pixySystem = new PixySystem(this, zone);
    }

    @Override
    public void runOpMode() {
        init2();
        parallelLiftSystem.goToPostitionSync(parallelLiftSystem.positions[0]);
        elevator.goToBottomLifterDown();
        double startHeading = imuSystem.getHeading();

        ////
        waitForStart();
        ////

        pixySystem.runPixySystem();
        cryptoBox(zone);

        stop();
    }

    public void placeBlock() {
        elevator.goToBottomLifterDown();
        this.claw.goToTop();
        sleep(1000);
    }

    public void grabBlock() {
        this.claw.goToBottom();
        sleep(500);
        elevator.goToPosition(800);
        sleep(1000);
    }

    public void correctBoxLeftApproach(int boxNumber) {
        int inchesPerBox = 15;
        telemetry.addLine("driving to box: " + boxNumber + " or inches: " + (-inchesPerBox * boxNumber));
        telemetry.update();
//        driveToPositionInches((-inchesPerBox * boxNumber), 0.75);
    }

    public void correctBoxRightApproach(int boxNumber) {
        int inchesPerBox = 11;
        this.driveSystem.driveToPositionInches(((inchesPerBox * 2) - (boxNumber * inchesPerBox)), 1);
    }
    // zone 0 is blue non-audience      zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
    public void syncConfigZoneBooleansAndInts(int zone, boolean isBlue, boolean isAudienceSide) {
        if (zone > 4) {
            if (isBlue && isAudienceSide) {
                this.zone = 0;
            } else if (isBlue && !isAudienceSide) {
                this.zone = 1;
            } else if (!isBlue && isAudienceSide) {
                this.zone = 2;
            } else if (!isBlue && !isAudienceSide){
                this.zone = 3;
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
        claw.goToBottom();
        sleep(2000);
        elevator.goToUnloadBlock2();
        // pic 0 is left     pic 1 is right      pic 2 is center
        // zone 0 is blue non-audience     zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0) {
            driveSystem.driveToPositionInches(-40, 1);
            driveSystem.turn(-90, 1);
            driveSystem.driveToPositionInches(-18, 1);
            driveSystem.turn(90, 1);
            elevator.goToZero(telemetry);
            claw.goToTop();
            sleep(1000);
            driveSystem.driveToPositionInches(-20, 1);
        } else if (zone == 1) {
            driveSystem.driveToPositionInches(-50, 1);
            driveSystem.turn(90, 1);
            elevator.goToZero(telemetry);
            claw.goToTop();
            sleep(2000);
            driveSystem.driveToPositionInches(-13, 1);
        } else if (zone == 2) {
            driveSystem.driveToPositionInches(40, 1);
            driveSystem.turn(-90, 1);
            driveSystem.driveToPositionInches(-18, 1);
            driveSystem.turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.goToTop();
            sleep(1000);
            driveSystem.driveToPositionInches(-18, 1);
        } else {
            driveSystem.driveToPositionInches(-50, 1);
            driveSystem.turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.goToTop();
            sleep(2000);
            driveSystem.driveToPositionInches(-18, 1);
        }
        claw.goToTop();
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
