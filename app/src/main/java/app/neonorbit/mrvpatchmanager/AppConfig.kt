package app.neonorbit.mrvpatchmanager

import app.neonorbit.mrvpatchmanager.apk.ApkUtil
import app.neonorbit.mrvpatchmanager.apk.AppType
import app.neonorbit.mrvpatchmanager.data.AppItemData
import java.io.File

object AppConfig {
    private const val DOWNLOAD_DIR_NAME = "download"
    private const val CACHE_TEMP_DIR_NAME = "temp_dir"
    private const val PATCHED_APK_DIR_NAME = "patched"
    private const val PATCHED_OUT_DIR_NAME = "patch_out"

    const val MODULE_PACKAGE = "app.neonorbit.chatheadenabler"
    const val MANAGER_PACKAGE = "app.neonorbit.mrvpatchmanager"

    const val MODULE_APK_NAME = "Module.apk"
    const val MANAGER_APK_NAME = "Manager.apk"
    const val MODULE_ASSET_NAME = "module.pkg"

    val TEMP_DIR: File get() = AppServices.getCacheDir(CACHE_TEMP_DIR_NAME)

    val DOWNLOAD_DIR: File get() = AppServices.getCacheDir(DOWNLOAD_DIR_NAME)

    val PATCHED_OUT_DIR: File get() = AppServices.getCacheDir(PATCHED_OUT_DIR_NAME)

    val PATCHED_APK_DIR: File get() = AppServices.getFilesDir(PATCHED_APK_DIR_NAME)

    fun getDownloadApkFile(type: AppType) = File(DOWNLOAD_DIR, "${type.getName()}.apk")

    fun getPatchedApkFile(file: File) = ApkUtil.getApkSimpleInfo(file)?.let { info ->
        (getFbAppName(info.pkg) ?: info.name.replace(' ', '-')) + "-v${info.version}.apk"
    }?.let { name -> File(PATCHED_APK_DIR, name) }

    fun getFbAppName(type: AppType): String {
        return when(type) {
            AppType.FACEBOOK -> "Facebook"
            AppType.MESSENGER -> "Messenger"
            AppType.FACEBOOK_LITE -> "Facebook-Lite"
            AppType.MESSENGER_LITE -> "Messenger-Lite"
            AppType.BUSINESS_SUITE -> "Business-Suite"
        }
    }

    private fun getFbAppName(pkg: String): String? {
        return when(pkg) {
            "com.facebook.katana" -> "Facebook"
            "com.facebook.orca" -> "Messenger"
            "com.facebook.lite" -> "Facebook-Lite"
            "com.facebook.mlite" -> "Messenger-Lite"
            "com.facebook.pages.app" -> "Business-Suite"
            else -> null
        }
    }

    val DEFAULT_FB_PACKAGES: List<String> = listOf() // removed:
    val DEFAULT_FB_SIGNATURE: String = "" // removed: BuildConfig.FB_SIG
    val MRV_PUBLIC_SIGNATURE: String = "" // removed: BuildConfig.MRV_SIG

    val FB_APP_LIST by lazy {
        listOf(
            AppItemData("Messenger App", AppType.MESSENGER, R.drawable.ic_fb_orca),
            AppItemData("Facebook App", AppType.FACEBOOK, R.drawable.ic_fb_katana),
            AppItemData("Facebook Lite", AppType.FACEBOOK_LITE, R.drawable.ic_fb_lite),
            AppItemData("Messenger Lite", AppType.MESSENGER_LITE, R.drawable.ic_fb_mlite),
            AppItemData("Business Suite", AppType.BUSINESS_SUITE, R.drawable.ic_fb_page),
        )
    }
}
