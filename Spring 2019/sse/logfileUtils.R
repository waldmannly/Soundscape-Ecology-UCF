
convertLogFile <- function(logfilepath){
  log<- read.csv(logfilepath, quote = "" ,header = F, sep="\n")
  ll<- grep("[1]", log$V1,value=T, fixed = T)
  logcl<- substr(ll, 6, nchar(ll)-1) 
  finalLog<- grep("FILENAME,SAMPLINGRATE", logcl,value = F, fixed = T) 
  finalLog<- finalLog+1
  finalLog <- c(1,finalLog)  
  return(logcl[finalLog])
  }