package it.carminepat.systeminfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.carminepat.systeminfo.utils.CommandLine;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;
import lombok.Getter;

/**
 * information about operating system
 *
 * @author carminepat@gmail.com
 *
 */
@Getter
public class Os {

    private String name;
    private String version;
    private String arch;
    private String country;
    private String language;
    private String displayCountry;
    private String displayLanguage;
    private String displayTimeZone;
    private String timeZone;
    private Date dateTime;
    private String displayDateTime;
    private String hostName;
    private String serialNumber;
    private String hardwareUUID;
    private String lastBootTime;

    private static Os instance = null;

    public static Os i() {
        if (instance == null) {
            instance = new Os();
        }
        return instance;
    }

    private Os() {
        this.arch = System.getProperty("os.arch");
        this.name = System.getProperty("os.name");
        this.version = System.getProperty("os.version");
        this.country = Locale.getDefault().getCountry();
        this.language = Locale.getDefault().getLanguage();
        this.displayCountry = Locale.getDefault().getDisplayCountry();
        this.displayLanguage = Locale.getDefault().getDisplayLanguage();
        this.timeZone = TimeZone.getDefault().getID();
        this.displayTimeZone = TimeZone.getDefault().getDisplayName();
        this.dateTime = new Date();
        this.displayDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dateTime);
        this.hostName = this.initHostName();
        this.serialNumber = this.initSerialNumber();
        this.hardwareUUID = this.initHardwareUUID();
        this.lastBootTime = this.initLastBootTime();
    }

    @JsonIgnore
    public String getEnvironmentVariable(String s) {
        if (s != null && !"".equals(s)) {
            return System.getenv(s);
        }
        return "";
    }

    @JsonIgnore
    public Map<String, String> getEnvironmentVariables() {
        return System.getenv();
    }

    //TODO backup old environment
    @JsonIgnore
    public void setEnvironmentVariable(String key, String value) {
        if (key != null && !"".equals(key) && value != null && !"".equals(value)) {
            if (value.contains(" ")) {
                value = String.format("\"%s\"", value);
            }
            CommandLine.i().getResultOfExecution(String.format("setx %s %s", key, value));
        }
    }

    //TODO backup old environment
    @JsonIgnore
    public void addToEnvironmentVariable(String key, String value) {
        if (key != null && !"".equals(key) && value != null && !"".equals(value)) {
            String oldValue = this.getEnvironmentVariable(key);
            if (oldValue != null && !"".equals(oldValue)) {
                oldValue = oldValue + ";" + value;
            } else {
                oldValue = value;
            }
            if (oldValue.contains(" ")) {
                oldValue = String.format("\"%s\"", oldValue);
            }
            CommandLine.i().getResultOfExecution(String.format("setx %s %s", key, oldValue));
        }
    }

    private String initHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            System.err.println("Error hostname" + e);
        }
        return "";
    }

    /**
     * serial number of OS (operating system)
     *
     * @return String
     */
    private String initSerialNumber() {
        if (isWindows()) {
            String element = "serialNumber";
            return CommandLine.i().clearResultWindows(CommandLine.i().getResultOfExecution("wmic os get " + element), element.toUpperCase());
        } else if (isMac()) {
            String regex = "[S|s]erial\\s+[N|n]umber.*";
            return CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regex, Pattern.MULTILINE);
        }
        return "";
    }

    /**
     * UUID of hardware of machine
     *
     * @return String
     */
    private String initHardwareUUID() {
        if (isWindows()) {
            String element = "UUID";
            return CommandLine.i().clearResultWindows(CommandLine.i().getResultOfExecution("wmic csproduct get " + element), element);
        } else if (isMac()) {
            String regex = "[H|h]ardware\\s+[UUID|uuid].*";
            return CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType"), regex, Pattern.MULTILINE);
        }
        return "";
    }

    /**
     * date about last boot time
     *
     * @return String
     */
    private String initLastBootTime() {
        if (isWindows()) {
            try {
                String element = "lastBootUpTime";
                String result = CommandLine.i().clearResultWindows(CommandLine.i().getResultOfExecution("wmic os get " + element), element.toUpperCase());
                if (result != null && !"".equals(result)) {
                    Date d = new SimpleDateFormat("yyyyMMddHHmmss").parse(result);
                    return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(d);
                }
            } catch (Exception e) {
                System.err.println("Error formatting date " + e);
            }
        } else if (isMac()) {
            try {
                String regex = "[L|l]ast\\s+[R|r]un.*";
                String result = CommandLine.i().clearResultMac(CommandLine.i().getResultOfExecution("system_profiler SPDiagnosticsDataType"), regex, Pattern.MULTILINE);
                if (result != null && !"".equals(result)) {
                    Date d = new SimpleDateFormat("dd/MM/yy, HH:mm").parse(result);
                    return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(d);
                }
            } catch (Exception e) {
                System.err.println("Error formatting date " + e);
            }
        }
        return "";
    }

    public String getFullName() {
        String fn = String.format("%s %s", this.name, this.version);
        return fn;
    }

    public void restart() {
        if (Os.i().isWindows()) {
            CommandLine.i().getResultOfExecution("shutdown /r");
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    public void restartAfterSeconds(int seconds) {
        if (Os.i().isWindows()) {
            CommandLine.i().getResultOfExecution("shutdown /r -t " + seconds);
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    public void shutdown() {
        if (Os.i().isWindows()) {
            CommandLine.i().getResultOfExecution("shutdown /s");
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    public void shutdownAfterSeconds(int seconds) {
        if (Os.i().isWindows()) {
            CommandLine.i().getResultOfExecution("shutdown /s -t " + seconds);
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    @JsonIgnore
    public boolean isWindows() {
        return this.name.toLowerCase().contains("win");
    }

    @JsonIgnore
    public boolean isMac() {
        return (this.name.toLowerCase().contains("mac"));
    }

    @JsonIgnore
    public boolean isUnix() {
        return (this.name.toLowerCase().contains("nix") || this.name.toLowerCase().contains("nux") || this.name.toLowerCase().contains("aix") || this.name.toLowerCase().contains("freebsd"));
    }

    @JsonIgnore
    public boolean isSolaris() {
        return (this.name.toLowerCase().contains("sunos") || this.name.toLowerCase().contains("solaris") || this.name.toLowerCase().contains("sun os"));
    }

    @JsonIgnore
    public Date getDateTime() {
        return dateTime;
    }

}
