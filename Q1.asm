.data
base2: .word 320
array: .word 90
.text
add x1,x2,x3
la x4,base2
la x13,base2
sw x3,8(x13)
sw x1,4(x4)
li x5,2
li x7,3
addi x8,x7,1
li x19,3
lw x12,0(x4)

sw x5,8(x13)
li x9,5
li x10,6

li x11,7
li x31,100

#li x15,81
#sub x4,x5,x16
#li x5,10
#mv x6,x3