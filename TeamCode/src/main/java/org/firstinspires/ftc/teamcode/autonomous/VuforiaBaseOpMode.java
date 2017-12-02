package org.firstinspires.ftc.teamcode.autonomous;

import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.util.ramp.*;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

/**
 * Created by evancoulson on 12/3/16.
 */



public abstract class VuforiaBaseOpMode extends AutonomousOpMode {

    protected static class Target {
        public static int Wheels = 0;
        public static int Tools = 1;
        public static int Lego = 2;
        public static int Gears = 3;
    };

    VuforiaTrackables beacons;

    public void initialize() {
        initializeAllDevices();

        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AfIW5rj/////AAAAGaDrYjvjtkibrSYzQTjEFjJb+NGdODG1LJE2IVqxl0wdLW+9JZ3nIyQF2Hef7GlSLQxR/6SQ3pkFudWmzU48zdcBEYJ+HCwOH3vKFK8gJjuzrcc7nis7JrU+IMTONPctq+JTavtRk+LBhM5bxiFJhEO7CFnDqDDEFc5f720179XJOvZZA0nuCvIqwSslb+ybEVo/G8BDwH1FjGOaH/CxWaXGxVmGd4zISFBsMyrwopDI2T0pHdqvRBQ795QCuJFQjGQUtk9UU3hw/E8Z+oSC36CSWZPdpH3XkKtvSb9teM5xgomeEJ17MdV+XwTYL0iB/aRXZiXRczAtjrcederMUrNqqS0o7XvYS3eW1ViHfynl";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(Target.Wheels).setName("Wheels");
        beacons.get(Target.Tools).setName("Tools");
        beacons.get(Target.Lego).setName("Lego");
        beacons.get(Target.Gears).setName("Gears");

        beacons.activate();
    }

    public void driveToTarget(int targetNum, double minPower, double maxPower)
    {
        double minDistance = 10.0; //TODO: Find good value.
        double maxDistance = 100.0; //TODO: Find good value.

        VuforiaTrackableDefaultListener target = (VuforiaTrackableDefaultListener) beacons.get(targetNum).getListener();

        /*
            We want to ramp the speed (power) down as we get closer to the target. Between maxDistance and minDistance
            power drops exponentially from maxPower to minPower.
        */
        Ramp ramp = new ExponentialRamp(minDistance, minPower, maxDistance, maxPower);

        double distanceToTarget = Double.MAX_VALUE;
        double angleToTarget;
        double degreesToTurnToTarget;

        while (distanceToTarget > minDistance)
        {
            OpenGLMatrix  pose = target.getPose();

            if (pose != null)
            {
                VectorF translationToTarget = pose.getTranslation();
                double x = translationToTarget.get(0);
                double y = translationToTarget.get(1);
                double z = translationToTarget.get(2);

                angleToTarget = Math.atan2(z, x); // in radians
                degreesToTurnToTarget = Math.toDegrees(angleToTarget);
                distanceToTarget = Math.sqrt(z * z + x * x);  // Pythagoras calc of hypotenuse

                double power = ramp.value(distanceToTarget);

                telemetry.addData("Tracking", "Target found.");
                telemetry.addData("Translation", String.format("(%.1f, %.1f, %.1f)", x, y, z));
                telemetry.addData("Angle", "%1f degrees", degreesToTurnToTarget);
                telemetry.addData("Power", "%1f", power);

                driveSystem.mecanumDrivePolar(angleToTarget, power);
            }
            else
            {
                // Can't see the target... just drive forward

                telemetry.addData("Tracking", "Can't see target.");

                driveSystem.setPower(minPower);
            }
            telemetry.update();
            idle();
        }

        driveSystem.setPower(0);
    }
}
