package it.carminepat.systeminfo.utils;

import java.text.DecimalFormat;

/**
 *
 * @author cpaternoster
 */
public class Converter {

    public String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        String result = null;
        result = new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
        return result;
    }
}
