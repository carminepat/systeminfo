package it.carminepat.systeminfo;

import it.carminepat.systeminfo.utils.CommandLine;
import java.util.regex.Pattern;
import lombok.Getter;

/**
 * information about memory
 *
 * @author carminepat@gmail.com
 */
@Getter
public class Memory {

    private String totalInstalled;

    private static Memory instance = null;

    public static Memory i() {
        if (instance == null) {
            instance = new Memory();
        }
        return instance;
    }

    private Memory() {
        this.totalInstalled = this.initTotalInstalled();
    }

    private String initTotalInstalled() {
        if (Os.i().isWindows()) {
            String regex = "[M|m]emoria\\s+[F|f]isica\\s+[T|t]otale\\:.*";
            return CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("systeminfo"), regex, Pattern.MULTILINE);
        } else if (Os.i().isMac()) {
            String regEx = "[M|m]emory\\:\\s+.*";
            return CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regEx, Pattern.MULTILINE);
        }
        return "";
    }
}
