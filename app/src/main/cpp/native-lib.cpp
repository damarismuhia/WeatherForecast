#include <jni.h>
#include <string>

#include <unistd.h>
#include <sys/ptrace.h>
#include <sys/wait.h>
#include <pthread.h> static

extern "C" {

JNIEXPORT jstring JNICALL
Java_com_dmuhia_weatherforecast_WeatherApplication_getBaseURL(JNIEnv *env, jobject thiz) {
    return env-> NewStringUTF("https://api.openweathermap.org/data/2.5/");
}
JNIEXPORT jstring JNICALL
Java_com_dmuhia_weatherforecast_WeatherApplication_getApiKey(JNIEnv *env, jobject thiz) {
    return env-> NewStringUTF("cfe577b09f43deea2722462eea76e473");

}
JNIEXPORT jstring JNICALL
Java_com_dmuhia_weatherforecast_WeatherApplication_getCipherKey(JNIEnv *env, jobject thiz) {
    return env-> NewStringUTF("sqlcipher_key_alias");

}

JNIEXPORT jint JNICALL
Java_com_dmuhia_weatherforecast_WeatherApplication_rootFunction(JNIEnv *env, jobject thiz)  {
    const char *paths[] = {
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su",
            "/data/local/xbin/",
            "/system/bin/",
            "/system/bin/.ext/",
            "/system/bin/failsafe/",
            "/system/sd/xbin/",
            "/su/xbin/",
            "/su/bin/",
            "/magisk/.core/bin/",
            "/system/usr/we-need-root/",
            "/system/xbin/"};
    int counter = 0;
    while (counter < 9) {
        if (FILE *file = fopen(paths[counter], "r")) {
            fclose(file);
            return 1;
        }
        counter++;
    }
    return 0;
}
}
