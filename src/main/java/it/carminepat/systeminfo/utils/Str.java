package it.carminepat.systeminfo.utils;

/**
 *
 * @author carmi
 */
public class Str {

    private static Str instance = null;

    public static Str i() {
        if (instance == null) {
            instance = new Str();
        }
        return instance;
    }

    public String getOnlyNumber(String source) {
        if (source != null && !"".equals(source)) {
            return source.replaceAll("\\D", "");
        }
        return "";
    }

    public long getStringToLong(String s) {
        s=this.getOnlyNumber(s);
        if (s != null && !"".equals(s) && s.matches("\\d+")) {
            try {
                return Long.parseLong(s);
            } catch (Exception e) {
                System.err.println(String.format("Error in parsing of string [string: %s] [error: %s]", s, e));
            }
        }
        return 0;
    }
}
