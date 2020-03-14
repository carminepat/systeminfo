package it.carminepat.systeminfo;

import it.carminepat.systeminfo.utils.CommandLine;
import it.carminepat.systeminfo.utils.Converter;
import it.carminepat.systeminfo.utils.Str;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * information about process
 *
 * @author cpaternoster
 */
public class Process {

    private static Process instance = null;

    private Process() {

    }

    public static Process i() {
        if (instance == null) {
            instance = new Process();
        }
        return instance;
    }

    /**
     * get ordered List (by memory use desc) of SingleProcess active in this OS
     *
     * @return List<SingleProcess>
     */
    public List<SingleProcess> getListOfProcess() {
        List<SingleProcess> l = new ArrayList<>();
        if (Os.i().isWindows()) {
            l = this.getListOfWindowsFromCMD("tasklist");
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
        if (l != null && !l.isEmpty()) {
            Collections.sort(l);
        }
        return l;
    }

    /**
     * get first five process ordered by memory used
     *
     * @return List<SingleProcess>
     */
    public List<SingleProcess> getListOfFirstFiveProcess() {
        List<SingleProcess> l = new ArrayList<>();
        if (Os.i().isWindows()) {
            l = this.getListOfProcess();
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
        if (l != null && !l.isEmpty()) {
            Collections.sort(l);
            if (l.size() > 5) {
                l = l.subList(0, 5);
            }
        }
        return l;
    }

    /**
     * get process use great than megaBytes memory specified in parameter
     *
     * @param megaBytes number
     * @return List<SingleProcess>
     */
    public List<SingleProcess> getListOfProcessMemoryGTMb(long megaBytes) {
        List<SingleProcess> l = new ArrayList<>();
        if (Os.i().isWindows()) {
            if (megaBytes == 0) {
                l = this.getListOfProcess();
            } else {
                long Kb = Converter.i().convertMbToKb(megaBytes);
                String cmdGetTask = "tasklist";
                cmdGetTask = String.format("%s /FI \"MEMUSAGE gt %s\"", cmdGetTask, Kb);
                l = this.getListOfWindowsFromCMD(cmdGetTask);
            }
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
        if (l != null && !l.isEmpty()) {
            Collections.sort(l);
        }
        return l;
    }

    /**
     * get filtered list of active process, filter by name
     *
     * @param filter name of process
     * @return List<SingleProcess>
     */
    public List<SingleProcess> getListOfProcessByName(String filter) {
        List<SingleProcess> l = new ArrayList<>();
        if (Os.i().isWindows()) {
            String cmdGetTask = "tasklist";
            if (filter != null && !"".equals(filter)) {
                if (!filter.trim().endsWith(".exe")) {
                    filter = filter.trim() + ".exe";
                }
                cmdGetTask = String.format("%s /FI \"ImageName eq %s\"", cmdGetTask, filter);
            }
            l = this.getListOfWindowsFromCMD(cmdGetTask);
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
        if (l != null && !l.isEmpty()) {
            Collections.sort(l);
        }
        return l;
    }

    /**
     * get filtered list of active process, filter by username
     *
     * @param username
     * @return List<SingleProcess>
     */
    public List<SingleProcess> getListOfProcessByUser(String filter) {
        List<SingleProcess> l = new ArrayList<>();
        if (Os.i().isWindows()) {
            String cmdGetTask = "tasklist";
            if (filter != null && !"".equals(filter)) {
                cmdGetTask = String.format("%s /FI \"USERNAME eq %s\"", cmdGetTask, filter);
            }
            l = this.getListOfWindowsFromCMD(cmdGetTask);
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
        if (l != null && !l.isEmpty()) {
            Collections.sort(l);
        }
        return l;
    }

    /**
     * get filtered SingleProcess by PID
     *
     * @param pid process id to get information
     * @return SingleProcess
     */
    public SingleProcess getProcessByPid(int pid) {
        SingleProcess result = null;
        if (Os.i().isWindows()) {
            String cmdGetTask = "tasklist";
            cmdGetTask = String.format("%s /FI \"PID eq %s\"", cmdGetTask, pid);
            List<SingleProcess> l = this.getListOfWindowsFromCMD(cmdGetTask);
            if (l != null && l.size() > 0) {
                result = l.get(0);
            }
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }
        return result;
    }

    public void killTask(int pid) {
        if (Os.i().isWindows()) {
            CommandLine.i().getResultOfExecution(String.format("taskkill /F /PID %s", pid));
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this od");
        }

    }

    /**
     * implementation of get information of process by windows
     *
     * @param cmdTask command to send in a console
     * @return List<SingleProcess> process
     */
    private List<SingleProcess> getListOfWindowsFromCMD(String cmdTask) {
        List<SingleProcess> l = new ArrayList<>();
        String result = CommandLine.i().clearResultWindowsWithLine(CommandLine.i().getResultOfExecution(cmdTask), "");
        List<String> split = CommandLine.i().getStringInLines(result);
        for (int i = 2; i < split.size(); i++) {
            String singleRow = split.get(i);
            String[] elementOfRow = singleRow.split("\\s{2,}");
            if (elementOfRow != null && elementOfRow.length == 4) {
                SingleProcess sp = new SingleProcess();
                sp.setName(elementOfRow[0]);
                String pidNameSession = elementOfRow[1];
                sp.setPid(Str.i().getStringToInt(Str.i().getOnlyNumber(pidNameSession)));
                sp.setNameSession(Str.i().getOnlyString(pidNameSession));
                sp.setSessionNumber(Str.i().getStringToInt(elementOfRow[2]));
                String memory = elementOfRow[3].trim();
                if (memory.toUpperCase().endsWith("K")) {
                    memory = memory + "b";
                }
                sp.setMemoryL(Converter.i().convertUnitSizeToByte(memory));
                sp.setMomory(Converter.i().readableFileSize(sp.getMemoryL()));
                l.add(sp);
            } else {
                System.err.println("Sorry, row not valuable: " + singleRow);
            }
        }
        return l;
    }

}
