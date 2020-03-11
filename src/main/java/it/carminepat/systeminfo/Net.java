package it.carminepat.systeminfo;

/**
 * information about network
 *
 * @author carminepat@gmail.com
 */
public class Net {

    private String ethernetLocalIP;
    private String externalIP;

    private static Net instance = null;

    private Net() {

    }

    public static Net i() {
        if (instance == null) {
            instance = new Net();
        }
        return instance;
    }

}
