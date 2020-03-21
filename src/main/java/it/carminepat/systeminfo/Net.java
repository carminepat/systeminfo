package it.carminepat.systeminfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.carminepat.systeminfo.utils.CommandLine;
import it.carminepat.systeminfo.utils.Str;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * information about network
 *
 * @author carminepat@gmail.com
 */
@Getter
public class Net {

    @JsonIgnore
    private static String csv_separator_win = ",";
    private String localIpAddress;
    private String publicIP;
    private List<SingleNetwork> listNetwork;

    private static Net instance = null;

    private Net() {
        this.listNetwork = this.initNetworks();
        this.localIpAddress = this.initLocalIp();
        this.publicIP = this.initPublicIp();
    }

    public static Net i() {
        if (instance == null) {
            instance = new Net();
        }
        return instance;
    }

    private List<SingleNetwork> initNetworks() {
        List<SingleNetwork> lista = new ArrayList<SingleNetwork>();
        if (Os.i().isWindows()) {
            List<String> results = CommandLine.i().getStringInLines(CommandLine.i().getResultOfExecution("wmic nic get Description,DeviceID,MACAddress,Manufacturer,Name,NetConnectionID,ProductName,ServiceName /format:csv"));
            if (results != null && !results.isEmpty()) {
                for (int i = 1; i < results.size(); i++) {
                    String[] elements = results.get(i).split(csv_separator_win);
                    if (elements != null && elements.length > 1) {
                        SingleNetwork s = new SingleNetwork();
                        s.setDescription(elements[1]);
                        s.setDeviceId(Str.i().getStringToInt(elements[2]));
                        s.setMacAddress(elements[3]);
                        s.setManufacturer(elements[4]);
                        s.setName(elements[5]);
                        s.setNetConnectionId(elements[6]);
                        s.setProductName(elements[7]);
                        s.setServiceName(elements[8]);
                        lista.add(s);
                    }
                }
            }
        } else {
            throw new UnsupportedOperationException("Not implemented yet for this OS");
        }
        return lista;
    }

    private String initLocalIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (Exception e) {
            System.err.println("Error to get network information" + e);
        }
        return "";
    }

    private String initPublicIp() {
        BufferedReader in = null;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            String ip = in.readLine();
            return ip;
        } catch (IOException e) {
            System.err.println("Error in getting public ip: " + e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                System.err.println("not closeable stream: " + e);
            }
        }
        return "";
    }
}
