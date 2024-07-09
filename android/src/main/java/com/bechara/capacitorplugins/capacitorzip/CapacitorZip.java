package com.bechara.capacitorplugins.capacitorzip;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import java.io.File;
import java.io.IOException;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import org.json.JSONObject;


public class CapacitorZip {

    public void unzipFile(PluginCall call, final ZipResultCallback callback) {
        String source = call.getString("source", "");
        String destination = call.getString("destination", "");
        String password = call.getString("password", "");

        assert source != null;
        assert destination != null;
        assert password != null;

        // Convert capacitor absolute path to native absolute path
        source = convertToNativePath(source);
        destination = convertToNativePath(destination);

        // Validate Inputs
        if (source.isEmpty()) {
            call.reject("No source specified", ErrorCodes.NO_SOURCE_SPECIFIED);
            return;
        }
        if (destination.isEmpty()) {
            call.reject("No destination specified", ErrorCodes.NO_DESTINATION_SPECIFIED);
            return;
        }

        File file = new File(source);
        if (!file.exists()) {
            call.reject("File doesn't exist", ErrorCodes.NO_FILE_EXISTS);
            return;
        }

        // Create zip instance
        ZipFile zipFile = new ZipFile(source);
        // progress monitor
        ProgressMonitor progressMonitor = zipFile.getProgressMonitor();

        // Check zip file validity
        if (!zipFile.isValidZipFile()) {
            call.reject("File is corrupted or not valid", ErrorCodes.NO_FILE_VALID);
            return;
        }

        try {
            // Check zip encryption
            boolean isEncrypted = zipFile.isEncrypted();
            if (isEncrypted && password.isEmpty()) {
                call.reject("Zip is encrypted, specify a password", ErrorCodes.NO_PASSWORD_SPECIFIED);
                return;
            }

            if (isEncrypted) {
                zipFile.setPassword(password.toCharArray());
            }

            File d = new File(destination);
            if (!d.exists()) {
                d.mkdirs();
            }

            // Unzip in background
            zipFile.setRunInThread(true);
            zipFile.extractAll(destination);

            Thread progressThread = new Thread(() -> {
                // update the progress
                while (!progressMonitor.getState().equals(ProgressMonitor.State.READY)) {
                    JSObject ret = new JSObject();
                    ret.put("progress", progressMonitor.getPercentDone());
                    ret.put("current", progressMonitor.getFileName());
                    ret.put("task", progressMonitor.getCurrentTask());
                    callback.progress(ret);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            progressThread.start();

            zipFile.close();

            JSObject ret = new JSObject();
            ret.put("message", ErrorCodes.SUCCESS);
            ret.put("uri", "file://" + destination);
            call.resolve(ret);
        } catch (ZipException e) {
            call.reject("Zip Error Occurred", ErrorCodes.UNKNOWN_ERROR, e);
            e.printStackTrace();
        } catch (IOException e) {
            call.reject("Error Occurred", ErrorCodes.UNKNOWN_ERROR, e);
            e.printStackTrace();
        }
    }

    private String convertToNativePath(String capacitorPath) {
        String nativePath = capacitorPath;
        if (capacitorPath.contains("_capacitor_")) {
            nativePath = nativePath.replace("_capacitor_", "");
        }
        if (nativePath.contains("file://")) {
            nativePath = nativePath.replace("file://", "");
        }
        return nativePath;
    }
}
