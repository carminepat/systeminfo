package it.carminepat.systeminfo.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author carminepat
 */
public class CommandLine {
    
    private static CommandLine instance = null;
    
    public static CommandLine i() {
        if (instance == null) {
            instance = new CommandLine();
        }
        return instance;
    }
    
    private CommandLine() {
    }
    
    ;
    
    public String getResultOfExecution(String command) {
        String result = "";
        try {
            StringBuilder sb = new StringBuilder();
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            result = sb.toString();
            int exitEval = process.waitFor();
            if (exitEval != 0) {
                System.err.println("Error!!! " + exitEval);
            }
        } catch (Exception e) {
            System.err.println(String.format("Error in command exec [cmd: %s] [error: %s]", command, e));
        }
        return result;
    }
    
    public String clearResultWindows(String result, String element) {
        result = result.replaceAll("\r\n", " ")
                .replace("\\s{2,}", " ")
                .toUpperCase()
                .replace(element.toUpperCase(), "")
                .trim();
        return result;
    }
    
    public String clearResultWindowsWithLine(String result, String element) {
        result = result.toUpperCase()
                .replace(element.toUpperCase(), "")
                .trim();
        return result;
    }
    
    public List<String> getStringInLines(String s) {
        List<String> l = new ArrayList<>();
        if (s != null && !"".equals(s)) {
            String[] split = s.split("\r\n");
            for (String iesimo : split) {
                if (!iesimo.replaceAll("\\r\\n", "").isBlank()) {
                    l.add(iesimo);
                }
            }
        }
        return l;
    }
    
    public String clearResultMac(String result, String regex, int patternType) {
        Pattern p = Pattern.compile(regex, patternType);
        Matcher matcher = p.matcher(result);
        if (matcher.find()) {
            String sn = matcher.group(0);
            sn = sn.substring(sn.lastIndexOf(":") + 1).trim();
            return sn;
        }
        return "";
    }
}
