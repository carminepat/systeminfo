package it.carminepat.systeminfo;

import it.carminepat.systeminfo.utils.CommandLine;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

/**
 * information about CPU
 * @author carminepat@gmail.com
 */
@Getter
public class Cpu {

    private static Cpu instance = null;

    private String processorName;
    private String processorSpeed;
    private int numberOfProcessors;
    private int numberOfCores;
    private String L3cache;
    private String L2cache;

    public static Cpu i() {
        if (instance == null) {
            instance = new Cpu();
        }
        return instance;
    }

    private Cpu() {
        this.processorName = this.initProcessorName();
        this.processorSpeed = this.initProcessorSpeed();
        this.numberOfProcessors = this.initNumberOfProcessors();
        this.numberOfCores = this.initNumberOfCores();
        this.L2cache = this.initL2cache();
        this.L3cache = this.initL3cache();
    }

    private String initProcessorName() {
        if (Os.i().isWindows()) {
            String element = "Name";
            return CommandLine.i().clearResultWindows(CommandLine.i().getResultOfExecution("wmic cpu get " + element), element.toUpperCase());
        } else if (Os.i().isMac()) {
            String regEx = "[P|p]rocessor\\s+[N|n]ame.*";
            return CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regEx, Pattern.MULTILINE);
        }
        return "";
    }

    private String initProcessorSpeed() {
        if (Os.i().isWindows()) {
            String result = CommandLine.i().getResultOfExecution("wmic cpu get MaxClockSpeed")
                    .replaceAll("\r\n", " ")
                    .replace("\\s{2,}", " ")
                    .toUpperCase()
                    .replace("MAXCLOCKSPEED", "")
                    .trim();
            return result;
        } else if (Os.i().isMac()) {
            String result = CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType");
            Pattern p = Pattern.compile("[P|p]rocessor\\s+[S|s]peed.*", Pattern.MULTILINE);
            Matcher matcher = p.matcher(result);
            if (matcher.find()) {
                String sn = matcher.group(0);
                sn = sn.substring(sn.indexOf(":") + 1).trim();
                return sn;
            }
        }
        return "";
    }

    private int initNumberOfCores() {
        if (Os.i().isWindows()) {
            String result = CommandLine.i().getResultOfExecution("wmic cpu get NumberOfCores")
                    .replaceAll("\r\n", " ")
                    .replace("\\s{2,}", " ")
                    .toUpperCase()
                    .replace("NUMBEROFCORES", "")
                    .trim();
            return Integer.parseInt(result);
        } else if (Os.i().isMac()) {
            String result = CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType");
            Pattern p = Pattern.compile("[N|n]umber\\s+of\\s+[C|c]ores.*", Pattern.MULTILINE);
            Matcher matcher = p.matcher(result);
            if (matcher.find()) {
                String sn = matcher.group(0);
                sn = sn.substring(sn.indexOf(":") + 1).trim();
                return Integer.parseInt(sn);
            }
        }
        return 0;
    }

    private int initNumberOfProcessors() {
        if (Os.i().isWindows()) {
            String result = CommandLine.i().getResultOfExecution("wmic cpu get﻿NumberOfLogicalProcessors")
                    .replaceAll("\r\n", " ")
                    .replace("\\s{2,}", " ")
                    .toUpperCase()
                    .replace("NUMBEROFLOGICALPROCESSORS", "")
                    .trim();
            return Integer.parseInt(result);
        } else if (Os.i().isMac()) {
            String result = CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType");
            Pattern p = Pattern.compile("[N|n]umber\\s+of\\s+[P|p]rocessors.*", Pattern.MULTILINE);
            Matcher matcher = p.matcher(result);
            if (matcher.find()) {
                String sn = matcher.group(0);
                sn = sn.substring(sn.indexOf(":") + 1).trim();
                return Integer.parseInt(sn);
            }
        }
        return 0;
    }

    private String initL3cache() {
        if (Os.i().isWindows()) {
            String result = CommandLine.i().getResultOfExecution("wmic cpu L3CacheSize")
                    .replaceAll("\r\n", " ")
                    .replace("\\s{2,}", " ")
                    .toUpperCase()
                    .replace("L3CACHESIZE", "")
                    .trim();
            return result;
        } else if (Os.i().isMac()) {
            String result = CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType");
            Pattern p = Pattern.compile("[L|l]3\\s+[C|c]ache.*", Pattern.MULTILINE);
            Matcher matcher = p.matcher(result);
            if (matcher.find()) {
                String sn = matcher.group(0);
                sn = sn.substring(sn.indexOf(":") + 1).trim();
                return sn;
            }
        }
        return "";
    }

    private String initL2cache() {
        if (Os.i().isWindows()) {
            String result = CommandLine.i().getResultOfExecution("wmic cpu L2CacheSize")
                    .replaceAll("\r\n", " ")
                    .replace("\\s{2,}", " ")
                    .toUpperCase()
                    .replace("L2CACHESIZE", "")
                    .trim();
            return result;
        } else if (Os.i().isMac()) {
            String result = CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType");
            Pattern p = Pattern.compile("[L|l]2\\s+[C|c]ache.*", Pattern.MULTILINE);
            Matcher matcher = p.matcher(result);
            if (matcher.find()) {
                String sn = matcher.group(0);
                sn = sn.substring(sn.indexOf(":") + 1).trim();
                return sn;
            }
        }
        return "";
    }
}
