LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := yssl
LOCAL_SRC_FILES := yssl.cpp

include $(BUILD_SHARED_LIBRARY)
