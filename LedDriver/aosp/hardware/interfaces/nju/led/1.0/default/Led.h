#ifndef ANDROID_HARDWARE_NJU_LED_V1_0_LED_H
#define ANDROID_HARDWARE_NJU_LED_V1_0_LED_H

#include <android/hardware/nju/led/1.0/ILed.h>
#include <hidl/MQDescriptor.h>
#include <hidl/Status.h>

namespace android {
namespace hardware {
namespace nju {
namespace led {
namespace V1_0 {
namespace implementation {

using ::android::hardware::nju::led::V1_0::ILed;
using ::android::hardware::Return;
using ::android::hardware::Void;
using ::android::sp;

struct Led : public ILed {
    // Methods from ::android::hardware::nju::led::V1_0::ILed follow.
    Return<void> openLed() override;
    Return<void> closeLed() override;
    // Methods from ::android::hidl::base::V1_0::IBase follow.

};

// FIXME: most likely delete, this is only for passthrough implementations
// extern "C" ILed* HIDL_FETCH_ILed(const char* name);

}  // namespace implementation
}  // namespace V1_0
}  // namespace led
}  // namespace nju
}  // namespace hardware
}  // namespace android

#endif  // ANDROID_HARDWARE_NJU_LED_V1_0_LED_H
