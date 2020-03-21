package it.carminepat.systeminfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author carminepat@gmail.com
 */
@Getter
@Setter
public class SingleProgram {

    private String description;
    private String IdentifyingNumber;
    @JsonIgnore
    private Date installDate;
    private String installationDate;
    private String installLocation;
    private String installState;
    private String name;
    private String packageCache;
    private String vendor;
    private String version;

    public void setInstallDate(String date) {
        if (date != null && !"".equals(date)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat();
                sdf.applyPattern("yyyyMMdd");
                this.installDate = sdf.parse(date);
                sdf.applyPattern("dd/MM/yyyy");
                this.installationDate = sdf.format(this.installDate);
            } catch (Exception e) {
                System.err.println("not date: " + date + " - " + e);
            }
        }
    }
}
