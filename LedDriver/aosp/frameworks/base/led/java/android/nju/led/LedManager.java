package android.nju.led;

import android.annotation.SystemApi;
import android.annotation.SystemService;

@SystemApi
@SystemService("nju_led")
public class LedManager {
    private final ILedService mLed;

    /** @hide */
    public LedManager(ILedService service) {
        mLed = service;
    }

    public void openLed() throws Exception {
        mLed.openLed();
    }

    public void closeLed() throws Exception {
        mLed.closeLed();
    }
}