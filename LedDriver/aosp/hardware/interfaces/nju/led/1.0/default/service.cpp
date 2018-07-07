#define LOG_TAG "android.hardware.nju.led@1.0-service"

#include <android/hardware/nju/led/1.0/ILed.h>
#include <hidl/LegacySupport.h>

#include "Led.h"

using android::hardware::nju::led::V1_0::ILed;
using android::hardware::nju::led::V1_0::implementation::Led;
using android::hardware::configureRpcThreadpool;
using android::hardware::joinRpcThreadpool;
using android::sp;
using android::status_t;
using android::OK;

int main() {
    //android::ProcessState::initWithDriver("/dev/hwbinder");
    // return defaultPassthroughServiceImplementation<ILed>();

    configureRpcThreadpool(1, true);

    sp<ILed> led = new Led;
    status_t status = led->registerAsService();
    ALOGW_IF(status != OK, "Could not register ILed v1.0");
    ALOGD("Led default service is ready.");

    joinRpcThreadpool();
    return 1;

}
