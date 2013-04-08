Analysis
========

This is a repo for a package I am designing to help with analysis of experimental data.

At the moment it can do linear analysis but there is scope for nonlinear analysis later on. 

The input files for LinearData.java must be in the form 

length = int
error x = double
error y = double
X1 Y1
X2 Y2
.  .
.  .
.  .
Xn Yn

in order to be read.

ExtractColumn.java and AverageData.java only need the format:


length = int
X1 Y1
X2 Y2
.  .
.  .
.  .
Xn Yn
