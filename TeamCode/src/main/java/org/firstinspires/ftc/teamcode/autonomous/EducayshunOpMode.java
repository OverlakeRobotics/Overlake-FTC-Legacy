package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.sun.tools.javac.comp.Todo;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.teamcode.robot.ConfigParser;
import org.firstinspires.ftc.teamcode.robot.MecanumDriveSystem;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by Steven Abbott on 9/15/2017.
 */

@Autonomous(name="EducayshunOpMode", group="Bot")
public class EducayshunOpMode extends AutonomousOpMode {
    public final double DRIVE_POWER = 0.8;
    public int zone;

    ConfigParser config;

    public EducayshunOpMode() {

        this.config = new ConfigParser("Autonomous.omc");

        zone = config.getInt("zone");


    }
    //VectorF trans = null;
    //Orientation rot = null;
    //OpenGLMatrix pose = null;
    //double  position = (1 - 0) / 2; // Start at halfway position
    public static final String TAG = "Vuforia VuMark Sample";
    //OpenGLMatrix lastLocation = null;
    //VuforiaLocalizer vuforia;

    public void runOpMode() {

        initializeAllDevices();
        sleep(2000);
        elevator.goToZero(telemetry);
        claw.goToReleasePosition();
        int struggle = eye.find();
        waitForStart();
        int determination = 1000000;
        telemetry.addLine("" + struggle);
        telemetry.update();
        /*if (struggle == determination) {
            cryptoBox(ZONE);
            telemetry.addLine("Could not determine the picture. Running on dead reckoning.");
            telemetry.update();
            sleep(3000);
        } else {
            vuforiaCryptoBox(ZONE);
        }*/
        vuforiaCryptoBox(zone);







        stop();
    }

    public void cryptoBox(int zone) {
        // pic 0 is left     pic 1 is right      pic 2 is center
        // zone 0 is blue non-audience     zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0) {
            driveToPositionInches(-40, 1);
            turn(-90, 1);
            driveToPositionInches(-18, 1);
            turn(90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(1000);
            driveToPositionInches(-20, 1);
        } else if (zone == 1) {
            driveToPositionInches(-50, 1);
            turn(90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(2000);
            driveToPositionInches(-20, 1);
        } else if (zone == 2) {
            driveToPositionInches(40, 1);
            turn(-90, 1);
            driveToPositionInches(-18, 1);
            turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(1000);
            driveToPositionInches(-20, 1);
        } else {
            driveToPositionInches(-50, 1);
            turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(2000);
            driveToPositionInches(-20, 1);
        }
        claw.goToReleasePosition();
        driveToPositionInches(5, 1);
    }

    public void vuforiaCryptoBox(int zone) {
        //Todo @Michael: Tweak the amount of inches driven at different intervals and the inches per box in right and left cryptobox approach
        /*int determination = 1000000;
        int struggle = eye.find(determination);
        if (struggle == determination) {
            cryptoBox(zone);
            telemetry.addLine("Could not determine the picture. Running on dead reckoning.");
            telemetry.update();
            sleep(3000);
        }*/
        claw.goToLoadPosition();
        sleep(1000);
        elevator.goToUnloadBlock3();
        sleep(1000);
        int picNumber = eye.look(); // 0 = left   1 = center   2 = right
        telemetry.addLine("DETERMINED THE PICTURE!!!! YAY. Its picture number " + picNumber);
        telemetry.update();
        sleep(1000);
        // pic 0 is left     pic 1 is right      pic 2 is center
        // zone 0 is blue non-audience     zone 1 is blue audience    zone 2 is red non-audience    zone 3 is red audience
        if (zone == 0) { // blue non-audience CLOSE TO GOOD
            driveToPositionInches(-40, 1);
            turn(-90, 1);

            driveToPositionInches(-9, 1);
            telemetry.addLine("We're at the first cryptobox!");
            telemetry.update();
            sleep(1000);
            correctBoxLeftApproach(picNumber);

            turn(90, 1);
            driveToPositionInches(-15, 1);
            elevator.goToZero(telemetry);
            sleep(1000);
            claw.goToReleasePosition();
            sleep(1000);
            driveToPositionInches(15, 1);
        } else if (zone == 1) { // blue audience

            driveToPositionInches(-50, 1);
            telemetry.addLine("We're at the first cryptobox!");
            telemetry.update();
            sleep(5000);
            correctBoxLeftApproach(picNumber);

            turn(90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(2000);
            driveToPositionInches(-20, 1);
        } else if (zone == 2) { // red non-audience CLOSE TO GOOD
            driveToPositionInches(40, 1);
            turn(90, 1);

            driveToPositionInches(-5, 1);
            telemetry.addLine("We're at the first cryptobox!");
            telemetry.update();
            sleep(5000);
            correctBoxRightApproach(picNumber);

            turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(1000);
            driveToPositionInches(-20, 1);
        } else { // red audience

            driveToPositionInches(-50, 1);
            telemetry.addLine("We're at the first cryptobox!");
            telemetry.update();
            sleep(5000);
            correctBoxRightApproach(picNumber);

            turn(-90, 1);
            elevator.goToZero(telemetry);
            claw.goToReleasePosition();
            sleep(2000);
            driveToPositionInches(-20, 1);
        }
        claw.goToReleasePosition();
        driveToPositionInches(5, 1);
    }

    public void correctBoxLeftApproach(int boxNumber) {
        int inchesPerBox = 15;
        telemetry.addLine("driving to box: " + boxNumber + " or inches: " + (-inchesPerBox * boxNumber));
        telemetry.update();
        driveToPositionInches((-inchesPerBox * boxNumber), 0.75);
    }

    public void correctBoxRightApproach(int boxNumber) {
        int inchesPerBox = 11;
        driveToPositionInches((33 - (boxNumber * -inchesPerBox)), 1);
    }

    public void fancyDrive(int x, int y, double power) {
        int error = 50;
        int rotError = 3;
        eye.look();
        double xp = 0.003; // Init values for xp, yp, rp, xDiff, and Ydiff are arbitrary but are meant to help with debugging
        double yp = 0.004; // while being low enough to not power a malfunctioning robot.
        double rp = 0.005;
        double xDiff = eye.trans.get(1) - x;
        double yDiff = eye.trans.get(2) - y;
        double rotDiff = eye.rot.secondAngle;
        while (xDiff > x + error || xDiff < x - error || yDiff > y + error || yDiff < y - error || rp > rotError || rp < rotError) {
            eye.look();
            rotDiff = eye.rot.secondAngle;
            xDiff = eye.trans.get(1) - x;
            yDiff = eye.trans.get(2) - y;
            if ((rotDiff * (error / rotError)) > xDiff || (rotDiff * (error / rotError)) > yDiff) {
                rp = power;
                xp = power / (rotDiff * (error / rotError) * xDiff);
                yp = power / (rotDiff * (error / rotError) * yDiff);
            } else if (xDiff > yDiff) {
                xp = power;
                yp = power / xDiff * yDiff;
                rp = power / xDiff * (rotDiff * (error / rotError));
            } else {
                yp = power;
                xp = power / yDiff * xDiff;
                rp = power / yDiff * (rotDiff * (error / rotError));
            }
            driveSystem.mecanumDrive((float)xp, (float)yp, (float)rp, 0, false);
            sleep(50);
        }
    }


    /*public void testServo() {
        //armLeft.setPosition(position);
        //armRight.setPosition(position);


    }
    public void driveBox() {
        int echsdee = 0;
        while (echsdee < 4){
            driveToPositionRevs(3, DRIVE_POWER);
            turn(90, DRIVE_POWER);
            echsdee++;
        }

    }

    public void driveBox2() {
        driveSystem.mecanumDriveXY(0, 2);
        sleep(2000);
        driveSystem.mecanumDriveXY(2, 0);
        sleep(2000);
        driveSystem.mecanumDriveXY(0, -2);
        sleep(2000);
        driveSystem.mecanumDriveXY(-2, 0);
        sleep(2000);
        driveSystem.mecanumDriveXY(0, 0);
    }*/

    public void learnIMU() {
        while (true){
            telemetry.addLine("Heading:               " + imuSystem.getHeading());
            /*telemetry.addLine("X plane Slope:         " + imuSystem.getAngleOnPlaneX());
            telemetry.addLine("Y plane slope:         " + imuSystem.getAngleOnPlaneY());
            telemetry.addLine("Linear X acceleration: " + imuSystem.getLinearAccelerationX());
            telemetry.addLine("Linear Y acceleration: " + imuSystem.getLinearAccelerationY());
            telemetry.addLine("Linear Z acceleration: " + imuSystem.getLinearAceelerationZ());
            telemetry.addLine("x distance traveled:   " + imuSystem.getPosition().x / 1000);
            telemetry.addLine("y distance traveled:   " + imuSystem.getPosition().y / 1000);
            telemetry.addLine("z distance traveled:   " + imuSystem.getPosition().z / 1000);*/
            telemetry.update();
            sleep(1);
        }
    }

    /*public void balance() {
        double power  = 0.35;
        double BALANCED_X_PLANE = 159.9375;
        double BALANCED_Y_PLANE = 0.0625;
        double MARGIN_OF_ERROR = 3;
        int CORRECTION = 150;
        while (CORRECTION == 150) {
            if (imuSystem.getAngleOnPlaneX() > BALANCED_X_PLANE + MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(power, 0);
            } else if (imuSystem.getAngleOnPlaneX() < BALANCED_X_PLANE - MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(-power, 0);
            } else {
                driveSystem.mecanumDriveXY(0, 0);
            }
            sleep(CORRECTION);
            driveSystem.mecanumDriveXY(0, 0);
            if (imuSystem.getAngleOnPlaneY() < BALANCED_Y_PLANE - MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(0, power);
            } else if (imuSystem.getAngleOnPlaneY() > BALANCED_Y_PLANE + MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(0, -power);
            } else {
                driveSystem.mecanumDriveXY(0, 0);
            }
            sleep(CORRECTION);
            driveSystem.mecanumDriveXY(0, 0);
        }
    }

    void driveToPositionAccel(double revolutions, double maxPower)
    {
        double minPower = 0.1;

        this.driveSystem.setTargetPositionRevs(revolutions);
            double targetInches = driveSystem.ticksToInches(driveSystem.revolutionsToTicks(revolutions));
            telemetry.addLine("Target position " + revolutions + " revolutions or " + targetInches + " inches.");
            telemetry.update();
        /*
            Create a Ramp that will map a distance in revolutions between 0.01 and 1.0
            onto power values between minPower and maxPower.
            When the robot is greater than 1.0 revolution from the target the power
            will be set to maxPower, but when it gets within 1.0 revolutions, the power
            will be ramped down to minPower*/




    /*Position position1 = imuSystem.getPosition();
        Ramp ramp = new ExponentialRamp(driveSystem.revolutionsToTicks(0.01), minPower,
                driveSystem.revolutionsToTicks(1.0), maxPower);

        // Wait until they are done
        driveSystem.setPower(maxPower);
        while (this.driveSystem.anyMotorsBusy())
        {
            telemetry.update();

            this.idle();

            this.driveSystem.adjustPower(ramp);
        }

        // Now that we've arrived, kill the motors so they don't just sit there buzzing
        driveSystem.setPower(0);
        //Position position2 = imuSystem.getPosition();
        double imuDistanceReached = Math.hypot(position1.x, position1.y);
        telemetry.addLine("x distance traveled: ");

        // Always leave the screen looking pretty
        telemetry.update();
    }

    public VectorF anglesFromTarget(VuforiaTrackableDefaultListener image) {
        float[] data = image.getRawPose().getData();
        float[][] rotation = {
                { data[0], data[1]},
                { data[4], data[5], data[6] },
                { data[8], data[9], data[10] }
        };
        double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
        double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
        double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);
        return new VectorF((float)thetaX, (float)thetaY, (float)thetaZ);
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    public void driveXY(int x, int y, MecanumDriveSystem driveSystem, double power){
        int error = 100;
        eye.look();
        while (eye.trans.get(1) > x + error || eye.trans.get(1) < x - error || eye.trans.get(2) > y + error || eye.trans.get(2) < y - error) {
            eye.look();
            if (eye.trans.get(1) > x + error) {//z toward and away from the picture
                //driveToPositionMM(eye.trans.get(2) - x, 0, power);
                telemetry.addLine("Too far right");
                telemetry.update();
                driveSystem.mecanumDriveXY(-1, 0);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else if (eye.trans.get(1) < x - error) {
                //driveToPositionMM(x - eye.trans.get(2), 0, power);
                telemetry.addLine("Too far left");
                telemetry.update();
                driveSystem.mecanumDriveXY(1, 0);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else {
                telemetry.addLine("Good on left right");
                telemetry.update();
                driveSystem.mecanumDriveXY(0, 0);
            }
            if (eye.trans.get(2) > y + error) {
                //driveToPositionMM(0, eye.trans.get(1) - y, power);
                telemetry.addLine("Too far forward");
                telemetry.update();
                driveSystem.mecanumDriveXY(0, 1);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else if (eye.trans.get(2) < y - error) {
                //driveToPositionMM(0, y - eye.trans.get(1), power);
                telemetry.addLine("Too far back");
                telemetry.update();
                driveSystem.mecanumDriveXY(0, -1);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else {
                telemetry.addLine("Good on forward-back");
                telemetry.update();
                driveSystem.mecanumDriveXY(0,0);
            }
        }
        telemetry.addLine("YAAAAAAYYYYY!!!!!");
        telemetry.update();
        return;
    }*/
}
