beq x3 x4 loop
loop:
add x2 x3 x5
j exit
add x5 x2 x3
exit:
li x7,7