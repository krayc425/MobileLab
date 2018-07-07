package com.android.server.led;

import android.hardware.nju.led.V1_0.ILed;
import android.nju.led.ILedService;
import android.util.Slog;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import com.android.server.SystemService;

public class LedService extends SystemService {
    private static final String TAG = "LedService";
    private ILed mLed = null;

    public LedService(Context context) {
        super(context);
        Slog.i(TAG, "constructor begin");
        try {
            mLed = ILed.getService();
        } catch (Exception e) {
            Slog.e(TAG, "Exception in constructor: " + String.valueOf(e));
        }
        Slog.i(TAG, "constructor end");
    }

    @Override
    public void onStart() {
        publishBinderService("nju_led", mService);
    }

    private final IBinder mService = new ILedService.Stub() {
        @Override
        public void openLed() {
            final long token = Binder.clearCallingIdentity();
            try {
                Slog.i(TAG, "openLed begin");
                mLed.openLed();
                Slog.i(TAG, "openLed finish");
            } catch (Exception e) {
                Slog.e(TAG, "Exception: " + String.valueOf(e));
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        @Override
        public void closeLed() {
            final long token = Binder.clearCallingIdentity();
            try {
                Slog.i(TAG, "closeLed begin");
                mLed.closeLed();
                Slog.i(TAG, "closeLed end");
            } catch (Exception e) {
                Slog.e(TAG, "Exception: " + String.valueOf(e));
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }
    };
}