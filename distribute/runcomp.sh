#! /bin/bash
python analysemapper.py |java GetStatisticalInfo > statisticalinfo.csv
tar --remove-files -jcvf route.tar.bz2 route.xml
rm output
