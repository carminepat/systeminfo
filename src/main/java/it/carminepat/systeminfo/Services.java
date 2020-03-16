package it.carminepat.systeminfo;

import it.carminepat.systeminfo.utils.CommandLine;
import it.carminepat.systeminfo.utils.Str;
import java.util.ArrayList;
import java.util.List;

/**
 * information and operation about services
 *
 * @author carminepat@gmail.com
 */
public class Services {

    public enum StartModeType {
        DISABLED, MANUAL, AUTOMATIC
    };
    private static Services instance = null;

    private Services() {

    }

    public static Services i() {
        if (instance == null) {
            instance = new Services();
        }
        return instance;
    }

    /**
     * get all list of services in this system
     *
     * @return List<SingleService>
     */
    public List<SingleService> getListOfServices() {
        if (Os.i().isWindows()) {
            return this.getListServiceWindowsByCMD("wmic service get name,Caption,StartMode,started,ProcessID,PathName,DisplayName,Status,AcceptStop,state");
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    /**
     * get all list of services stopped in this system
     *
     * @return List<SingleService>
     */
    public List<SingleService> getListOfServicesStopped() {
        if (Os.i().isWindows()) {
            return this.getListServiceWindowsByCMD("wmic service where state=\"Stopped\" get name,Caption,StartMode,started,ProcessID,PathName,DisplayName,Status,AcceptStop,state");
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    /**
     * get all list of services runnung in this system
     *
     * @return List<SingleService>
     */
    public List<SingleService> getListOfServicesRunning() {
        if (Os.i().isWindows()) {
            return this.getListServiceWindowsByCMD("wmic service where state=\"Running\" get name,Caption,StartMode,started,ProcessID,PathName,DisplayName,Status,AcceptStop,state");
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    public List<SingleService> getListOfServicesAcceptStop() {
        if (Os.i().isWindows()) {
            return this.getListServiceWindowsByCMD("wmic service where AcceptStop=true get name,Caption,StartMode,started,ProcessID,PathName,DisplayName,Status,AcceptStop,state");
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    public List<SingleService> getListOfServicesNotAcceptStop() {
        if (Os.i().isWindows()) {
            return this.getListServiceWindowsByCMD("wmic service where AcceptStop=false get name,Caption,StartMode,started,ProcessID,PathName,DisplayName,Status,AcceptStop,state");
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    public SingleService getServiceByName(String name) {
        if (Os.i().isWindows()) {
            List<SingleService> l = this.getListServiceWindowsByCMD(String.format("wmic service where Name=\"%s\" get name,Caption,StartMode,started,ProcessID,PathName,DisplayName,Status,AcceptStop,state", name));
            if (l != null && !l.isEmpty()) {
                return l.get(0);
            }
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
        return null;
    }

    public SingleService getServiceByPID(int PID) {
        if (Os.i().isWindows()) {
            List<SingleService> l = this.getListServiceWindowsByCMD(String.format("wmic service where ProcessId=%s get name,Caption,StartMode,started,ProcessID,PathName,DisplayName,Status,AcceptStop,state", PID));
            if (l != null && !l.isEmpty()) {
                return l.get(0);
            }
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
        return null;
    }

    public void startService(String name) {
        if (Os.i().isWindows()) {
            CommandLine.i().getResultOfExecution(String.format("wmic service where Name=\"%s\" call startservice", name));
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    public void stopService(String name) {
        if (Os.i().isWindows()) {
            CommandLine.i().getResultOfExecution(String.format("wmic service where Name=\"%s\" call stopservice", name));
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    public void changeStartMode(String name, StartModeType MODE) {
        if (Os.i().isWindows()) {
            String modeCommand = "";
            switch (MODE) {
                case AUTOMATIC:
                    modeCommand = "automatic";
                    break;
                case MANUAL:
                    modeCommand = "manual";
                    break;
                case DISABLED:
                    modeCommand = "disabled";
                    break;
            }
            CommandLine.i().getResultOfExecution(String.format("wmic service where Name=\"%s\" call changestartmode %s", name, modeCommand));
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
    }

    private List<SingleService> getListServiceWindowsByCMD(String cmd) {
        List<SingleService> l = new ArrayList<>();
        if (cmd != null && !"".equals(cmd)) {
            String result = CommandLine.i().clearResultWindowsWithLine(CommandLine.i().getResultOfExecution(cmd), "");
            List<String> rows = CommandLine.i().getStringInLines(result);
            for (int i = 1; i < rows.size(); i++) {
                String row = rows.get(i);
                String[] elements = row.split("\\s{2,}");
                if (elements != null && elements.length == 10) {
                    SingleService ser = new SingleService();
                    ser.setAcceptStop(Str.i().getBooleanFromString(elements[0]));
                    ser.setCaption(elements[1]);
                    ser.setDisplayName(elements[2]);
                    ser.setName(elements[3]);
                    ser.setPathName(elements[4]);
                    ser.setPID(Str.i().getStringToInt(elements[5]));
                    ser.setStarted(Str.i().getBooleanFromString(elements[6]));
                    ser.setStartMode(elements[7]);
                    ser.setState(elements[8]);
                    ser.setStatus(elements[9]);
                    l.add(ser);
                } else {
                    System.err.println("not valuable row: " + row);
                }
            }
        }
        return l;
    }
}
