package Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        
        Parser p=new Parser();
        List<ArrayList<String>> instructions1 = p.parseAssembly("src\\Test\\Q1.asm");
        List<ArrayList<String>> instructions2 = p.parseAssembly("src\\Test\\Q2.asm");
        ArrayList<String> textinstructions1 = instructions1.get(0);  
        ArrayList<String> textinstructions2 = instructions2.get(0);
        ArrayList<String> datainstructions1 = instructions1.get(1);  
        ArrayList<String> datainstructions2 = instructions2.get(1);
        
        List<ArrayList<String>> dataInstructions = new ArrayList<>();
         dataInstructions.add(datainstructions1);
         dataInstructions.add(datainstructions2);
         
        Processor sim = new Processor();
        sim.analyzedata(dataInstructions);
        sim.cores[0].registers[3]=5;
        sim.cores[0].registers[4]=5;
        sim.cores[1].registers[3]=5;
        System.out.println("+============ Before Run ============:");
        for (int i = 0; i < 2; i++) {
            System.out.println(Arrays.toString(sim.cores[i].registers));
        }

        for (int i = 0; i < 2; i++) {  // for giving gap between the two runs.
            System.out.println();
        }
        System.out.println("Memory Before :");
        sim.printdata();
        System.out.println();
        System.out.println();
        System.out.println("+============ After Run ============:");
        sim.cores[0].program = textinstructions1.toArray(new String[0]);
        sim.cores[1].program = textinstructions2.toArray(new String[0]);
        sim.run();

        for (int i = 0; i < 2; i++) {
            System.out.println(Arrays.toString(sim.cores[i].registers));
        }
        System.out.println();
        System.out.println("Memory After :");
        sim.printdata();

    }
}
