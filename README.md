#### Implemented pipeline 
* we have used a while loop in pipeline method to implement the pipelining which consists five functions corresponding five stages of a pipeline(IF,ID,EX,MEM,WB).
* We have used temporary registers and boolean variables between stages of the pipeline for the flow of the pipeline
* In IF program counter will be known and data hazards will be detected
* In ID registers are fetched and branch predictions are taken
* In EX corresponding executions are done based on the opcode and stored in a temporary register and propagated to next stage of pipeline through another temporary register
* In MEM operations related to lw,sw,la are executed
#### Implemented data hazards
* Read After Write hazard is taken care using stalls

#### Implemented branch predictor
* initialized a boolean variable to "not taken" and checked for original branch execution at EX stage

#### How to run?
* download the zip file 
* check the paths of files in Main.java
* Debug to get the output

#### Output
* updated registers of two cores
* Memory after executing programs in two cores
* Number of cycles
* Number of stalls
