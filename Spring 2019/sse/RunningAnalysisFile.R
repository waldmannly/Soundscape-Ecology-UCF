source('soundecologyEditedFile.R')
#parameters 
soundindex<- "ndsi"
no_cores<- 2

res<-"C:/Users/evan/Desktop/testdata.csv"
logfile <- "C:/Users/evan/Desktop/logfile.csv" # i think that this file will be appended to, so make sure it is empty.
filesToAnalyze <- "C:/Users/evan/Desktop/filenames.txt"

lengthSec <-3480 # seconds in 60 minutes but not all samples are exactly 60 minutes
sampleLengthSec <-30 # 30 seconds analysis lengths 
amountOfSample <-116 # there are 120, 30 seconds in 60 minutes 


#Start of code that is not parameters

#reading in the file paths from a csv 
fileTable <- read.csv(filesToAnalyze, header=F, stringsAsFactors=FALSE)

fileNames <- fileTable$V1
totalNumberOfFiles <- length(fileNames)
directoryArr <- rep("default String", totalNumberOfFiles*amountOfSample)
fromArr <- rep(-lengthSec, totalNumberOfFiles*amountOfSample)
wave_FilesArr <- rep("default String", totalNumberOfFiles*amountOfSample)

count<-0
for (num in 1:length(fileNames))
{
    for (i in 1:amountOfSample)
    {
      count <- count +1
      directoryArr[count] <- fileNames[num]
      fromArr[count] <-  30 * ((count-1)%% amountOfSample)
      
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

multiple_sounds_EditforLS(directoryArr = directoryArr, wave_FilesArr = wave_FilesArr, resultfile = res, soundindex = soundindex ,no_cores = no_cores, from = fromArr, to = toArr, units = "seconds", anthro_min = 1000, anthro_max = 2000, bio_min = 2000, bio_max = 11000, logfile = logfile)




#you should be able to recover the partital data from the logfile with the following 
source("logfileUtils.R")
logData<- convertLogFile(logfile)
newData <- data.frame(do.call('rbind', strsplit(as.character(logData),',',fixed=TRUE)))
colnames(newData)<- as.character(unlist(newData[1,]))
newData <- newData[-1 ,]
head(newData)
write.table(newData , "dataFile.csv", row.names = F, quote=F)
