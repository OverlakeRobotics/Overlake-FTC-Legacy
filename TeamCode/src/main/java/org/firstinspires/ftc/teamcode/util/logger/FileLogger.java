package org.firstinspires.ftc.teamcode.util.logger;

import android.app.Application;

/**
 * Created by EvanCoulson on 1/4/18.
 */

public class FileLogger extends Application {
    public FileLogger() {
        if (isExternalStorageWriteable()) {

        }
    }

    private boolean isExternalStorageWriteable() {
        return true;
    }
}
