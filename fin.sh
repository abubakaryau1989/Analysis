#!/bin/bash

gnuplot << TOEND

# Set the output file type
set terminal png 
set output 'z.png' 
unset key

set border 3
set xtics nomirror
set ytics nomirror

set format x "%2.0f" 
set format y "%2.0f" 

u(x)=m*x+b
fit u(x) "linear.txt" using 1:2 via m,b

set xzeroaxis linestyle 2 lt 2 lc 9
set xlabel "Applied Current mA" offset -0.3 -4.0
set ylabel "Voltage mV"  
set xrange [ -15.00 : 15.00 ]
set yrange [ -3.0 : 3.0 ]

plot  u(x), 'z.png' using 1:2:4:5 with xyerrorbars
TOEND
