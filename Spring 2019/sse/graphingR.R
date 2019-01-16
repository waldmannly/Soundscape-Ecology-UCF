#path to the data file 
path <- "/Users/Evan/Desktop/test.csv"

#read in the data file 
df <- read.csv(path,header=TRUE)

#transform the name of the file into different columns
newData <- data.frame(do.call('rbind', strsplit(as.character(df$FILENAME),'_',fixed=TRUE)))
addnewData<- transform(newData, YEAR = substr(X2, 1, 4), MONTH = substr(X2, 5, 6), DAY = substr(X2, 7,8), HOUR = substr(X3, 1,2))
colnames(addnewData)[1] <- "RECORDER"
newData<- cbind(addnewData,df)

#merge month, day, and hour into a time formated column 
DATE<- as.POSIXlt(paste0( newData$YEAR,'/',newData$MONTH,'/', newData$DAY, " ", newData$HOUR,':', "00:00 EST") )
finalData<-cbind(DATE, newData)

#look at all of the data
finalData

#we can look at just the first few entries of the data with 
head(finalData)

#we can graph the data by hour
plot(finalData$HOUR,newData$LEFT_CHANNEL)

#or by day
plot(finalData$DAY, newData$RIGHT_CHANNEL)

# or over time with a point a each hour
plot(finalData$DATE, newData$RIGHT_CHANNEL)

#and we can get fancy with the ggplot2 package as well
#this needs to be run once --> install.packages("ggplot2")
library(ggplot2)
ggplot(finalData, aes(x=finalData$DATE, y=finalData$LEFT_CHANNEL, col=finalData$RECORDER)) + 
  geom_point() + geom_smooth(method='lm',formula=y~x) +  theme_bw() + 
  labs(x="Time", y="Right Channel", title="Title", col="Recorder")
