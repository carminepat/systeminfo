package it.carminepat.systeminfo.utils.win;

import it.carminepat.systeminfo.utils.CommandLine;

/**
 *
 * @author cpaternoster@gmail.com
 */
public class WinSystemInfoCache {

    private String systemInfo;
    private long lastUpdated = 0;
    private final int numberSecondsOfCache = 30;

    private static WinSystemInfoCache instance = null;

    private WinSystemInfoCache() {
        this.systemInfo = this.initSystemInfo();
    }

    public static WinSystemInfoCache i() {
        if (instance == null) {
            instance = new WinSystemInfoCache();
        }
        return instance;
    }

    public String getSystemInfo() {
        return systemInfo;
    }

    private String initSystemInfo() {
        if (this.systemInfo == null || "".equals(this.systemInfo)) {
            return CommandLine.i().getResultOfExecution("systeminfo");
        } else if (isUpdatable() == true) {
            String res = CommandLine.i().getResultOfExecution("systeminfo");
            this.lastUpdated = System.currentTimeMillis();
            return res;
        } else {
            return this.systemInfo;
        }
    }

    private boolean isUpdatable() {
        long msFromLastCache = (System.currentTimeMillis() - lastUpdated);
        long msOccurs = numberSecondsOfCache * 1000;
        if (lastUpdated == 0) {
            return true;
        } else if (msFromLastCache > msOccurs) {
            return true;
        }
        return false;
    }
}
