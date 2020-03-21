package it.carminepat.systeminfo;

import it.carminepat.systeminfo.utils.CommandLine;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author carminepat@gmail.com
 */
@Getter
public class Program {

    //wmic product list full /format:csv
    private static String csv_separator_win = ",";
    private List<SingleProgram> programs;

    private static Program instance = null;

    public static Program i() {
        if (instance == null) {
            instance = new Program();
        }
        return instance;
    }

    private Program() {
        this.programs = this.initPrograms();
    }

    private List<SingleProgram> initPrograms() {
        List<SingleProgram> lista = new ArrayList<>();
        if (Os.i().isWindows()) {
            List<String> results = CommandLine.i().getStringInLines(CommandLine.i().getResultOfExecution("wmic product list full /format:csv"));
            if (results != null && !results.isEmpty()) {
                for (int i = 1; i < results.size(); i++) {
                    String[] elements = results.get(i).split(csv_separator_win);
                    if (elements != null && elements.length > 1) {
                        SingleProgram s = new SingleProgram();
                        s.setDescription(elements[1]);
                        s.setIdentifyingNumber(elements[2]);
                        s.setInstallDate(elements[3]);
                        s.setInstallLocation(elements[4]);
                        s.setInstallState(elements[5]);
                        s.setName(elements[6]);
                        s.setPackageCache(elements[7]);
                        s.setVendor(elements[9]);
                        s.setVersion(elements[10]);
                        lista.add(s);
                    }
                }
            }
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this OS");
        }
        return lista;
    }

}
