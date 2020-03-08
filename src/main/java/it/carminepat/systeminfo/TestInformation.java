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
    public static void main(String[] args) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String jsonOs = mapper.writeValueAsString(Os.i());
        System.out.println(jsonOs);
        String jsonCpu=mapper.writeValueAsString(Cpu.i());
        System.out.println(jsonCpu);
        String jsonMemory=mapper.writeValueAsString(Memory.i());
        System.out.println(jsonMemory);
    }
    
}
