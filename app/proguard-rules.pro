# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/Cellar/android-sdk/24.4.1_1/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Gson specific classes
-dontwarn sun.misc.**
-keepattributes *Annotation*
-keep class io.github.anthonyeef.cattle.data.**{*;}
-keep class io.github.anthonyeef.cattle.entity.**{*;}
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Retrofit 2
-dontwarn okio.**
-dontwarn org.conscrypt.**
-dontwarn javax.annotation.concurrent.GuardedBy
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-keepclassmembernames interface * {
    @retrofit.http.* <methods>;
}
-keep class io.github.anthonyeef.cattle.service.** {*;}

# Kotlin
-dontwarn kotlin.**
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

# RecyclerView
-keep class android.support.v7.widget.** {*;}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.GeneratedAppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# Prettytime
-keep class org.ocpsoft.prettytime.i18n.**

# self
-keep class io.github.anthonyeef.view.bahavior.FloatingActionButtonScrollAwareBehavior