#include <fstream>
#include <iostream>
#include <stdio.h>
#include <string>

#define NUMLINE 94549
#define NUMCAB TOBESUB

using namespace std;


int main(){
    ifstream ifs("pointedgemap.csv");
    float * pointx = new float[NUMLINE];
    float * pointy = new float[NUMLINE];
    string * edges = new string[NUMLINE];
    float x1, y1, x2, y2;
    int minind1, minind2, i, j;
    float minv1, minv2, tempv1, tempv2;
    string pickupedge, dropoffedge;
    for(i = 0; i < NUMLINE; i++){
        ifs>>pointx[i]>>pointy[i]>>edges[i];
    }
    ifs.close();
    ifs.open("projectedpoints.csv");
    printf("<trips>\n");
    for(i = 0; i < NUMCAB; i++){
        ifs>>x1>>y1>>x2>>y2;
        minind1 = 0, minind2 = 0;
        minv1 = (x1 - pointx[0]) * (x1 - pointx[0])+(y1 - pointy[0]) * (y1 - pointy[0]);
        minv2 = (x2 - pointx[0]) * (x2 - pointx[0])+(y2 - pointy[0]) * (y2 - pointy[0]);
        for(j = 1; j < NUMLINE; j++){
            tempv1 = (x1 - pointx[j]) * (x1 - pointx[j])+(y1 - pointy[j]) * (y1 - pointy[j]);
            tempv2 = (x2 - pointx[j]) * (x2 - pointx[j])+(y2 - pointy[j]) * (y2 - pointy[j]);
            if(tempv1 < minv1){
                minv1 = tempv1;
                minind1 = j;
            }
            if(tempv2 < minv2){
                minv2 = tempv2;
                minind2 = j;
            }
        }
        if(minv1 < 10000 && minv2 < 10000)
        printf("\t<trip id=\"%d\" depart=\"%d\" from=\"%s\" to=\"%s\"/>\n", i+2, i, edges[minind1].c_str(), edges[minind2].c_str());

    }
    printf("</trips>");
    delete [] pointx;
    delete [] pointy;
    delete [] edges;
    ifs.close();
}
