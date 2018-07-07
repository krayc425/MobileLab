#define LOG_TAG "HAL_Led"

#include "Led.h"
#include <log/log.h>
#include <cstdio>
#include <unistd.h>

namespace android {
namespace hardware {
namespace nju {
namespace led {
namespace V1_0 {
namespace implementation {

#define GPIO_VALUE_CLOSE "echo 0 > /sys/class/gpio/gpio492/value"  // difference is possible
#define GPIO_VALUE_OPEN "echo 1 > /sys/class/gpio/gpio492/value"   // difference is possible
#define GPIO_DIR "echo out > /sys/class/gpio/gpio492/direction"    // difference is possible
#define GPIO_OPEN "echo 492 > /sys/class/gpio/export"

FILE* fp;

// Methods from ::android::hardware::nju::led::V1_0::ILed follow.
Return<void> Led::openLed() {
    ALOGI("hw_openLed begin");
    if ((fp = popen(GPIO_OPEN, "r")) == NULL) {
        ALOGE("Fail to GPIO_OPEN");
        return Void();
    }

    if ((fp = popen(GPIO_DIR, "r")) == NULL) {
        ALOGE("Fail to GPIO_DIR");
        return Void(); 
    }

    if ((fp = popen(GPIO_VALUE_OPEN, "r")) == NULL) {
        ALOGE("Fail to GPIO_DIR");
        return Void(); 
    }

    pclose(fp);
    ALOGI("hw_openLed end");
    return Void();
}

Return<void> Led::closeLed() {
    ALOGI("hw_closeLed begin");
    if ((fp = popen(GPIO_VALUE_CLOSE, "r")) == NULL) {
        ALOGE("Fail to GPIO_DIR");
        return Void(); 
    }

    pclose(fp);
    ALOGI("hw_closeLed end");

    return Void();
}

// Methods from ::android::hidl::base::V1_0::IBase follow.

// ILed* HIDL_FETCH_ILed(const char* /* name */) {
//     return new Led();
// }
//
}  // namespace implementation
}  // namespace V1_0
}  // namespace led
}  // namespace nju
}  // namespace hardware
}  // namespace android
