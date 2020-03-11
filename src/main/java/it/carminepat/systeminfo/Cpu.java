package it.carminepat.systeminfo;

import it.carminepat.systeminfo.utils.CommandLine;
import java.util.regex.Pattern;
import lombok.Getter;

/**
 * information about CPU
 *
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
            String element = "MaxClockSpeed";
            return CommandLine.i().clearResultWindows(CommandLine.i().getResultOfExecution("wmic cpu get " + element), element.toUpperCase());
        } else if (Os.i().isMac()) {
            String regex = "[P|p]rocessor\\s+[S|s]peed.*";
            return CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regex, Pattern.MULTILINE);
        }
        return "";
    }

    private int initNumberOfCores() {
        if (Os.i().isWindows()) {
            String element = "NumberOfCores";
            String result = CommandLine.i().clearResultWindows(CommandLine.i().getResultOfExecution("wmic cpu get " + element), element.toUpperCase());
            if (result != null && !"".equals(result)) {
                try {
                    return Integer.parseInt(result);
                } catch (Exception e) {
                    System.err.println("Not parseable integer " + result + ". Error: " + e);
                }
            }
        } else if (Os.i().isMac()) {
            String regex = "[N|n]umber\\s+of\\s+[C|c]ores.*";
            String result = CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regex, Pattern.MULTILINE);
            if (result != null && !"".equals(result)) {
                try {
                    return Integer.parseInt(result);
                } catch (Exception e) {
                    System.err.println("Not parseable integer " + result + ". Error: " + e);
                }
            }
        }
        return 0;
    }

    private int initNumberOfProcessors() {
        if (Os.i().isWindows()) {
            String element = "NumberOfLogicalProcessors";
            String result = CommandLine.i().clearResultWindows(CommandLine.i().getResultOfExecution("wmic cpu get﻿ " + element), element.toUpperCase());
            if (result != null && !"".equals(result)) {
                try {
                    return Integer.parseInt(result);
                } catch (Exception e) {
                    System.err.println("Not integer parseable " + result + ". Error: " + e);
                }
            }
        } else if (Os.i().isMac()) {
            String regex = "[N|n]umber\\s+of\\s+[P|p]rocessors.*";
            String result = CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regex, Pattern.MULTILINE);
            if (result != null && !"".equals(result)) {
                try {
                    return Integer.parseInt(result);
                } catch (Exception e) {
                    System.err.println("Not integer parseable " + result + ". Error: " + e);
                }
            }
        }
        return 0;
    }

    private String initL3cache() {
        if (Os.i().isWindows()) {
            String element = "L3CacheSize";
            return CommandLine.i().clearResultWindows(CommandLine.i().getResultOfExecution("wmic cpu get " + element), element.toUpperCase());
        } else if (Os.i().isMac()) {
            String regex = "[L|l]3\\s+[C|c]ache.*";
            return CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regex, Pattern.MULTILINE);
        }
        return "";
    }

    private String initL2cache() {
        if (Os.i().isWindows()) {
            String element = "L2CacheSize";
            return CommandLine.i().clearResultWindows(CommandLine.i().getResultOfExecution("wmic cpu get " + element), element.toUpperCase());
        } else if (Os.i().isMac()) {
            String regex = "[L|l]2\\s+[C|c]ache.*";
            return CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regex, Pattern.MULTILINE);
        }
        return "";
    }
}
