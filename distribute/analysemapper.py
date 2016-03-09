import xml.etree.ElementTree as et
import csv
from datetime import datetime as dt
from datetime import timedelta

e = et.parse('route.xml').getroot()

edge_distance_map = {}
# make the edge-distance map
with open('edgelength.csv','rb') as csvfile:
    csvreader = csv.reader(csvfile, delimiter='\t')
    for line in csvreader:
        edge_distance_map[line[0]] = float(line[1])


max_id = 0
route_edge_map = {}
route_distance_map = {}
# make the vehicle-route map
for vehicle in e.findall('vehicle'):
    max_id = int(vehicle.get('id'))
    total_length = 0.0
    for edge in vehicle.find('route').get('edges').split(' '):
        total_length = total_length + edge_distance_map[edge]
    route_distance_map[max_id] = total_length
    route_edge_map[max_id] = vehicle.find('route').get('edges')

i=1
# read the csv
with open('output','rb') as csvfile:
    csvreader = csv.reader(csvfile, delimiter=' ')
    for line in csvreader:
        #print 'at',i
        i = i+1
        if i > max_id:
            break
        if route_distance_map.has_key(i):
            pickup_datetime = dt.strptime(line[0],'%Y-%m-%d@%H:%M:%S')
            dropoff_datetime = dt.strptime(line[1],'%Y-%m-%d@%H:%M:%S')
            td = dropoff_datetime - pickup_datetime
            #print td.total_seconds(),route_distance_map[i]
            td_s = td.total_seconds()
            if td_s > 1.0:
                speed_mps = route_distance_map[i] / td.total_seconds()
                curr_datetime = pickup_datetime
                for edge in route_edge_map[i].split(' '):
                    timeinc = timedelta(seconds = int(edge_distance_map[edge]/speed_mps))
                    curr_datetime = curr_datetime + timeinc
                    print '%s\t%s\t%f' %(edge, curr_datetime.isoformat(' '),speed_mps)
