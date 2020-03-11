package it.carminepat.systeminfo;

import it.carminepat.systeminfo.utils.CommandLine;
import it.carminepat.systeminfo.utils.win.WinSystemInfoCache;
import java.util.regex.Pattern;
import lombok.Getter;

/**
 * information about memory
 *
 * @author carminepat@gmail.com
 */
@Getter
public class Memory {

    private String physicalTotalInstalled;
    private String physicalFree;
    private String physicalInUse;
    private String virtualTotalInstalled;
    private String virtualFree;
    private String virtualInUse;

    private static Memory instance = null;

    public static Memory i() {
        if (instance == null) {
            instance = new Memory();
        }
        return instance;
    }

    private Memory() {
        this.physicalTotalInstalled = this.initPhysicalTotalInstalled();
        this.physicalFree = this.initPhysicalFree();
        this.virtualTotalInstalled = this.initVirtualTotalInstalled();
        this.virtualFree = this.initVirtualFree();
        this.virtualInUse = this.initVirtualInUse();
    }

    private String initPhysicalTotalInstalled() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[F|f]isica\\s+[T|t]otale\\:.*";
            return CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
        } else if (Os.i().isMac()) {
            String regEx = "[M|m]emory\\:\\s+.*";
            return CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regEx, Pattern.MULTILINE);
        }
        return "";
    }

    private String initPhysicalFree() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[F|f]isica\\s+[D|d]isponibile\\:.*";
            return CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
        } else if (Os.i().isMac()) {

        }
        return "";
    }

    private String initVirtualTotalInstalled() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[V|v]irtuale\\:\\s+[D|d]imensione\\s+[M|m]assima\\:.*";
            return CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
        } else if (Os.i().isMac()) {

        }
        return "";
    }

    private String initVirtualFree() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[V|v]irtuale\\:\\s+[D|d]isponibile\\:.*";
            return CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
        } else if (Os.i().isMac()) {

        }
        return "";
    }

    private String initVirtualInUse() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[V|v]irtuale\\:\\s+[I|i]n\\s+[U|u]so\\:.*";
            return CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
        } else if (Os.i().isMac()) {

        }
        return "";
    }
}
