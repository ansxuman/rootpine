package com.ansxuman.rootpine.domain.util;

import android.content.pm.PackageManager;
import android.os.Build;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class RootUtil {
    private static final List<String> ROOT_PACKAGES = Arrays.asList(
        "com.topjohnwu.magisk",
        "com.noshufou.android.su",
        "com.thirdparty.superuser",
        "eu.chainfire.supersu",
        "com.koushikdutta.superuser",
        "com.zachspong.temprootremovejb",
        "com.ramdroid.appquarantine",
        "com.formyhm.hideroot",
        "com.amphoras.hidemyroot",
        "com.saurik.substrate"
    );

    private static final List<String> DANGEROUS_APPS = Arrays.asList(
        "com.chelpus.lackypatch",
        "com.dimonvideo.luckypatcher",
        "com.android.vending.billing.InAppBillingService.LUCK",
        "com.android.vending.billing.InAppBillingService.COIN",
        "com.android.vending.billing.InAppBillingService.CRAC",
        "com.xmodgame",
        "com.cih.game_cih",
        "com.charles.lpoqasert",
        "catch_.me_.if_.you_.can_"
    );

    public boolean checkSuBinary() {
        String[] paths = {
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/system/su",
            "/system/bin/.ext/su",
            "/system/usr/we-need-root/su"
        };

        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    public boolean checkMagisk() {
        return new File("/sbin/.magisk").exists() ||
               new File("/system/xbin/magisk").exists();
    }

    public boolean checkDangerousProps() {
        String[] props = {
            "ro.debuggable",
            "ro.secure",
            "ro.build.type",
            "ro.build.tags",
            "ro.build.selinux"
        };

        try {
            for (String prop : props) {
                String value = System.getProperty(prop);
                if (value != null && 
                    (value.contains("test-keys") || 
                     value.equals("1") || 
                     value.contains("userdebug"))) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkRootManagementApps(PackageManager packageManager) {
        for (String packageName : ROOT_PACKAGES) {
            try {
                packageManager.getPackageInfo(packageName, 0);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                // Package not found, continue checking
            }
        }
        return false;
    }

    public boolean checkDangerousApps(PackageManager packageManager) {
        for (String packageName : DANGEROUS_APPS) {
            try {
                packageManager.getPackageInfo(packageName, 0);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                // Package not found, continue checking
            }
        }
        return false;
    }

    public boolean checkBusyboxBinary() {
        String[] paths = {
            "/system/xbin/busybox",
            "/system/bin/busybox"
        };
        
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    public boolean checkCustomROM() {
        String buildTags = Build.TAGS;
        String buildFingerprint = Build.FINGERPRINT.toLowerCase();
        String buildManufacturer = Build.MANUFACTURER.toLowerCase();
        
        return (buildTags != null && buildTags.contains("test-keys")) ||
               buildFingerprint.contains("custom") ||
               buildFingerprint.contains("lineage") ||
               buildFingerprint.contains("generic") ||
               buildManufacturer.contains("genymotion") ||
               buildFingerprint.contains("release-keys") ||
               buildFingerprint.contains("userdebug");
    }
}