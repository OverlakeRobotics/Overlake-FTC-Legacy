package org.firstinspires.ftc.teamcode.util.logger;

import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

/**
 * Created by EvanCoulson on 1/4/18.
 */

public class FileLogger extends Application {

    private File logFile;
    private FileWriter writer;

    public FileLogger(String fileName) {
        if (isExternalStorageWriteable()) {
            File appDir = new File(Environment.getExternalStorageDirectory() + "/Overlake_FTC");
            File logDir = new File(appDir + "/logs");
            this.logFile = new File(logDir, fileName + ".txt");

            if (!appDir.exists()) {
                appDir.mkdir();
            }

            if (!logDir.exists()) {
                logDir.mkdir();
            }

            try {
                this.writer = new FileWriter(logFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void log(String level, String data) {
        Date d = new Date();
        try {
            writer.append("[" + d.toString() + "] " + level + ": " + data);
            writer.flush();
        } catch(Exception e) {
            e.printStackTrace();;
        }
    }

    private boolean isExternalStorageWriteable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
