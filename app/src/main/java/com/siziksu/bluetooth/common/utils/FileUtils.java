package com.siziksu.bluetooth.common.utils;

import android.content.Context;
import android.util.Log;

import com.siziksu.bluetooth.common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

/**
 * Class with some util methods for files.
 */
public final class FileUtils {

    @Inject
    Context context;

    public FileUtils(Context context) {
        this.context = context;
    }

    /**
     * Gets the content of an assets file.
     *
     * @param fileName the file name (including sub folders)
     *
     * @return the file content
     */
    public String getFileContent(String fileName) {
        BufferedReader reader = null;
        try {
            StringBuilder json = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            return json.toString();
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(Constants.TAG, e.getMessage(), e);
                }
            }
        }
        return "";
    }
}
