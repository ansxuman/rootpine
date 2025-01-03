package com.ansxuman.rootpine.data.repository;

import com.ansxuman.rootpine.data.model.CheckType;
import com.ansxuman.rootpine.data.model.RootCheckResult;
import com.ansxuman.rootpine.domain.util.RootUtil;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.pm.PackageManager;

public class RootCheckRepository {
    private final RootUtil rootUtil;
    private final PackageManager packageManager;

    public RootCheckRepository(Context context) {
        this.rootUtil = new RootUtil();
        this.packageManager = context.getPackageManager();
    }

    public List<RootCheckResult> performAllChecks() {
        List<RootCheckResult> results = new ArrayList<>();
        
        // SU Binary Check
        boolean suBinaryFound = rootUtil.checkSuBinary();
        results.add(new RootCheckResult(
            CheckType.SU_BINARY,
            suBinaryFound,
            suBinaryFound ? "SU binary detected" : "No SU binary found"
        ));

        // Magisk Check
        boolean magiskFound = rootUtil.checkMagisk();
        results.add(new RootCheckResult(
            CheckType.MAGISK,
            magiskFound,
            magiskFound ? "Magisk detected" : "No Magisk found"
        ));

        // Root Management Apps
        boolean rootAppsFound = rootUtil.checkRootManagementApps(packageManager);
        results.add(new RootCheckResult(
            CheckType.ROOT_MANAGEMENT,
            rootAppsFound,
            rootAppsFound ? "Root management apps found" : "No root management apps detected"
        ));

        // Dangerous Apps
        boolean dangerousAppsFound = rootUtil.checkDangerousApps(packageManager);
        results.add(new RootCheckResult(
            CheckType.DANGEROUS_APPS,
            dangerousAppsFound,
            dangerousAppsFound ? "Dangerous apps detected" : "No dangerous apps found"
        ));

        // Custom ROM Check
        boolean customRomFound = rootUtil.checkCustomROM();
        results.add(new RootCheckResult(
            CheckType.CUSTOM_ROM,
            customRomFound,
            customRomFound ? "Custom ROM detected" : "Stock ROM detected"
        ));

        // Dangerous Props
        boolean dangerousProps = rootUtil.checkDangerousProps();
        results.add(new RootCheckResult(
            CheckType.DANGEROUS_PROPS,
            dangerousProps,
            dangerousProps ? "Dangerous system properties found" : "System properties are secure"
        ));

        // Busybox Check
        boolean busyboxFound = rootUtil.checkBusyboxBinary();
        results.add(new RootCheckResult(
            CheckType.BUSYBOX,
            busyboxFound,
            busyboxFound ? "Busybox binary found" : "No Busybox binary detected"
        ));

        return results;
    }
}