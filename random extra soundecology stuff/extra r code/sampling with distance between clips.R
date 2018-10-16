
totalNumberOfFiles =445
lengthSec =600
sampleLengthSec =30 
amountOfSample = 4
lengthOfSeperationBetweenIntervals = 60; 


if ((lengthSec/(2*lengthOfSeperationBetweenIntervals) <= amountOfSample))
  print("this wont work reduce sepeationbetweenIntervals or amount of samples")

for (recording in 1:totalNumberOfFiles){
intervals = rep(-lengthOfSeperationBetweenIntervals-1, amountOfSample)

for (i in 1:amountOfSample)
{
  pickNew =TRUE
  while (pickNew){
    startseconds <- sample(0:(lengthSec-sampleLengthSec), 1, replace=T)
    pickNew=FALSE
    for(j in 1:amountOfSample) 
    {
      if (lengthOfSeperationBetweenIntervals>= abs(intervals[j]-startseconds))
        pickNew= TRUE
    }
  }
  intervals[i] <- startseconds
}

#run the analysis for these intervals ...
print(intervals )
}





# for (j in 1:amountOfSample){
# while ((lengthOfSeperationBetweenIntervals>= abs(intervals[1]-startseconds)) ||(lengthOfSeperationBetweenIntervals>= abs(intervals[3]-startseconds))||(lengthOfSeperationBetweenIntervals>= abs(intervals[2]-startseconds)) ) #&&  (intervals[j]-startseconds <= lengthOfSeperationBetweenIntervals))
#      startseconds <- sample(1:(lengthSec-sampleLengthSec), 1, replace=T)

# }

