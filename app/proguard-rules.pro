# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepclassmembers class com.nhzw.bingdu.fragment.NewsDetailFragment$JavascriptInterface {
   public *;
}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify

-dontwarn com.bugtags.library.**
-dontwarn com.handmark.**
-dontwarn com.mob.**
-dontwarn com.xiaomi.**
-dontwarn com.umeng.**
-dontwarn in.srain.cube.**
-dontwarn android.support.**
-dontwarn com.google.**
-dontwarn twitter4j.**
-dontwarn oauth.**
-dontwarn org.**
-dontwarn com.distimo.**
-dontwarn com.adobe.**
-dontwarn com.scmp.newspulse.fragment.qrcode.**
-dontwarn net.sourceforge.zbar.**
-dontwarn com.enrique.stackblur.**
-dontwarn com.sina.weibo.sdk.**
-dontwarn com.baidu.**
-dontwarn com.litesuits.http.**
-dontwarn com.github.moduth.**
-dontwarn com.igexin.**

## so文件
#-libraryjars libs/arm64-v8a/liblocSDK6a.so
#-libraryjars libs/arm64-v8a/libtvhelper.so
#
#-libraryjars libs/armeabi-v7a/liblocSDK6a.so
#
#-libraryjars libs/x86/liblocSDK6a.so
#-libraryjars libs/x86/libtvhelper.so
#
#-libraryjars libs/x86_64/liblocSDK6a.so
#
#-libraryjars libs/armeabi/liblocSDK6a.so
#-libraryjars libs/armeabi/libtvhelper.so

-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations, RuntimeVisibleParameterAnnotations, RuntimeInvisibleParameterAnnotations,AnnotationDefault
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService

-keep class android.support.** { *; }
-keep class android.app.** { *; }
-keep interface android.support.v4.app.** { *; }

-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

-keepattributes Signature
-keepattributes SourceFile,LineNumberTable


-keep class **$Properties

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keep class **.R$* { *; }

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
 public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * extends android.app.Activity {  # 保持自定义控件类不被混淆
    public void *(android.view.View);
}

-keepclassmembers class * extends android.support.v4.app.FragmentActivity {  # 保持自定义控件类不被混淆
    public void *(android.view.View);
}


############################第三方混淆#################################第三方混淆########################################第三方混淆###########################


## ----------------------------------
##      butterknife
## ----------------------------------
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

## ----------------------------------
##      utilcode
## ----------------------------------
-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**

## ----------------------------------
##      nohttp
## ----------------------------------
-keepclassmembers class ** {
    private javax.net.ssl.SSLSocketFactory delegate;
}

## ----------------------------------
##      BRVAH
## ----------------------------------
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

## ----------------------------------
##      sharesdk
## ----------------------------------

-keep class cn.sharesdk.onekeyshare.** { *; }
-keep class cn.sharesdk.** { *; }
-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class com.alipay.share.sdk.**{*;}
-keep class **.R$* {
    *;
}

## ----------------------------------
##      友盟
## ----------------------------------
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep class com.umeng.** { *; }


## ----------------------------------
##   glide混淆
## ----------------------------------
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

## ----------------------------------
##   fastjson
## ----------------------------------
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*; }
-keep class com.jewel.model.**{*;}


## ----------------------------------
##   greendao
## ----------------------------------
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use Rx:
-dontwarn rx.**

## ----------------------------------
##   bugly
## ----------------------------------
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}