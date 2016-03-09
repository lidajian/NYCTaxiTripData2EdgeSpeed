import xml.etree.ElementTree as et
import numpy as np

e = et.parse('Manhattan_only_sumo.net.xml').getroot()

fed = open('edgelength.csv','wb')
# read the network
for edge in e.findall('edge'):
    if edge.get('function') != 'internal':
        edge_id = edge.get('id')
        length_list = []
        for lane in edge.findall('lane'):
            length_list.append(float(lane.get('length')))
        fed.write('%s\t%f\n' %(edge_id, np.mean(length_list)))
fed.close()
