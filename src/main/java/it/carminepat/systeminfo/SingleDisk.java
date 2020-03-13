package it.carminepat.systeminfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * information about single disk
 *
 * @author carmi
 */
@Getter
@Setter
public class SingleDisk {

    private String name;
    private String volumeName;
    private String serial;
    private String description;
    private String fileSystem;
    @JsonIgnore
    private long freeSpaceL;
    private String freeSpace;
    @JsonIgnore
    private long sizeL;
    private String size;

}
