LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := F1jni
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \

LOCAL_SRC_FILES := \
	D:\Projects\AndroidStudioProject\F1-1000\F1_api\src\main\jni\F1_cardrdr.cpp \
	D:\Projects\AndroidStudioProject\F1-1000\F1_api\src\main\jni\F1_jni.cpp \

LOCAL_C_INCLUDES += D:\Projects\AndroidStudioProject\F1-1000\F1_api\src\main\jni
LOCAL_C_INCLUDES += D:\Projects\AndroidStudioProject\F1-1000\F1_api\src\release\jni

include $(BUILD_SHARED_LIBRARY)
