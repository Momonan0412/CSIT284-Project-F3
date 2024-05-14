package com.example.quizapplication.utils;

import android.os.StrictMode;
/**
 * Utility class for configuring and enabling StrictMode to detect potential issues
 * related to resource management and prevent Application Not Responding (ANR) errors.
 *
 * This class provides a fix for situations where rapid or frequent clicks on UI elements,
 * such as TextViews, may lead to ANR errors by enabling StrictMode with policies to
 * detect leaked Closeable objects (e.g., cursors) and leaked SQLite objects.
 *
 * To use this utility, call the {@link #enableStrictMode()} method during the
 * application's initialization phase, such as in the {@code onCreate()} method of
 * the {@link android.app.Application} class or the main activity's {@code onCreate()} method.
 *
 * Example usage:
 *
 * {@code
 * public class MyApplication extends Application {
 *
 *     {@literal @}Override
 *     public void onCreate() {
 *         super.onCreate();
 *         StrictModeUtils.enableStrictMode();
 *     }
 * }
 * }
 */

public class StrictModeUtil {
    private StrictModeUtil(){};
    public static void enableStrictMode() {
        StrictMode.VmPolicy policy = new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .penaltyDeath()
                .penaltyLog()
                .build();
        StrictMode.setVmPolicy(policy);
    }
}
