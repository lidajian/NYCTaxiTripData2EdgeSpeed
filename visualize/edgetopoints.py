import xml.etree.ElementTree as et
import numpy as np
from pyproj import Proj
import csv

edges = {}
with open('selectededge.csv','rb') as csvfile:
    csvreader = csv.reader(csvfile)
    for line in csvreader:
        edges[line[0]] = 1


e = et.parse('Manhattan_only_sumo.net.xml').getroot()

# read the network
for edge in e.findall('edge'):
    if edge.get('function') != 'internal':
        edge_id = edge.get('id')
        if edges.has_key(edge_id):
            for lane in edge.findall('lane'):
                for point in lane.get('shape').split(' '):
                    xydata = point.split(',')
                    print "%s\t%s" %(xydata[0], xydata[1])


