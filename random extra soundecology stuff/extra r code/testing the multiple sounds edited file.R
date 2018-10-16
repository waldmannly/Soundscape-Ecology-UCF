#write code to make csv file automatically fixed for this function
#fix code for H index 
#make this a function (virtually done... needs some testing )
#write documentation 
#add in flac file reading (should just involve bring over stuff from the original files )

#summary of function: 
#this function allows you to provide alist of path files to your soundscape records and then calculates 
# a specified index using a modified function from Vil,,,, soundecology package. This function also includes
# the option of implementing a random sample through all of your soundfiles. files in the sampe row will have
# the same start and end times, making the comparisons across them more accurate/legitimate than strictly random sample. 


#parameters  
#fileDirectoy 
#lengthSec
#sampleLengthSec
#amountOfSample
#lenthOfSeperationBetweenIntervals 
#resultfile
#soundindex
#no_cores

#random_sample_multiple_file_soundscape_ecology<- function(fileDirectory, resultFile, soundindex, no_cores,lengthofSample, lengthOfSeperation, lengthOfFile, amountOfSamples){
#lengthSec<- lengthofSample
#sampleLengthSec<- lengthofSample
#amountOfSample<- amountOfSamples
#lengthOfSeperationBetweenIntervals<- lengthOfSeperation
#res <- resultFile
#filesToAnalyze<- fileDirectory
  
#delete these(below) once function works 
soundindex<- "acoustic_complexity"
#no_cores<- "max"
no_cores<- 6

for (run in 1:10)#10 runs of amount of samples
{

#parameters of input and output
res<-paste("C:/Users/evan/Desktop/dataACIrun4samplesTrialRUN",run,".csv",sep="") 
#filesToAnalyze <- "C:/Users/evan/Desktop/Soundscape ecology same recordings.csv"
filesToAnalyze <- "C:/Users/evan/Desktop/fileLists - Copy.csv"

#require the user to create the soundscape file paths before hand?


#reading in the file paths from a csv 
fileTable <- read.csv(filesToAnalyze, header=T)

#delete the first columns of ints from the data table
#fileTable[,(1)]<- NULL
#fileTable[,(1)]<- NULL


#requiring that each site has the same lengths of data, preferably that all the data matches too
#if (length(fileTable$Arb) != length(fileTable$farm) && length(fileTable$farm)!= length(fileTable$union))
#  stop("There are not the same amount of files in each list.")

#the comment below replaces the if above 
sites <-length(fileTable)
for (i in 1:(sites-1))
{
  if (length(fileTable[,i]) != length(fileTable[,i+1]))
    stop("There are not the same amount of files in each list (column) [site].")
}

#parameters for random sample and making directoryArr
#totalNumberOfFiles <-length(fileTable$Arb)*3
totalNumberOfFiles <- length(fileTable[,1])* sites # change this later

lengthSec <-600
sampleLengthSec <-30 
amountOfSample <- 4
lengthOfSeperationBetweenIntervals <- 0; 


#condition that should be met to make sure that samples can actually be picked
if ((lengthSec/(2*lengthOfSeperationBetweenIntervals) <= amountOfSample))
  stop("this wont work reduce sepeationbetweenIntervals or amount of samples")



directoryArr<- rep("default String", totalNumberOfFiles*amountOfSample)
fromArr<- rep(-lengthSec, totalNumberOfFiles*amountOfSample)
wave_FilesArr<- rep("default String", totalNumberOfFiles*amountOfSample)

for (num in 1:length(fileTable[,1])-1)#length(fileTable$Arb)-1)#change this later
{
  #random sampling 
  intervals <- rep(-lengthSec, amountOfSample)
    for (i in 1:amountOfSample)
    {
      pickNew =TRUE
      while (pickNew){
        startseconds <- sample(0:(lengthSec-sampleLengthSec), 1, replace=T)
        pickNew=FALSE
        #for(j in 1:amountOfSample) #makes non overlapping...
        #{
         # if (lengthOfSeperationBetweenIntervals>= abs(intervals[j]-startseconds))
          #  pickNew= TRUE
        #}
      }
      intervals[i] <- startseconds
    }
    #run the analysis for these intervals ...
   # print(intervals )

  
  
  for (sample in 1:amountOfSample-1)
  {
    #organizing file names based on input and amount of samples
    for (i in 1:sites)
    {
      directoryArr[i+sample*3+amountOfSample*num*3] <- as.character(fileTable[,i][num+1])
      fromArr[i+sample*3+amountOfSample*num*3] <- intervals[sample+1]
    }
    #directoryArr[2+sample*3+ amountOfSample*num*3] <- as.character(fileTable$farm[num+1])
    #fromArr[2+sample*3+ amountOfSample*num*3] <- intervals[sample+1]
 
    #directoryArr[3+sample*3+ amountOfSample*num*3] <- as.character(fileTable$union[num+1])
    #fromArr[3+sample*3+ amountOfSample*num*3] <- intervals[sample+1]
    
    #the comment below should replace the 6 statments above ... needs testing 
    #for (i in 1:sites) {
    #  directoryArr[i+sample*sites+ amountOfSample*num*sites] <-as.character(fileTable[i,num+1])
    # fromArr[i+sample*sites+ amountOfSample*num*sites] <- intervals[sample+1]
    # }
  }
}
#create toArr based off of fromArray 
toArr<- fromArr + rep(sampleLengthSec, length(fromArr))


#gets wav file names based off of path 
for (i in 1:length(directoryArr)){
  pos <- unlist(gregexpr(pattern ='/',directoryArr[i]))
  wave_FilesArr[i] <- substr(as.character(directoryArr[i]), pos[length(pos)]+1, nchar(directoryArr[i]))
}

library(soundecology)

#multiple_sounds_EditforLS(directoryArr,wave_FilesArr ,resultfile = res, soundindex = soundindex ,no_cores =no_cores, fromArr, toArr, units = "seconds",anthro_min = 10, anthro_max = 3000, bio_min = 3000, bio_max = 40000)
#soundindex<- "mean_amp"


#  soundindex<- "mean_amp"
#  res <- paste("C:/Users/evan/Desktop/30 seconds/dataRMSrun30 seconds",i,".csv", sep= "") 
#  multiple_sounds_EditforLS(directoryArr[1:960],wave_FilesArr[1:960] ,resultfile = res, soundindex = soundindex ,no_cores =no_cores, fromArr[1:960], toArr[1:960], units = "seconds")
  
 # res <- paste("C:/Users/evan/Desktop/30 seconds/dataACIrun30 seconds",i,".csv", sep= "") 
  soundindex<-"acoustic_complexity"
  multiple_sounds_EditforLS(directoryArr[1:960],wave_FilesArr[1:960] ,resultfile = res, soundindex = soundindex ,no_cores =no_cores, fromArr[1:960], toArr[1:960], units = "seconds")
  
#  res <- paste("C:/Users/evan/Desktop/30 seconds/dataNDSIrun30 seconds",i,".csv", sep= "") 
#  soundindex<-"ndsi"
#  multiple_sounds_EditforLS(directoryArr[1:960],wave_FilesArr[1:960] ,resultfile = res, soundindex = soundindex ,no_cores =no_cores, fromArr[1:960], toArr[1:960], units = "seconds")
}


#}




#
#OLD 
#

#dir <- "N:/soundscape data 3 days/Station 1 Arboretum"
#res <- "C:/Users/evan/Desktop/results"
#directoryArr<-c("/Volumes/Beever_SoundscapesBackup/Station1 Arboretum/Data/S4A01979_20170602_100000.wav",	"/Volumes/Beever_SoundscapesBackup/Station2 Solar Farm/Data/S4A04376_20170602_100000.wav",	"/Volumes/Beever_SoundscapesBackup/Station3 Union/Data/S4A04386_20170602_100000.wav","/Volumes/Beever_SoundscapesBackup/Station1 Arboretum/Data/S4A01979_20170602_110000.wav",	"/Volumes/Beever_SoundscapesBackup/Station2 Solar Farm/Data/S4A04376_20170602_110000.wav",	 "/Volumes/Beever_SoundscapesBackup/Station3 Union/Data/S4A04386_20170602_110000.wav", "/Volumes/Beever_SoundscapesBackup/Station1 Arboretum/Data/S4A01979_20170602_120000.wav",	 "/Volumes/Beever_SoundscapesBackup/Station2 Solar Farm/Data/S4A04376_20170602_120000.wav",	"/Volumes/Beever_SoundscapesBackup/Station3 Union/Data/S4A04386_20170602_120000.wav","/Volumes/Beever_SoundscapesBackup/Station1 Arboretum/Data/S4A01979_20170602_130000.wav",	"/Volumes/Beever_SoundscapesBackup/Station2 Solar Farm/Data/S4A04376_20170602_130000.wav",	"/Volumes/Beever_SoundscapesBackup/Station3 Union/Data/S4A04386_20170602_130000.wav","/Volumes/Beever_SoundscapesBackup/Station1 Arboretum/Data/S4A01979_20170602_140000.wav",	"/Volumes/Beever_SoundscapesBackup/Station2 Solar Farm/Data/S4A04376_20170602_140000.wav","/Volumes/Beever_SoundscapesBackup/Station3 Union/Data/S4A04386_20170602_140000.wav")
#wave_FilesArr<-c("S4A01979_20170602_100000.wav",	"S4A04376_20170602_100000.wav",	"S4A04386_20170602_100000.wav","S4A01979_20170602_110000.wav",	"S4A04376_20170602_110000.wav",	 "S4A04386_20170602_110000.wav", "S4A01979_20170602_120000.wav",	 "S4A04376_20170602_120000.wav",	"S4A04386_20170602_120000.wav","S4A01979_20170602_130000.wav",	"S4A04376_20170602_130000.wav",	"S4A04386_20170602_130000.wav","S4A01979_20170602_140000.wav",	"S4A04376_20170602_140000.wav","S4A04386_20170602_140000.wav")
#fromArr <- c(0,0,0,40,40,40,70,70,70,300,300,300,0,0,0)
