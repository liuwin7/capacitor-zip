package com.bechara.capacitorplugins.capacitorzip;

import android.Manifest;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.getcapacitor.JSObject;

@CapacitorPlugin(
    name = "CapacitorZip",
    permissions = {
        @Permission(alias = "storage", strings = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE })
    }
)
public class CapacitorZipPlugin extends Plugin {

    private final CapacitorZip implementation = new CapacitorZip();

    @PluginMethod
    public void unzip(PluginCall call) {
        if (getPermissionState("storage") != PermissionState.GRANTED) {
            requestPermissionForAlias("storage", call, "storagePermissionCallback");
        } else {
            unzipFile(call);
        }
    }

    @PermissionCallback
    private void storagePermissionCallback(PluginCall call) {
        if (getPermissionState("storage") == PermissionState.GRANTED) {
            unzipFile(call);
        } else {
            call.reject("Permission is required to access storage", ErrorCodes.NO_PERMISSION);
        }
    }

    private void unzipFile(PluginCall call) {
        implementation.unzipFile(call, new ZipResultCallback() {
            @Override
            public void progress(JSObject pObj) {
                notifyListeners("onProgress", pObj);
            }
        });
    }
}
