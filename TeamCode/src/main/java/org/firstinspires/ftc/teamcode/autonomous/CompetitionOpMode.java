package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.pixycam.PixyCam;
import org.firstinspires.ftc.teamcode.robot.PixySystem;

/**
 * Created by lexis on 23-Oct-17.
 */

@Autonomous(name="CompetitionOpMode", group="Bot")
public class CompetitionOpMode extends BaseOpMode {
    int zone;

    boolean isRedSide;
    boolean isAudience;
    public static final String TAG = "Vuforia VuMark Sample";

    public CompetitionOpMode() {
        super("Autonomous");
        this.isRedSide = this.config.getBoolean("isRedSide");
        this.isAudience = this.config.getBoolean("isAudience");
        this.pixySystem = new PixySystem(this, isRedSide);
    }

    @Override
    public void runOpMode() {
        waitForStart();
        elevator.goToZero(telemetry);
        claw.goToLoadPosition();
        sleep(1500);
        elevator.goToUnloadBlock3();
        sleep(1000);
        //send in "this" and if the team color is blue (true) or red (false)
        pixySystem = new PixySystem(this, isRedSide);
        pixySystem.initPixyStuff();
        pixySystem.doServoStuff();
        sleep(1000);
        vuforiaCryptoBox(isRedSide, isAudience);

        stop();
    }

    public void vuforiaCryptoBox(boolean isRedSide, boolean isAudience) {
        //Todo @Michael: Tweak the amount of inches driven at different intervals and the inches per box in right and left cryptobox approach

        int picNumber = eye.look(); // 0 = left   1 = center   2 = right
        telemetry.addLine("DETERMINED THE PICTURE!!!! YAY. Its picture number " + picNumber);
        telemetry.update();
        sleep(50);

        // pic 0 is left     pic 1 is right      pic 2 is center

        // zone 0 is blue non-audience      zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (!isRedSide && !isAudience) { // blue non-audience CLOSE TO GOOD
//            driveToPositionInches(-40, 0.6);
//            turn(-90, 1);

//            driveToPositionInches(-25, 1);
            //telemetry.addLine("We're at the first cryptobox!");
            //telemetry.update();
            //sleep(1000);
            //correctBoxLeftApproach(picNumber);

            if (picNumber == 0) {
//                turn(117 , 1);
            } else if (picNumber == 1) {
//                turn(90, 1);
            } else {
//                turn(62, 1);
            }
//            driveToPositionInches(-14, 1);
            elevator.goToZero(telemetry);
            sleep(1000);
            claw.goToReleasePosition();
            sleep(1000);
//            driveToPositionInches(5, 1);

        } else if (!isRedSide && isAudience) { // blue audience

//            driveToPositionInches(-58, 0.6);
            //telemetry.addLine("We're at the first cryptobox!");
            //telemetry.update();
            //sleep(2000);
            //correctBoxLeftApproach(picNumber);

            if (picNumber == 0) {
//                turn(117 , 1);
            } else if (picNumber == 1) {
//                turn(90, 1);
            } else {
//                turn(62, 1);
            }
            //turn(90, 1);
            //sleep(2000);
//            driveToPositionInches(-12, 1);

            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(1000);
//            driveToPositionInches(5, 1);
        } else if (isRedSide && !isAudience) { // red non-audience CLOSE TO GOOD
//            driveToPositionInches(-40, 0.6);
//            turn(90, 1);

//            driveToPositionInches(-25, 1);
            //telemetry.addLine("We're at the first cryptobox!");
            //telemetry.update();
            //sleep(1000);
            //correctBoxLeftApproach(picNumber);

            if (picNumber == 0) {
//                turn(-65 , 1);
            } else if (picNumber == 1) {
//                turn(-90, 1);
            } else {
//                turn(-117, 1);
            }
//            driveToPositionInches(-14, 1);
            elevator.goToZero(telemetry);
            sleep(1000);
            claw.goToReleasePosition();
            sleep(1000);
//            driveToPositionInches(5, 1);
        } else if(isRedSide && isAudience){ // red audience

//            driveToPositionInches(-57, 0.6);
            //telemetry.addLine("We're at the first cryptobox!");
            //telemetry.update();
            //sleep(2000);
            //correctBoxLeftApproach(picNumber);

            if (picNumber == 0) {
//                turn(-65, 1);
            } else if (picNumber == 1) {
//                turn(-90, 1);
            } else {
//                turn(-117, 1);
            }
            //turn(90, 1);
            //sleep(2000);
//            driveToPositionInches(-14, 1);

            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(1000);
//            driveToPositionInches(5, 1);
        }
    }

    public void correctBoxLeftApproach(int boxNumber) {
        int inchesPerBox = 15;
        telemetry.addLine("driving to box: " + boxNumber + " or inches: " + (-inchesPerBox * boxNumber));
        telemetry.update();
//        driveToPositionInches((-inchesPerBox * boxNumber), 0.75);
    }

    public void correctBoxRightApproach(int boxNumber) {
        int inchesPerBox = 11;
//        driveToPositionInches(((inchesPerBox * 2) - (boxNumber * inchesPerBox)), 1);
    }

    public void cryptoBox(int zone) {
        claw.setLoadPosition();
        sleep(2000);
        elevator.goToUnloadBlock2();
        // pic 0 is left     pic 1 is right      pic 2 is center
        // zone 0 is blue non-audience     zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0) {
//            driveToPositionInches(-40, 1);
//            turn(-90, 1);
//            driveToPositionInches(-18, 1);
//            turn(90, 1);
            elevator.goToZero(telemetry);
            claw.setReleasePosition();
            sleep(1000);
//            driveToPositionInches(-20, 1);


        } else if (zone == 1) {
//            driveToPositionInches(-50, 1);
//            turn(90, 1);
            elevator.goToZero(telemetry);
            claw.setReleasePosition();
            sleep(2000);
//            driveToPositionInches(-13, 1);
        } else if (zone == 2) {
//            driveToPositionInches(40, 1);
//            turn(-90, 1);
//            driveToPositionInches(-18, 1);
//            turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.setReleasePosition();
            sleep(1000);
//            driveToPositionInches(-18, 1);
        } else {
//            driveToPositionInches(-50, 1);
//            turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.setReleasePosition();
            sleep(2000);
//            driveToPositionInches(-18, 1);
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
