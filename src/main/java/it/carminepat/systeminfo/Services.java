package it.carminepat.systeminfo;

/**
 * information and operation about services
 *
 * @author carminepat@gmail.com
 */
public class Services {

    private static Services instance = null;

    private Services() {

    }

    public static Services i() {
        if (instance == null) {
            instance = new Services();
        }
        return instance;
    }
}
