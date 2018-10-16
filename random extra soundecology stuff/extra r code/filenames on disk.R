
#parameters 
#dirVol <- "/Volumes/Beever_SoundscapesBackup"
#pat<- ".wav"
#resultLoc<-"/Users/Evan/Desktop/datafilesTest.csv"

makeFileList <- function(directoryPath, pattern, resultCSVFile ){
  dirVol <- directoryPath
  pat<- pattern
  resultLoc<-resultCSVFile
  
  #this is the code for setting up the csv file full of the names 
  dirList<- list.dirs(dirVol)
  numFiles <- rep(-1, length(dirList))
  for (i in 1:length(dirList)){
    numFiles[i] <- length(list.files(dirList[i], pattern = pat, recursive=F, full.names=T)) 
  }
  m <- max(numFiles)
  
  df <- data.frame(matrix(ncol = length(dirList), nrow = m))
  for (i in 1:length(dirList)){
    df[i]<-list.files(dirList[i], pattern = pat, recursive=F, full.names=T)
  }
  write.csv(df, file = resultLoc)
}



#dirs <- list.dirs(dirVol,recursive=TRUE, full.names=TRUE) 
#files <- list.files("/Users/Evan/Desktop/SoundscapeDATA", pattern=".wav", recursive=TRUE, full.names=TRUE)
#arb2 <- "/Volumes/Beever_SoundscapesBackup/Station1 Arboretum/1B Data 2017.7.6"                                                          
#arb1 <- "/Volumes/Beever_SoundscapesBackup/Station1 Arboretum/Data"                                                                      
#far1 <- "/Volumes/Beever_SoundscapesBackup/Station2 Solar Farm/Data"                                                                     
#far2 <- "/Volumes/Beever_SoundscapesBackup/Station2 Solar Farm/Data 2017.7.6"                                                            
#uni1 <- "/Volumes/Beever_SoundscapesBackup/Station3 Union/Data"                                                                          
#uni2 <- "/Volumes/Beever_SoundscapesBackup/Station3 Union/Data 2017.7.6" 
#
#filesa2 <- list.files(arb2, pattern=".wav", recursive=TRUE, full.names=TRUE)
#filesa1 <- list.files(arb1, pattern=".wav", recursive=TRUE, full.names=TRUE)
#filesa2 <- append(filesa2, rep(0, 480 -276))
#length(filesa1)
#length(filesa2)
#filesf1 <- list.files(far1, pattern=".wav", recursive=TRUE, full.names=TRUE)
#filesf2 <- list.files(far2, pattern=".wav", recursive=TRUE, full.names=TRUE)
#length(filesf1)
#length(filesf2)
#filesf2 <- append(filesf2, rep(0, 480 -236))
#filesu1 <- list.files(uni1, pattern=".wav", recursive=TRUE, full.names=TRUE)
#filesu2 <- list.files(uni2, pattern=".wav", recursive=TRUE, full.names=TRUE)
#length(filesu1)
#length(filesu2)
#filesu1 <- append(filesu1, rep(0, 480 -433))
#filesu2 <- append(filesu2, rep(0, 480 -5))
#coords <- data.frame(arb1 =filesa1 , arb2 = filesa2, far1 = filesf1, far2 = filesf2, uni1 =filesu1, uni2= filesu2) 
#coords