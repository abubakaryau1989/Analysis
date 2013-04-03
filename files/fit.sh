#!/bin/bash

gnuplot << TOEND
# Set the output file

set terminal postscript epslatex color enhanced
set output 'linear.eps' 
unset key

set border 3
set xtics nomirror
set ytics nomirror

set format x "%2.0f" 
set format y "%2.0f" 

u(x) = a*x + b
fit u(x) 'linear.txt' using 1:2 via a,b

set xzeroaxis linestyle 2 lt 2 lc 9
set xlabel "Time" offset -0.3 -4.0

set ylabel "Voltage mV"  

plot 'linear.txt' using 1:2:4:5 with xyerrorbars
TOEND
convert -density 100 linear.eps linear.png
	




