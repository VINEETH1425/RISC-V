package Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Processor {
        int memory[];
        Core[] cores = new Core[2];
        HashMap<String, Integer> memoryMap = new HashMap<>();
        
        Processor(){
            memory = new int[4096]; 
            cores[0] = new Core();
            cores[1] = new Core();
            allocateMemory("base1",80);//Initialising where to store starting element of array elements of Q1
            allocateMemory("base2",320);//Initialising where to store starting element array elements of Q2
        }
        void run() {
            cores[0].MemoryMap = memoryMap;
            cores[1].MemoryMap = memoryMap;
            cores[0].Memory = memory;
            cores[1].Memory = memory;
            //boolean finished = false;
            for(int i=0;i<2;i++){
                cores[i].pipeline();
            }
        }
        
    public void allocateMemory(String data, int address) {
        memoryMap.put(data,address);
    }
       
    public void analyzedata(List<ArrayList<String>> datainstructions) {
        int it = memoryMap.get("base1");
        for (int k = 0; k < 2; k++) {
            ArrayList<String> list = datainstructions.get(k);
            for (int i = 1; i < list.size(); i++) {
                String[] str = list.get(i).trim().split("[,\\s]+");
                if(str[0].equals("base")){
                    continue;
                }
                for (int j = 2; j < str.length; j++) { 
                    memory[it] = Integer.parseInt(str[j]);
                    it=it+4;
                }
            }
            it = memoryMap.get("base2");//updating the iterator for 
        }
    }
    public void printdata(){
        for(int i=0;i<480;i=i+4){
            if(i%80==0 && i!=0){
                System.out.println();
            }
            System.out.print(memory[i]+" ");           
        }
    }
}