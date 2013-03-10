set terminal png
set output 'glygerin.png
plot "glycerinGnu.txt" using 1:2 title "Glycerin" with lines
set xlabel "Field"
set xlabel "Frequency"
f(x) = m*x + b
set style data lines
fit f(x) "glycerinGnu.txt" using 1:2 via m,b 
title_f(m,b) = sprintf("f(x) = %.2fx + %.2f", m, b)
plot "glycerinGnu.txt" u 1:2 w l, f(x) t title_f(m,b) 

