package com.example.layout_version;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.amazonaws.ivs.broadcast.BroadcastException;
import com.amazonaws.ivs.broadcast.BroadcastSession;
import com.amazonaws.ivs.broadcast.Device;
import com.amazonaws.ivs.broadcast.ImageDevice;
import com.amazonaws.ivs.broadcast.ImagePreviewView;
import com.amazonaws.ivs.broadcast.Presets;
import com.amazonaws.ivs.player.PlayerView;

import android.Manifest;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class LiveStreamActivity extends AppCompatActivity {

    FrameLayout previewHolder;
    BroadcastSession.Listener broadcastListener;
    BroadcastSession broadcastSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stream);
        BroadcastSession.Listener broadcastListener = new BroadcastSession.Listener() {
            @Override
            public void onStateChanged(@NonNull BroadcastSession.State state) {
                Log.d("TAG", "State=" + state);
            }

            @Override
            public void onError(@NonNull BroadcastException exception) {
                Log.e("TAG", "Exception: " + exception);
            }
        };

        final String[] requiredPermissions =
                { Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };



        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // If any permissions are missing we want to just request them all.
                ActivityCompat.requestPermissions(this, requiredPermissions, 0x100);
                break;
            }
        }

    }

    public void setupBroadcastSession()
    {

        // Create a broadcast-session instance and sign up to receive broadcast
// events and errors.
        Context ctx = getApplicationContext();
        broadcastSession = new BroadcastSession(ctx,
                broadcastListener,
                Presets.Configuration.STANDARD_PORTRAIT,
                Presets.Devices.FRONT_CAMERA(ctx));

        // awaitDeviceChanges will fire on the main thread after all pending devices
// attachments have been completed


        broadcastSession.awaitDeviceChanges(() -> {
            for(Device device: broadcastSession.listAttachedDevices()) {
                // Find the camera we attached earlier
                if(device.getDescriptor().type == Device.Descriptor.DeviceType.CAMERA) {
                    previewHolder = findViewById(R.id.frameLayout);
                    ImagePreviewView preview = ((ImageDevice)device).getPreviewView();
                    preview.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
                    previewHolder.addView(preview);
                }
            }
        });

        broadcastSession.start(
                "rtmps://1958e2d97d88.global-contribute.live-video.net:443/app/",
                "sk_us-east-1_DdqDOfelQCU9_ofTx6s4yekNFgesMT8eLdWIS9k8zLV");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions, grantResults);
        if (requestCode == 0x100) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }
            setupBroadcastSession();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        previewHolder.removeAllViews();
        broadcastSession.release();
    }
}