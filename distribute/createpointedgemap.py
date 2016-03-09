import xml.etree.ElementTree as et

e = et.parse('Manhattan_only_sumo.net.xml').getroot()

# read the network
for edge in e.findall('edge'):
    edge_id = edge.get('id')
    if edge.get('function') != 'internal':
        for lane in edge.findall('lane'):
            points = lane.get('shape')
            for point in points.split(' '):
                xydata = point.split(',')
                print "%s\t%s\t%s" %(xydata[0], xydata[1], edge_id)
