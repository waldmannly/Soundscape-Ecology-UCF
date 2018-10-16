

reading <- "C:/Users/evan/Desktop/highs and lows across indices.csv"

oldData <- read.csv(reading, header =T)

library(stringr)
wave_FilesArr<-oldData$FILENAME
directoryArr<- rep("default", length(wave_FilesArr))
fromArr<- rep(-1, length(wave_FilesArr))
toArr<- rep(-1, length(wave_FilesArr))
for (i in 1:length(wave_FilesArr)) 
{
  if (grepl("S4A01979", wave_FilesArr[i], fixed=TRUE) )
  {
    directoryArr[i] = paste("D:/Station1 Arboretum/" , wave_FilesArr[i], "_201706", str_pad(oldData$DAY[i], 2, pad = "0"), "_", str_pad(oldData$HOUR[i], 2, pad = "0"), "0000.wav" ,sep="")
  }
  else if (grepl("S4A04386", wave_FilesArr[i], fixed=TRUE))
  {
    directoryArr[i] = paste("D:/Station3 Union/" , wave_FilesArr[i],     "_201706", str_pad(oldData$DAY[i], 2, pad = "0"), "_", str_pad(oldData$HOUR[i], 2, pad = "0"), "0000.wav" ,sep="")
  }
  else if (grepl("S4A04376", wave_FilesArr[i], fixed=TRUE))
  {
    directoryArr[i] = paste("D:/Station2 Solar Farm/" , wave_FilesArr[i], "_201706", str_pad(oldData$DAY[i], 2, pad = "0"), "_", str_pad(oldData$HOUR[i], 2, pad = "0"), "0000.wav" ,sep="")
  }
  #fromArr[i]<- oldData$from[i]
}




##if reading from direct output
for (i in 1:length(wave_FilesArr)) 
{
  if (grepl("S4A01979", wave_FilesArr[i], fixed=TRUE) )
  {
    directoryArr[i] = paste("D:/Station1 Arboretum/" , wave_FilesArr[i],sep="")
  }
  else if (grepl("S4A04386", wave_FilesArr[i], fixed=TRUE))
  {
    directoryArr[i] = paste("D:/Station3 Union/" , wave_FilesArr[i], sep="")
  }
  else if (grepl("S4A04376", wave_FilesArr[i], fixed=TRUE))
  {
    directoryArr[i] = paste("D:/Station2 Solar Farm/" , wave_FilesArr[i],sep="")
  }
  #fromArr[i]<- oldData$from[i]
}









fromArr<- oldData$from
toArr <- fromArr+ rep(10, length(wave_FilesArr))

require(tuneR)
library(tuneR)
library(seewave)
resFolder <- "C:/Users/evan/Desktop/sse soundsBIG/"
for (i in 1:length(wave_FilesArr)) 
{
  wav <- readWave(directoryArr[i])
 if (round(length(wav@left) / wav@samp.rate, 2) == 10 )
 {
   print(paste(directoryArr[i] ," was destroyed"))
 }
  else
  {

waveobj<- readWave(directoryArr[i], from = fromArr[i] , to= toArr[i], units = "seconds")

#be careful not to save over data. 
writeWave(waveobj,filename = paste(resFolder,wave_FilesArr[i], "_201706", oldData$DAY[i], "_", str_pad(oldData$HOUR[i], 2, pad = "0"), "0000 _" ,fromArr[i],".wav"  , sep=""))
}
}




waveobj<- readWave(directoryArr[i], from = 1, to= 2 , units = "seconds")
waveobj

read <- function(name, from, to)
{
  waveobj<- readWave(name, from , to , units = "seconds")
  return(waveobj)
}
waveobj<- read(directoryArr[1], from = 20 , to= 30)


