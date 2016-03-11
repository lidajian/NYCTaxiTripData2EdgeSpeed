# NYCTaxiTripData2EdgeSpeed
Process the taxi trip data from NYC government to get the edge speed in Manhattan.

## How to use it?

I use a machine with 64GB memory and 900GB hard drive.

Here are some steps for these codes:
1. Put csv files in individual folders named "yyMM" ("1501" for example)
2. run ./distribute.sh yy (.distribute.sh 15 for example)
3. run ./parallelrun.sh yy (this won't occupy too much memory so it can be done in parallel)
4. run source batch.sh yy (this will use about 40% of the memory for one year)
5. collect.sh and CombineStatisticalInfo.java will help you combine statistical data from folders. 

Still in progress now...
