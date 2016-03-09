#! /bin/bash 
cat edgespeedmap.csv | java GetStatisticalInfo > statisticalinfo.csv
echo "compressing raw data"
tar --remove-files -jcvf edgespeedmap.tar.bz2 edgespeedmap.csv
