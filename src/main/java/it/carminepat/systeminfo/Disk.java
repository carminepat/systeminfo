package it.carminepat.systeminfo;

import it.carminepat.systeminfo.utils.CommandLine;
import it.carminepat.systeminfo.utils.Converter;
import it.carminepat.systeminfo.utils.Str;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * information about disk
 *
 * @author carminepat@gmail.com
 */
@Getter
public class Disk {

    private List<SingleDisk> disks;

    private static Disk instance = null;

    public static Disk i() {
        if (instance == null) {
            instance = new Disk();
        }
        return instance;
    }

    private Disk() {
        this.disks = this.initDiskWindows();
    }

    private ArrayList initDiskWindows() {
        ArrayList<SingleDisk> l = new ArrayList<>();
            String[] elements = {"Size", "Name", "VolumeName", "VolumeSerialNumber", "Description", "FileSystem", "FreeSpace"};
            for (String element : elements) {
                String result = CommandLine.i().clearResultWindowsWithLine(CommandLine.i().getResultOfExecution("wmic logicaldisk get " + element), element);
                List<String> split = CommandLine.i().getStringInLines(result);
                for (int i = 0; i < split.size(); i++) {
                    SingleDisk s = null;
                    boolean isNew = false;
                    try {
                        s = l.get(i);
                    } catch (Exception e) {
                        //silent index out of bound
                    }
                    if (s == null) {
                        s = new SingleDisk();
                        isNew = true;
                    }
                    switch (element) {
                        case "Size":
                            s.setSizeL(Str.i().getStringToLong(split.get(i).trim()));
                            s.setSize(Converter.i().readableFileSize(s.getSizeL()));
                            break;
                        case "Name":
                            s.setName(split.get(i).trim());
                            break;
                        case "VolumeName":
                            s.setVolumeName(split.get(i).trim());
                            break;
                        case "VolumeSerialNumber":
                            s.setSerial(split.get(i).trim());
                            break;
                        case "Description":
                            s.setDescription(split.get(i).trim());
                            break;
                        case "FileSystem":
                            s.setFileSystem(split.get(i).trim());
                            break;
                        case "FreeSpace":
                            s.setFreeSpaceL(Str.i().getStringToLong(split.get(i).trim()));
                            s.setFreeSpace(Converter.i().readableFileSize(s.getFreeSpaceL()));
                            break;
                    }
                    if (isNew) {
                        l.add(i, s);
                    } else {
                        l.set(i, s);
                    }
                }
            }
        return l;
    }

}
