package it.carminepat.systeminfo;

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
}
