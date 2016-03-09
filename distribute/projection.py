import xml.etree.ElementTree as et
import csv
from pyproj import Proj

e = et.parse('Manhattan_only_sumo.net.xml').getroot()
loc_info = e.find('location')
offset_str = loc_info.get('netOffset').split(',')
offset = (float(offset_str[0]), float(offset_str[1]))

projection = Proj(loc_info.get('projParameter'))

# read the csv
with open('output','rb') as csvfile:
    csvreader = csv.reader(csvfile, delimiter=' ')
    for line in csvreader:
        xydata = projection(float(line[2]), float(line[3]))
        xydata2 = projection(float(line[4]), float(line[5]))
        xydata_offseted =(xydata[0]+offset[0], xydata[1]+offset[1])
        xydata_offseted2 =(xydata2[0]+offset[0], xydata2[1]+offset[1])
        print "%f\t%f\t%f\t%f" %(xydata_offseted[0], xydata_offseted[1], xydata_offseted2[0], xydata_offseted2[1])
