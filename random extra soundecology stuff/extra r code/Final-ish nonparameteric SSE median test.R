#SAMPLE PATHS... 
# pathToMedian <- "/Users/Evan/Desktop/real data analysis/processed data to get the median for each sample size/10 samples of 5-second median/median ACI  10 5-second samples RUN "+run+".csv")
#paths with the run parameter in them??? 
pathToMedian10samples30seconds  <- 
pathToMedian10samples5seconds <- 
pathToMedian4samples30seconds <- 
pathToMedian1samples30seconds <- 
pathToMedian1samples5seconds<- 
pathToTrueValue600seconds <- 

# SAMPLE READING IN Data Frames...  
dfTrueValue600Seconds <- data.frame( read.csv(path, header=T))# true value - 600 seconds 
#take average of the 10 samples (divide by (600 divided by the length of the sample) ) 
#ie 600/30 ==> 20 30-second samples in a 10 minute clip... 
df1$LEFT_CHANNEL <- df1$LEFT_CHANNEL/20 
df1$RIGHT_CHANNEL <- df1$RIGHT_CHANNEL/20

df10samples30seconds <- data.frame( read.csv(path1, header=T))# 10 samples - 30 seconds 
#df1samples30seconds <- data.frame( read.csv(path2, header=T))# 1 sample - 30 seconds 
df4samples30seconds  <- data.frame( read.csv(path3, header=T))# 4 samples - 30 seconds 

#brainstorming junk Above ^^^^^^


##########################
# START OF REAL ANALYSIS #
##########################

#path to the csv(s) with the results of the analysis in it... 
pathToResult10samples30seconds<- "/Users/Evan/Desktop/real data analysis/RESULTS2/10samples30seconds.csv"
pathToResult10samples5seconds <-  "/Users/Evan/Desktop/real data analysis/RESULTS2/10samples5seconds.csv"
pathToResult4samples30seconds <-  "/Users/Evan/Desktop/real data analysis/RESULTS2/4samples30seconds.csv"
pathToResult1samples30seconds <-  "/Users/Evan/Desktop/real data analysis/RESULTS2/1samples30seconds.csv"
pathToResult1samples5seconds  <-  "/Users/Evan/Desktop/real data analysis/RESULTS2/1samples5seconds.csv"
pathToResultTrueValue600seconds30secondComparable <-  "/Users/Evan/Desktop/real data analysis/RESULTS1/TrueValue600seconds-30secondComparable.csv"
pathToResultTrueValue600seconds5secondComparable  <-  "/Users/Evan/Desktop/real data analysis/RESULTS1/TrueValue600seconds-5secondComparable.csv"

     ##############
     # 30 SECONDS #
     ##############
#true value path and data doesnt change per run.. 
pathToTrueValue600seconds <- "/Users/Evan/Desktop/real data analysis/tenMinutesCombinedACI.csv"
dfTrueValue600Seconds <- data.frame( read.csv(pathToTrueValue600seconds, header=T))# true value - 600 seconds 
#get the 30 second "comparable" TRUE value
dfTrueValue600Seconds$RIGHT_CHANNEL <- dfTrueValue600Seconds$RIGHT_CHANNEL/20
dfTrueValue600Seconds$LEFT_CHANNEL  <- dfTrueValue600Seconds$LEFT_CHANNEL/20

#10 samples of 30 seconds TESTS 
N <- 30  # some magic number, possibly an overestimate
resDF10samples30seconds <- data.frame(RUN =rep(NA, N), Type= rep("", N) , statistic =rep(NA, N) , p.value =rep(NA, N) , null.value =rep(NA, N) , alternative= rep("", N), method= rep("", N), data.name = rep("", N), stringsAsFactors=FALSE)
i <- 1 
for (run in 1:10)
{
  pathToMedian10samples30seconds<- paste( "/Users/Evan/Desktop/real data analysis/processed data to get the median for each sample size/10 samples of 30-second median /median ACI  10 30-second samples RUN ",run,".csv", sep = "")
  df10samples30seconds <- data.frame( read.csv(pathToMedian10samples30seconds, header=T))# 10 samples - 30 seconds #different each run... 
  
  resRight<- wilcox.test(dfTrueValue600Seconds$RIGHT_CHANNEL, df10samples30seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resRight)
  resDF10samples30seconds[i, ] <- c(RUN =run, Type = "RIGHT", statistic = resRight$statistic, p.value =resRight$p.value, null.value =resRight$null.value, alternative=resRight$alternative, method=resRight$method, data.name =resRight$data.name)
  i<- i+1 
    
  resLeft <- wilcox.test(dfTrueValue600Seconds$LEFT_CHANNEL, df10samples30seconds$LEFT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resLeft)
  resDF10samples30seconds[i, ] <- c(RUN = run, Type = "LEFT", statistic =resLeft$statistic, p.value = resLeft$p.value,null.value = resLeft$null.value, alternative= resLeft$alternative, method= resLeft$method, data.name =resLeft$data.name)
  i<- i+1 
  
  resLeftVRight <- wilcox.test(df10samples30seconds$LEFT_CHANNEL, df10samples30seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  resDF10samples30seconds[i, ] <- c(RUN = run, Type = "LEFTVRIGHT", statistic =resLeftVRight$statistic, p.value = resLeftVRight$p.value,null.value =resLeftVRight$null.value, alternative=resLeftVRight$alternative, method=resLeftVRight$method, data.name =resLeftVRight$data.name)
  i<- i+1 
}
write.csv(resDF10samples30seconds, file = pathToResult10samples30seconds)

#4 samples of 30 seconds TESTS 
N <- 30  # some magic number, possibly an overestimate
resDF4samples30seconds <- data.frame(RUN =rep(NA, N), Type= rep("", N) , statistic =rep(NA, N) , p.value =rep(NA, N) , null.value =rep(NA, N) , alternative= rep("", N), method= rep("", N), data.name = rep("", N), stringsAsFactors=FALSE)
i <- 1 
for (run in 1:10)
{
  pathToMedian4samples30seconds<- paste( "/Users/Evan/Desktop/real data analysis/processed data to get the median for each sample size/4 samples of 30-second median /median ACI  4 30-second samples RUN ",run,".csv", sep = "")
  df4samples30seconds <- data.frame( read.csv(pathToMedian4samples30seconds, header=T))# 4 samples - 30 seconds #different each run... 
  
  resRight<- wilcox.test(dfTrueValue600Seconds$RIGHT_CHANNEL, df4samples30seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resRight) 
  resDF4samples30seconds[i, ] <- c(RUN =run, Type = "RIGHT", statistic = resRight$statistic, p.value =resRight$p.value, null.value =resRight$null.value, alternative=resRight$alternative, method=resRight$method, data.name =resRight$data.name)
  i<- i+1 
  
  resLeft <- wilcox.test(dfTrueValue600Seconds$LEFT_CHANNEL, df4samples30seconds$LEFT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resLeft)
  resDF4samples30seconds[i, ] <- c(RUN = run, Type = "LEFT", statistic =resLeft$statistic, p.value = resLeft$p.value,null.value = resLeft$null.value, alternative= resLeft$alternative, method= resLeft$method, data.name =resLeft$data.name)
  i<- i+1 
  
  resLeftVRight <- wilcox.test(df4samples30seconds$LEFT_CHANNEL, df4samples30seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  resDF4samples30seconds[i, ] <- c(RUN = run, Type = "LEFTVRIGHT", statistic =resLeftVRight$statistic, p.value = resLeftVRight$p.value,null.value =resLeftVRight$null.value, alternative=resLeftVRight$alternative, method=resLeftVRight$method, data.name =resLeftVRight$data.name)
  i<- i+1 
}
write.csv(resDF4samples30seconds, file = pathToResult4samples30seconds)


#1 samples of 30 seconds TESTS
N <- 30  # some magic number, possibly an overestimate
resDF1samples30seconds <- data.frame(RUN =rep(NA, N), Type= rep("", N) , statistic =rep(NA, N) , p.value =rep(NA, N) , null.value =rep(NA, N) , alternative= rep("", N), method= rep("", N), data.name = rep("", N), stringsAsFactors=FALSE)
i <- 1 
for (run in 1:10)
{
  pathToMedian1samples30seconds<- paste( "/Users/Evan/Desktop/real data analysis/processed data to get the median for each sample size/1 samples of 30-second median/median ACI  1 30-second samples RUN ",run,".csv", sep = "")
  df1samples30seconds <- data.frame( read.csv(pathToMedian1samples30seconds, header=T))# 1 samples - 30 seconds #different each run... 
  
  resRight<- wilcox.test(dfTrueValue600Seconds$RIGHT_CHANNEL, df1samples30seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resRight) 
  resDF1samples30seconds[i, ] <- c(RUN =run, Type = "RIGHT", statistic = resRight$statistic, p.value =resRight$p.value, null.value =resRight$null.value, alternative=resRight$alternative, method=resRight$method, data.name =resRight$data.name)
  i<- i+1 
  
  resLeft <- wilcox.test(dfTrueValue600Seconds$LEFT_CHANNEL, df1samples30seconds$LEFT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resLeft)
  resDF1samples30seconds[i, ] <- c(RUN = run, Type = "LEFT", statistic =resLeft$statistic, p.value = resLeft$p.value,null.value = resLeft$null.value, alternative= resLeft$alternative, method= resLeft$method, data.name =resLeft$data.name)
  i<- i+1 
  
  resLeftVRight <- wilcox.test(df1samples30seconds$LEFT_CHANNEL, df1samples30seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  resDF1samples30seconds[i, ] <- c(RUN = run, Type = "LEFTVRIGHT", statistic =resLeftVRight$statistic, p.value = resLeftVRight$p.value,null.value =resLeftVRight$null.value, alternative=resLeftVRight$alternative, method=resLeftVRight$method, data.name =resLeftVRight$data.name)
  i<- i+1 
}
write.csv(resDF1samples30seconds, file = pathToResult1samples30seconds)

#comparison of the true value 30 seconds comparable to itself ... 




     #############
     # 5 SECONDS #
     #############
#true value path and data doesnt change per run.. 
pathToTrueValue600seconds <- "/Users/Evan/Desktop/real data analysis/tenMinutesCombinedACI.csv"
dfTrueValue600Seconds5comparable <- data.frame( read.csv(pathToTrueValue600seconds, header=T))# true value - 600 seconds 
#get the 5 second "comparable" TRUE value
dfTrueValue600Seconds5comparable$RIGHT_CHANNEL <- dfTrueValue600Seconds5comparable$RIGHT_CHANNEL/120
dfTrueValue600Seconds5comparable$LEFT_CHANNEL  <- dfTrueValue600Seconds5comparable$LEFT_CHANNEL/120

#10 samples of 5 seconds TESTS 
N <- 30  # some magic number, possibly an overestimate
resDF10samples5seconds <- data.frame(RUN =rep(NA, N), Type= rep("", N) , statistic =rep(NA, N) , p.value =rep(NA, N) , null.value =rep(NA, N) , alternative= rep("", N), method= rep("", N), data.name = rep("", N), stringsAsFactors=FALSE)
i <- 1 
for (run in 1:10)
{
  pathToMedian10samples5seconds<- paste( "/Users/Evan/Desktop/real data analysis/processed data to get the median for each sample size/10 samples of 5-second median/median ACI  10 5-second samples RUN ",run,".csv", sep = "")
  df10samples5seconds <- data.frame( read.csv(pathToMedian10samples5seconds, header=T))# 10 samples - 30 seconds #different each run... 
  
  resRight<- wilcox.test(dfTrueValue600Seconds5comparable$RIGHT_CHANNEL, df10samples5seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resRight) 
  resDF10samples5seconds[i, ] <- c(RUN =run, Type = "RIGHT", statistic = resRight$statistic, p.value =resRight$p.value, null.value =resRight$null.value, alternative=resRight$alternative, method=resRight$method, data.name =resRight$data.name)
  i<- i+1 
  
  resLeft <- wilcox.test(dfTrueValue600Seconds5comparable$LEFT_CHANNEL, df10samples5seconds$LEFT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resLeft)
  resDF10samples5seconds[i, ] <- c(RUN = run, Type = "LEFT", statistic =resLeft$statistic, p.value = resLeft$p.value,null.value = resLeft$null.value, alternative= resLeft$alternative, method= resLeft$method, data.name =resLeft$data.name)
  i<- i+1 
  
  resLeftVRight <- wilcox.test(df10samples5seconds$LEFT_CHANNEL, df10samples5seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  resDF10samples5seconds[i, ] <- c(RUN = run, Type = "LEFTVRIGHT", statistic =resLeftVRight$statistic, p.value = resLeftVRight$p.value,null.value =resLeftVRight$null.value, alternative=resLeftVRight$alternative, method=resLeftVRight$method, data.name =resLeftVRight$data.name)
  i<- i+1 
  
}
write.csv(resDF10samples5seconds, file = pathToResult10samples5seconds)

#1 samples of 5 seconds TESTs 
N <- 30  # some magic number, possibly an overestimate
resDF1samples5seconds <- data.frame(RUN =rep(NA, N), Type= rep("", N) , statistic =rep(NA, N) , p.value =rep(NA, N) , null.value =rep(NA, N) , alternative= rep("", N), method= rep("", N), data.name = rep("", N), stringsAsFactors=FALSE)
i <- 1 
for (run in 1:10)
{
  pathToMedian1samples5seconds<- paste( "/Users/Evan/Desktop/real data analysis/processed data to get the median for each sample size/1 samples of 5-second median/median ACI  1 5-second samples RUN ",run,".csv", sep = "")
  df1samples5seconds <- data.frame( read.csv(pathToMedian1samples5seconds, header=T))# 10 samples - 30 seconds #different each run... 
  
  resRight<- wilcox.test(dfTrueValue600Seconds5comparable$RIGHT_CHANNEL, df1samples5seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resRight) 
  resDF1samples5seconds[i, ] <- c(RUN =run, Type = "RIGHT", statistic = resRight$statistic, p.value =resRight$p.value, null.value =resRight$null.value, alternative=resRight$alternative, method=resRight$method, data.name =resRight$data.name)
  i<- i+1 
  
  resLeft <- wilcox.test(dfTrueValue600Seconds5comparable$LEFT_CHANNEL, df1samples5seconds$LEFT_CHANNEL, paired = TRUE, alternative = "two.sided")
  print(resLeft)
  resDF1samples5seconds[i, ] <- c(RUN = run, Type = "LEFT", statistic =resLeft$statistic, p.value = resLeft$p.value,null.value = resLeft$null.value, alternative= resLeft$alternative, method= resLeft$method, data.name =resLeft$data.name)
  i<- i+1 
  
  resLeftVRight <- wilcox.test(df1samples5seconds$LEFT_CHANNEL, df1samples5seconds$RIGHT_CHANNEL, paired = TRUE, alternative = "two.sided")
  resDF1samples5seconds[i, ] <- c(RUN = run, Type = "LEFTVRIGHT", statistic =resLeftVRight$statistic, p.value = resLeftVRight$p.value,null.value =resLeftVRight$null.value, alternative=resLeftVRight$alternative, method=resLeftVRight$method, data.name =resLeftVRight$data.name)
  i<- i+1 
  
}
write.csv(resDF1samples5seconds, file = pathToResult1samples5seconds)


#comparison of the true value 5 seconds comparable to itself ... 



#END
 