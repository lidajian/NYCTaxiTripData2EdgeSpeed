#! /bin/sh
sed '1d' yellow*.csv | tr ' ' '@' | awk -F ',' '{if($6 && $7 && $10 && $11)print $2,$3,$6,$7,$10,$11}' > output
echo "Got output"
python projection.py > projectedpoints.csv
echo "Projected"
wc -l projectedpoints.csv
#read -n1 -r -p "Press any key to continue..."
echo "recompiling"
l=`wc -l output|awk '{print $1}'`
sed -i 's/TOBESUB/'$l'/g' csv2trip.cpp
g++ csv2trip.cpp -o csv2trip
./csv2trip > trip.xml
echo "Got trip"
duarouter -n Manhattan_only_sumo.net.xml -t trip.xml -o route.xml --ignore-errors
rm route.alt.xml
rm projectedpoints.csv
rm trip.xml
echo "Got route"
