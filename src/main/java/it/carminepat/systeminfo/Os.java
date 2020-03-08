package it.carminepat.systeminfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.carminepat.systeminfo.utils.CommandLine;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
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

    private String initHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            System.err.println("Error hostname" + e);
        }
        return "";
    }

    private String initSerialNumber() {
        if (isWindows()) {
            String result = CommandLine.i().getResultOfExecution("wmic os get serialNumber")
                    .replaceAll("\r\n", " ")
                    .replace("\\s{2,}", " ")
                    .toUpperCase()
                    .replace("SERIALNUMBER", "")
                    .trim();
            return result;
        } else if (isMac()) {
            String result = CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType");
            Pattern p = Pattern.compile("[S|s]erial\\s+[N|n]umber.*", Pattern.MULTILINE);
            Matcher matcher = p.matcher(result);
            if (matcher.find()) {
                String sn = matcher.group(0);
                sn = sn.substring(sn.indexOf(":") + 1).trim();
                return sn;
            }
        }
        return "";
    }

    private String initHardwareUUID() {
        if (isWindows()) {
            String result = CommandLine.i().getResultOfExecution("wmic csproduct get UUID")
                    .replaceAll("\r\n", " ")
                    .replace("\\s{2,}", " ")
                    .toUpperCase()
                    .replace("UUID", "")
                    .trim();
            return result;
        } else if (isMac()) {
            String result = CommandLine.i().getResultOfExecution("system_profiler SPHardwareDataType");
            Pattern p = Pattern.compile("[H|h]ardware\\s+[UUID|uuid].*", Pattern.MULTILINE);
            Matcher matcher = p.matcher(result);
            if (matcher.find()) {
                String hu = matcher.group(0);
                hu = hu.substring(hu.indexOf(":") + 1).trim();
                return hu;
            }
        }
        return "";
    }

    private String initLastBootTime() {
        if (isWindows()) {
            try {
                String result = CommandLine.i().getResultOfExecution("wmic os get lastBootUpTime")
                        .replaceAll("\r\n", " ")
                        .replace("\\s{2,}", " ")
                        .toUpperCase()
                        .replace("lastBootUpTime", "")
                        .trim();
                result = result.replaceAll("\\.\\d+.*", "");
                Date d = new SimpleDateFormat("yyyyMMddHHmmss").parse(result);
                return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(d);
            } catch (Exception e) {
                System.err.println("Error formatting date" + e);
            }
        } else if (isMac()) {
            try {
                String result = CommandLine.i().getResultOfExecution("system_profiler SPDiagnosticsDataType");
                Pattern p = Pattern.compile("[L|l]ast\\s+[R|r]un.*", Pattern.MULTILINE);
                Matcher matcher = p.matcher(result);
                if (matcher.find()) {
                    String lr = matcher.group(0);
                    lr = lr.substring(lr.indexOf(":") + 1).trim();
                    Date d = new SimpleDateFormat("dd/MM/yy, HH:mm").parse(lr);
                    return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(d);
                }
            } catch (Exception e) {
                System.err.println("Error formatting date" + e);
            }
        }
        return "";
    }

    public String getFullName() {
        String fn = String.format("%s %s", this.name, this.version);
        return fn;
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
