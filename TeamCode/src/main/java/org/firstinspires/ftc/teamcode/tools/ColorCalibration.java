package org.firstinspires.ftc.teamcode.tools;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.robot.data.ColorSensorData;
import org.firstinspires.ftc.teamcode.robot.data.HueData;

@TeleOp(name="ColorCalibration")
@Disabled
public class ColorCalibration extends LinearOpMode
{
    ColorSensor sensorRGB;
    DeviceInterfaceModule cdim;
    static final int LED_CHANNEL = 5;

    // TODO: Implement this program for multiple sensors with an array of sensors
    ColorSensorData[] data;
    String message = "Program starting (next: RedBeacon)";

    @Override
    public void runOpMode() throws InterruptedException
    {
        hardwareMap.logDevices();
        //cdim = hardwareMap.deviceInterfaceModule.get("dim");
        sensorRGB = hardwareMap.colorSensor.get("color");
        //cdim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
        //cdim.setDigitalChannelState(LED_CHANNEL, true);

        composeDashboard();
        telemetry.update();
        data = new ColorSensorData[1];

        for (int i = 0; i < data.length; i++)
        {
            data[i] = new ColorSensorData();
            //getData(data[i].grayTile, "Gray tile calibrated (next: redTape)");
            //getData(data[i].redTape, "Red tape calibrated (next: blueTape)");
            //getData(data[i].blueTape, "Blue tape calibrated (next: whiteTape)");
            //getData(data[i].whiteTape, "White tape calibrated (next: redBeacon)");
            getData(data[i].redBeacon, "Red beacon calibrated (next: blueBeacon)");
            getData(data[i].blueBeacon, "Blue beacon calibrated (sending to file)");
        }

        ColorSensorData.toFile("/sdcard/FIRST/colorSensorData.txt", data);
        message = "Sent to file!";
        telemetry.update();
    }

    public void getData(HueData hue, String message)
    {
        boolean a = false;
        while (!a)
        {
            idle();
            a = gamepad1.a;
        }

        boolean b = false;
        while (!b)
        {
            idle();
            b = gamepad1.b;
        }

        for(int i = 0; i < 10; i++) {
            hue.addSample(hueFromRGB(sensorRGB.red(), sensorRGB.green(), sensorRGB.blue()));
            sleep(200);
        }

        hue.red = sensorRGB.red();
        hue.blue = sensorRGB.blue();
        hue.green = sensorRGB.green();

        this.message = message;
        telemetry.update();
    }

    public static double hueFromRGB(int r, int g, int b)
    {
        double y = Math.sqrt(3) * (g - b);
        double x = 2 * r - g - b;
        return Math.atan2(y, x) * (360.0 / (2 * Math.PI));
    }



    void composeDashboard()
    {
        telemetry.setMsTransmissionInterval(200);
        telemetry.addLine()
                .addData("Red: ", new Func<Object>() {
                    public Object value() {
                        return sensorRGB.red();
                    }
                })
                .addData ("Green: ", new Func<Object>() {
                    public Object value() {
                        return sensorRGB.green();
                    }
                })
                .addData ("Blue: ", new Func<Object>() {
                    public Object value() {
                        return sensorRGB.blue();
                    }
                });

        telemetry.addLine().addData( "Message: ", new Func<Object>()
                {
                    public Object value()
                    {
                        return message;
                    }
                });
    }
}

