package Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.*;

public class Core {
        Scanner sc=new Scanner(System.in);
        int registers[];
        int pc,pc2,pc1,pc3,pc4;
        int Off,RE;
        int cycles =1;
        String program[];
        int store;
        HashMap<String, Integer> MemoryMap = new HashMap<>();
        int countstalls=0;
        int Memory[];
        boolean b1,b2,b3,b4;
        String opcode;
        int rd,rd1,rd2,rd3,rd4;
        int count1=0;
        int mem;
        int result;
        int end;
        String Label;
        boolean branch_taken;
        boolean branch_to_be_taken_next_clock;
        boolean writeBack_of_last_instruction;
        boolean eof;
        boolean dataforward;
        Core() {
            this.registers = new int[32];
            this.pc=0;
            this.store=0;
            this.program=new String[0]; 
            b1=b2=b3=b4=false;
            branch_taken=false;
            eof=false;
        }
            int stallCounter = 0;
            int jstallcounter=0;
            void pipeline() {
                System.out.println();
                System.out.println("please read the below line before executing ");
                System.out.println("only pipeline is excuting and the output of registers is not updating in the case of 'data forwarding' in the case of basic instructions like add,sub,...");
                System.out.println("but in the case of 'without data forwarding' pipeline, output of registers and memory are giving correct for all the instructions");
                System.out.println();
                System.out.println("enter 1 for data forwarding and 0 for without forwarding : ");
                int enabledataforwarding=sc.nextInt();
                if(enabledataforwarding==1){
                    dataforward=true;
                }
                while (true){
                    System.out.println(" ");
                    writeBack(Memory, MemoryMap);
                    if (end == program.length) {
                        System.out.println("Total number of cycles taken: " + cycles);
                        System.out.println("Total number of stalls :"+ countstalls);
                        System.out.println("The value of IPC :"+(float)count1/cycles);
                        break;
                    }
                    memory(Memory, MemoryMap);
                        execute();
                    if (stallCounter == 0) {
                        decode();
                    } 
                    else {
                        System.out.println("stall");
                        countstalls++;
                        stallCounter--;
                        cycles++;
                        continue;
                    }
                    fetch();
                   cycles++;
                }
                }
            
         void fetch() {
            
            if (pc<program.length) {
                System.out.println("Came to fetch "+cycles);
                if (!branch_to_be_taken_next_clock&&branch_taken){
                    branch_taken = false;
                }
                if (branch_to_be_taken_next_clock){
                    branch_to_be_taken_next_clock = false;
                    branch_taken =true;
                    
                }
                b1 = true;
                pc1=pc;
                if(pc1<program.length){
                String parts[] = program[pc1].trim().split("[,\\s]+");
                System.out.println();
                if(parts.length==1){
                    pc++;
                    return;
                }
                if(parts[0].equals("j")){
                    jstallcounter++;
                    return;
                }
                else{
                int k1,k2;
                k1=pc1-1;
                k2=pc1-2;
                ArrayList <String> parts1 = new ArrayList<>();
                ArrayList <String> parts2 = new ArrayList<>();
                if(k1>=0){
                    String[] partsArray = program[k1].trim().split("[,\\s]+");
                    parts1.addAll(Arrays.asList(partsArray));
                }
                if(k2>=0){
                    String[] partsArray = program[k2].trim().split("[,\\s]+");
                    parts2.addAll(Arrays.asList(partsArray));
                }
                
                int x=-1,y=-1,rs1=-1,rs2=-1;
                if(parts1.size()>1){
                x=parseIntIfInteger(parts1.get(1).substring(1));
                }
                
                if(parts2.size()>1){
                y=parseIntIfInteger(parts2.get(1).substring(1));
                }
                
                if(parts.length==4){
                if(parts[0].equals("addi")){
                    rs1=Integer.parseInt(parts[2].substring(1));
                }
                else if(parts[0].equals("bne") || parts[0].equals("beq") || parts[0].equals("blt") || parts[0].equals("bgt")){
                    rs1 = Integer.parseInt(parts[1].substring(1));
                    rs2 = Integer.parseInt(parts[2].substring(1));
                }
                else{
                rs1=Integer.parseInt(parts[2].substring(1));
                rs2=Integer.parseInt(parts[3].substring(1));
                }
                }
               
                else if(parts.length==3){
                    if(parts[0].equals("sw") ){
                        rs2 = Integer.parseInt(parts[2].substring(parts[2].indexOf('(') + 2, parts[2].indexOf(')')));
                    }
                    else if(parts[0].equals("lw")){
                        rs2 = Integer.parseInt(parts[2].substring(parts[2].indexOf('(') + 2, parts[2].indexOf(')')));
                    }
                    else if(parts[0].equals("mv")){
                        rs2 = Integer.parseInt(parts[2].substring(1));
                    }
                }
                if(x!=-1 && (x==rs1 || x==rs2)){
                    if(dataforward==true){
                        stallCounter+=1;
                    }
                    else{
                    stallCounter+=3;
                    }
                }
               
                if(y!=-1 && x==-1 && (y==rs1 || y==rs2)){
                    if(dataforward==true){
                        stallCounter+=0;
                    }
                    else{
                    stallCounter+=2;
                    }
                }
                writeBack_of_last_instruction = false;
                }
            }
        }
            pc++;
        }
         void decode() {
            if (b1 &&!branch_taken) {
                System.out.println("came to decode "+cycles);
                if(pc1<program.length){
                String parts[] = program[pc1].trim().split("[,\\s]+");
                if(parts.length==1){
                    b1 = false;
                    b2 = true;
                    return;
                }
                if (parts.length >= 2) {
                    opcode = parts[0].toLowerCase();
                    if(parts.length >= 3) rd= Integer.parseInt(parts[1].substring(1));
                     if (opcode.equals("j")) {
                        jstallcounter++;
                        Label = parts[1];
                    }
                   
                    b1 = false;
                    b2 = true;
                } 
                else if(parts.length==4 && (parts[0].equals("bne")||parts[0].equals("beq"))){
                    Label = parts[3];
                }
                else {
                    System.err.println("Error: Insufficient elements in instruction");
                    b1 = true;
                    b2 = false;
                }
                pc2=pc1;
                writeBack_of_last_instruction = false;
            }
            }
            else{
                b2 = false;
            }
        }
        public void execute() {
            if(b2 && !branch_taken){
                if (pc2 >= program.length) {
                    return;
                }
                System.out.println("came to execute "+cycles);
                String parts[] = program[pc2].trim().split("[,\\s]+");
                String opcode = parts[0].toLowerCase();
                
                if (opcode.equals("lw") || opcode.equals("sw")) {
                    if (parts.length >= 4) {
                        String rs2String = parts[2].substring(parts[2].indexOf('(') + 2, parts[2].indexOf(')'));
                        int rs2 = Integer.parseInt(rs2String);
                        if (rs2 < registers.length && registers[rs2] == rd) {
                            b3 = false;
                            b4 = false;
                            return;
                        }
                    }
                }
    
                if (opcode.equals("add")) {
                    int rs1 = Integer.parseInt(parts[2].substring(1));
                    int rs2 = Integer.parseInt(parts[3].substring(1));
                    // if(dataforward==true){
                    //     rd2 = Integer.parseInt(parts[1].substring(1));
                    //     registers[rd2]=registers[rs1] + registers[rs2];
                         
                    // }(tried data forwarding)
                    this.result = registers[rs1] + registers[rs2];
                }
                else if (opcode.equals("sub")) {
                    //int dr =Integer.parseInt(parts[1].substring(1));
                    int rs1 = Integer.parseInt(parts[2].substring(1));
                    int rs2 = Integer.parseInt(parts[3].substring(1));
                    // if(dataforward==true){
                    //     registers[dr]=registers[rs1] - registers[rs2];
                    // }(tried data forwarding)
                    this.result = registers[rs1] - registers[rs2];
                }
                else if(opcode.equals("beq")){
                    int rs1 = Integer.parseInt(parts[1].substring(1));
                    int rs2 = Integer.parseInt(parts[2].substring(1));
                    String str = parts[3];
                    if (registers[rs1] == registers[rs2]) {
                        for (int i = 0; i < program.length-1; i++) {
                            if (program[i].startsWith(str+":")) {
                                branch_to_be_taken_next_clock = true;
                                countstalls+=2;
                                pc = i;
                                end =pc;
                                this.result = registers[rs1];
                                break;
                            }
                        }
                        
                    }
                }
                else if (opcode.equals("bne")) {
                    int rs1 = Integer.parseInt(parts[1].substring(1));
                    int rs2 = Integer.parseInt(parts[2].substring(1));
                    String str = parts[3];
                    if (registers[rs1] != registers[rs2]) {
                        System.out.println("came to here");
                        for (int i = 0; i < program.length; i++) {
                            if (i < program.length - 1 && program[i].startsWith(str)) {
                                branch_to_be_taken_next_clock = true;
                                countstalls+=2;
                                pc = i;
                                end =pc;
                                this.result = registers[rs1];
                                break;
                            }
                        }
                    }
                }
                else if(opcode.equals("blt")){
                    int rs1 = Integer.parseInt(parts[1].substring(1));
                    int rs2 = Integer.parseInt(parts[2].substring(1));
                    String str = parts[3];
                    if (registers[rs1] < registers[rs2]) {
                        int i;
                        for (i = 0; i<program.length; i++) {
                            if (i < program.length - 1 && program[i].startsWith(str)) {
                                branch_to_be_taken_next_clock = true;
                                countstalls+=2;
                                pc = i;
                                end =pc;
                                this.result = registers[rs1];
                                break;
                            }
                        }
                    }
                }
                else if(opcode.equals("bgt")){
                    int rs1 = Integer.parseInt(parts[1].substring(1));
                    int rs2 = Integer.parseInt(parts[2].substring(1));
                    String str = parts[3];
                    if (registers[rs1] >= registers[rs2]) {
                        int i;
                        for (i = 0; i<program.length; i++) {
                            if (i < program.length - 1 && program[i].startsWith(str)) {
                                branch_to_be_taken_next_clock = true;
                                countstalls+=2;
                                pc = i;
                                end =pc;
                                this.result = registers[rs1];
                                break;
                            }
                        }
                    }
                }
                else if(opcode.equals("mv")){
                    int rs2 = Integer.parseInt(parts[2].substring(1));
                    this.result = registers[rs2];
                }
                else if (opcode.equals("jal")) {
                    String label = parts[2];
                    for (int i = 0; i < program.length; i++) {
                        if (program[i].startsWith(label+":")) {
                            store = pc + 1; 
                            registers[rd] = store;
                            branch_to_be_taken_next_clock = true;
                            pc = i;
                            this.result = registers[rd];
                            countstalls+=2;
                            end =pc-1;
                            break;
                        }
                       
                    }
                } 
                else if (opcode.equals("ret")) {
                    pc = store;
                }
                
                else if(opcode.equals("addi")){
                    int rs1 = Integer.parseInt(parts[2].substring(1));
                    int r=Integer.parseInt(parts[3]);
                    this.result = registers[rs1] + r;
                }
                
                else if (opcode.equals("lw")) {
                    if (parts[2].charAt(1)=='(') {
                        int offset = Integer.parseInt(parts[2].substring(0, parts[2].indexOf('(')));
                        int rs1 = Integer.parseInt(parts[2].substring(parts[2].indexOf('(') + 2, parts[2].indexOf(')')));
                        this.result = Memory[registers[rs1] + offset];
                    }
                } 
                else if (opcode.equals("sw")) {
                    int rd = Integer.parseInt(parts[1].substring(1));
                    if (parts[2].charAt(1)=='(') {
                        Off = Integer.parseInt(parts[2].substring(0, parts[2].indexOf('(')));
                        RE = Integer.parseInt(parts[2].substring(parts[2].indexOf('(') + 2, parts[2].indexOf(')'))); 
                        this.result = registers[rd];
                    }
                }
    
                else if(opcode.equals("la")){
                        int x = readMemory(parts[2],MemoryMap);
                        this.result = x;
                }
                
                else if(opcode.equals("li")){
                    int r=Integer.parseInt(parts[2]);
                    this.result = r;
                }
                
                else if (opcode.equals("j")) {
                    for (int i = 0; i < program.length; i++) {
                        if (program[i].startsWith(Label+":")) {
                            branch_to_be_taken_next_clock = true;
                                pc = i;
                                end=pc-1;
                                countstalls+=2;
                                break;
                        }
                    }
                }
            b3=true;
            b2 = false;
            pc3=pc2;
            rd1=rd;
            writeBack_of_last_instruction = false;
        }
        else{
            b3 = false;
        }
    }
    void memory(int memory[],HashMap<String, Integer> map) {
        if (b3 ) {
            System.out.println("Came to memory "+cycles);
            String parts[] = program[pc3].trim().split("[,\\s]+");
            opcode = parts[0].toLowerCase();
            if (opcode.equals("lw")) {
                if (parts[2].charAt(1)=='(') {
                    int offset = Integer.parseInt(parts[2].substring(0, parts[2].indexOf('(')));
                    int rs1 = Integer.parseInt(parts[2].substring(parts[2].indexOf('(') + 2, parts[2].indexOf(')')));
                    this.mem = memory[registers[rs1] + offset];
                    rd2=rd1;
                }
            } 
            else if (opcode.equals("sw")) {
                if (parts[2].charAt(1)=='(') {
                    memory[registers[RE] + Off]=this.result;
                }
            }
            else if(opcode.equals("la")){
                mem= readMemory(parts[2], map);
                rd2=rd1;
            }
            else{
                this.mem=this.result;
                rd2=rd1;
            }
            b3 = false;
            b4 = true;
          writeBack_of_last_instruction = false;
        }
    }
         void writeBack(int memory[],HashMap<String, Integer> map) {
            if (b4) {
                System.out.println("Came to writeback "+cycles);
                count1++;
                if(opcode.equals("sw")){
                    memory[0] =0;
                } 
                else{
                     registers[rd2]=this.mem;
                }
                end++;
                if(end==program.length){
                    eof =true;
                }
                b4=false;
                writeBack_of_last_instruction = true;
            }
        }
    
        public int readMemory(String data, HashMap<String, Integer> map) {
            return map.get(data);
        }
        public int parseIntIfInteger(String str) {
            String subString = str;
            try {
                int parsedValue = Integer.parseInt(subString);
                return parsedValue;
            }
             catch (NumberFormatException e) {
                return -1;
            }
        }
}