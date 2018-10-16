path <- "/Users/Evan/Desktop/dataNDSIrun.csv"
df <- read.csv(path,header=TRUE)


foo <- data.frame(do.call('rbind', strsplit(as.character(df$FILENAME),'_',fixed=TRUE)))
foo
addfoo<- transform(foo, YEAR = substr(X2, 1, 4), MONTH = substr(X2, 5, 6), DAY = substr(X2, 7,8), HOUR = substr(X3, 1,2))
colnames(addfoo)[1] <- "RECORDER"
foo<- cbind(addfoo,df)
foo
