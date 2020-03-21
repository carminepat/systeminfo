package it.carminepat.systeminfo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author carminepat@gmail.com
 */
@Getter
@Setter
public class SingleNetwork {

    private String description;
    private int deviceId;
    private String macAddress;
    private String manufacturer;
    private String name;
    private String netConnectionId;
    private String productName;
    private String serviceName;

}
