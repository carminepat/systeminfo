package it.carminepat.systeminfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author carmi
 */
@Getter
@Setter
public class SingleProcess implements Comparable<SingleProcess> {

    private String name;
    private int pid;
    private String nameSession;
    private int sessionNumber;
    private String momory;
    @JsonIgnore
    private long memoryL;

    @Override
    public int compareTo(SingleProcess o) {
        if (o.getMemoryL() == this.memoryL) {
            return 0;
        } else if (o.getMemoryL() > this.memoryL) {
            return 1;
        }
        return -1;
    }

}
