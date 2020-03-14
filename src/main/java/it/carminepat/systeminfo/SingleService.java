package it.carminepat.systeminfo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author carmi
 */
@Getter
@Setter
public class SingleService {

    private boolean acceptStop;
    private String caption;
    private String displayName;
    private String name;
    private String pathName;
    private int PID;
    private boolean started;
    private String startMode;
    private String status;
    private String state;
}
