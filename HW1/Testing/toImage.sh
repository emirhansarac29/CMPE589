#!/bin/bash  
for f in `ls *.dot | cut -d. -f1`; 
do 
	dot -Tpdf $f.dot -o $f.pdf; 
done