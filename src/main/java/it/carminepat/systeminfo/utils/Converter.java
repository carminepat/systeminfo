package it.carminepat.systeminfo.utils;

import java.text.DecimalFormat;

/**
 *
 * @author cpaternoster
 */
public class Converter {

    private static Converter instance = null;

    public static Converter i() {
        if (instance == null) {
            instance = new Converter();
        }
        return instance;
    }

    public String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        String result = null;
        //long number = Math.round(size / Math.pow(1024, digitGroups));
        //result = new DecimalFormat("#,##0.#").format(number) + " " + units[digitGroups];
        result = new DecimalFormat("#,##0.00").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
        return result;
    }

    public long convertUnitSizeToByte(String s) {
        if (s != null && !"".equals(s)) {
            String unit = s.replaceAll("\\d+", "").replaceAll("\\.|\\,", "").trim().toUpperCase();
            long number = Str.i().getStringToLong(s);
            switch (unit) {
                case "EB":
                    return (long) (number * Math.pow(1024, 6));
                case "PB":
                    return (long) (number * Math.pow(1024, 5));
                case "TB":
                    return (long) (number * Math.pow(1024, 4));
                case "GB":
                    return (long) (number * Math.pow(1024, 3));
                case "MB":
                    return (long) (number * Math.pow(1024, 2));
                case "KB":
                    return number * 1024;
            }
        }
        return 0;
    }

    public long convertMbToKb(long MegaBytes) {
        if (MegaBytes == 0) {
            return MegaBytes;
        }
        return MegaBytes * 1024;
    }
}
