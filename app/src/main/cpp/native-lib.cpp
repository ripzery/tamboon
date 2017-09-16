#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_ripzery_tamboon_utils_OmiseKeyUtils_pk(
        JNIEnv *env,
        jobject /* this */) {
    std::string pk = "pkey_test_58i9pow3dgadkocuwlm";
    return env->NewStringUTF(pk.c_str());
}