#parameters 
soundindex<- "ndsi"
no_cores<- 1

res<-"/Users/jo437452/Desktop/hurricaneNDSIresults.csv"
filesToAnalyze <- "/Users/jo437452/Desktop/hurricaneNDSI.csv"

lengthSec <-600 # 10 minutes
sampleLengthSec <-30 # 30 seconds analysis lengths 
amountOfSample <-20 # there are 20, 30 seconds in 10 minutes 


#Start of code that is not parameters

#reading in the file paths from a csv 
fileTable <- read.csv(filesToAnalyze, header=F, stringsAsFactors=FALSE)

fileNames <- fileTable$V1
totalNumberOfFiles <- length(fileNames)
directoryArr<- rep("default String", totalNumberOfFiles*amountOfSample)
fromArr<- rep(-lengthSec, totalNumberOfFiles*amountOfSample)
wave_FilesArr<- rep("default String", totalNumberOfFiles*amountOfSample)

count<-0
for (num in 1:length(fileNames))
{
    for (i in 1:amountOfSample)
    {
      count <- count +1
      directoryArr[count] <- fileNames[num]
      fromArr[count] <-  30 * ((count-1 )%% amountOfSample)
      
    }
}
#create toArr based off of fromArray 
toArr<- fromArr + rep(sampleLengthSec, length(fromArr))

#gets wav file names based off of path from directoryArr
for (i in 1:length(directoryArr)){
  pos <- unlist(gregexpr(pattern ='/',directoryArr[i]))
  wave_FilesArr[i] <- substr(as.character(directoryArr[i]), pos[length(pos)]+1, nchar(directoryArr[i]))
}


library(soundecology)

multiple_sounds_EditforLS(directoryArr,wave_FilesArr ,resultfile = res, soundindex = soundindex ,no_cores =no_cores, fromArr, toArr, units = "seconds")






