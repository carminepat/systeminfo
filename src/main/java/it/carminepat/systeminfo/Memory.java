package it.carminepat.systeminfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.carminepat.systeminfo.utils.CommandLine;
import it.carminepat.systeminfo.utils.Converter;
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

    private String physicalTotalInstalled = "0";
    @JsonIgnore
    private long physicalTotalInstalledL = 0;
    private String physicalFree = "0";
    @JsonIgnore
    private long physicalFreeL = 0;
    private String physicalInUse = "0";
    @JsonIgnore
    private long physicalInUseL = 0;
    private String virtualTotalInstalled = "0";
    @JsonIgnore
    private long virtualTotalInstalledL = 0;
    private String virtualFree = "0";
    @JsonIgnore
    private long virtualFreeL = 0;
    private String virtualInUse = "0";
    @JsonIgnore
    private long virtualInUseL = 0;

    private static Memory instance = null;

    public static Memory i() {
        if (instance == null) {
            instance = new Memory();
        }
        return instance;
    }

    private Memory() {
        this.initPhysicalTotalInstalled();
        this.initPhysicalFree();
        this.initVirtualTotalInstalled();
        this.initVirtualFree();
        this.initVirtualInUse();
    }

    private void initPhysicalTotalInstalled() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[F|f]isica\\s+[T|t]otale\\:.*";
            String result = CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
            this.physicalTotalInstalledL = Converter.i().convertUnitSizeToByte(result);
            this.physicalTotalInstalled = Converter.i().readableFileSize(this.physicalTotalInstalledL);
        } else if (Os.i().isMac()) {
            String regEx = "[M|m]emory\\:\\s+.*";
            CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regEx, Pattern.MULTILINE);
        }
    }

    private void initPhysicalFree() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[F|f]isica\\s+[D|d]isponibile\\:.*";
            String result = CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
            this.physicalFreeL = Converter.i().convertUnitSizeToByte(result);
            this.physicalInUseL = this.physicalTotalInstalledL - this.physicalFreeL;
            this.physicalFree = Converter.i().readableFileSize(this.physicalFreeL);
            this.physicalInUse = Converter.i().readableFileSize(this.physicalInUseL);
        } else if (Os.i().isMac()) {

        }
    }

    private void initVirtualTotalInstalled() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[V|v]irtuale\\:\\s+[D|d]imensione\\s+[M|m]assima\\:.*";
            String result = CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
            this.virtualTotalInstalledL = Converter.i().convertUnitSizeToByte(result);
            this.virtualTotalInstalled = Converter.i().readableFileSize(this.virtualTotalInstalledL);
        } else if (Os.i().isMac()) {

        }
    }

    private void initVirtualFree() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[V|v]irtuale\\:\\s+[D|d]isponibile\\:.*";
            String result = CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
            this.virtualFreeL = Converter.i().convertUnitSizeToByte(result);
            this.virtualFree = Converter.i().readableFileSize(this.virtualFreeL);
        } else if (Os.i().isMac()) {

        }
    }

    private void initVirtualInUse() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[V|v]irtuale\\:\\s+[I|i]n\\s+[U|u]so\\:.*";
            String result = CommandLine.i().clearResultMac(WinSystemInfoCache.i().getSystemInfo(), regex, Pattern.MULTILINE);
            this.virtualInUseL = Converter.i().convertUnitSizeToByte(result);
            this.virtualInUse = Converter.i().readableFileSize(this.virtualInUseL);
        } else if (Os.i().isMac()) {

        }
    }
}
