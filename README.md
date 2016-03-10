# NYCTaxiTripData2EdgeSpeed
Process the taxi trip data from NYC government to get the edge speed in Manhattan.

I use a machine with 64GB memory and 900GB hard drive.
Here are some steps for these codes:
1 Put csv files in individual folders named "yyMM" ("1501" for example)
2 run ./distribute.sh yy (.distribute.sh 15 for example)
3 go into the folders, run ./run.sh (this can be done in parallel)
4 run source batch.sh yy (this will use about 40% of the memory)
5 go into the folders, run ./runanalysis.sh(running these in parallel for a year uses almost all the memory)

Still in progress now...
