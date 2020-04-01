package it.carminepat.systeminfo;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author carminepat
 */
public class TestInformation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonOs = mapper.writeValueAsString(Os.i());
        System.out.println(jsonOs);
//        String jsonCpu=mapper.writeValueAsString(Cpu.i());
//        System.out.println(jsonCpu);
//        String jsonMemory=mapper.writeValueAsString(Memory.i());
//        System.out.println(jsonMemory);
//        String jsonDisk=mapper.writeValueAsString(Disk.i());
//        System.out.println(jsonDisk);      
//        String jsonProcess = mapper.writeValueAsString(Process.i().getListOfFirstFiveProcess());
//        System.out.println(jsonProcess);
//        String jsonServices = mapper.writeValueAsString(Services.i().getListOfServices());
//        System.out.println(jsonServices);
        //Os.i().setEnvironmentVariable("UTILS_PUBLISYS", "C:\\progetti\\aziendali\\publisys");
//        System.out.println(mapper.writeValueAsString(Program.i().getPrograms()));
//        System.out.println(mapper.writeValueAsString(Net.i()));
    }

}
