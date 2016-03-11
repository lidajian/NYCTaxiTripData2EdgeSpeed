#! /bin/bash
arr=("01" "02" "03" "04" "05" "06" "07" "08" "09" "10" "11" "12")
for i in "${arr[@]}"
do
    cd $1$i
    ./run.sh &
    cd ..
done

