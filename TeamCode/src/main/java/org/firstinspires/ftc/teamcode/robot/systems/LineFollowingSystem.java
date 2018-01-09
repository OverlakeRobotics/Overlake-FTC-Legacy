package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robot.data.ColorSensorData;
import org.firstinspires.ftc.teamcode.robot.data.HueData;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 */
public class LineFollowingSystem
{
    ColorSensorData data;


    public ColorSensor lineColorSensor;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;

    /* Constructor */
    public LineFollowingSystem(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {
        // Save reference to Hardware map
        this.hwMap = hwMap;

        this.lineColorSensor = this.hwMap.get(ColorSensor.class, "line_color_sensor");
    }

    public boolean isLineColorSensorOverHue(HueData hue)
    {
        return (hue.isHue(HueData.fromRGB(this.lineColorSensor.red(), this.lineColorSensor.green(), this.lineColorSensor.blue())));
    }
}

