# AN INTRODUCTION TO R 
# This tutorial assumes that you have at least installed R and RStudio. 

# If you are completely new to coding, there are somethings you
# need to know to understand what is going on in R. 

# 1. The pound sign starts a comment.
# This line is not "read" by R, in fact nothing that is after
# a pound sign matters to R. Comments are meant to add clarity
# to your code to people (including yourself) reading it later on. 

# 2. The <- is an assignment operation.
# This means that you are storing a value(s) in a variable. 

# 3. There are restriction on what you are considered variables. 
# For example most words can be variables, but symbol cannot. 
# If you want too see more you can google restricted words in R. 

# 4. There are several types of variables. 
# The most notable two are strings, numbers, and lists. 
# A string always is surronded by quotations marks. 
# Numbers can be either decimals or integers. 
# Lists are several variables in a list (another name for a list is an array). 

# 5. Words that are followed by parathesises are functions. 
# Calling a function is used to execute code that is stored in your R install, 
# packages, or memory. Function can take variables that are called parameters
# so that the values can be used by the function. 

# That should get you started and give you the ability to 
# trouble shoot by doing a google search using the standard vocabualary. 

# RSTUDIO
# An easy way to navigate R 
# press control+enter to run the next line of code that is not a comment. 
# You can see what you have run and what your output is by looking at the console. 


# LIBRARIES
# In order to use code that others have made public availiable through CRAN
# you must tell R that you want to do so. You must install the packages first,
# but you only have to do this once. Once you have installed a package, you can use 
# the package for ever, but R needs to you want to use it, with the library() function. 
# import libraries 
library(tuneR)
library(seewave)
library(soundecology)


# VARIABLES 
## explain variables 
# you can assign values to variables like 
value <- 5 
# you can get the value stored in a variable with just the variable name 
value
# you can make an array with the c() function like 
array <- c(1,2,3,4,5,7)*2
#in this case, I multiplied the array by 2 after creating it

# then you can look at the whole array by writing its name 
array
# or an value at a certain position by either of the below
array[5]
array[value]
# Notice that when you capture a value by storing it a variable,
# it suppress the output from showing on the console. You can 
# type the variable name in the console window to see what is
# in the variable. 

# PATH 
# To use files outside of the folder where your .R file is located, 
# you must tell R where that file is. One way to do this is to provide
# R with a string of the path to your file. 

# path to file you want to analyze 
path<- "C:/Users/evan/Desktop/testing.wav"
# in this path I have a wav file on my desktop named testing

# R can create files if the location you give it does not exist on your 
# computer. Becare with this because you can create files unexpectedly. 
# If the file exists at the location, most of the time R will overwrite 
# that file, unless the code specificly tells R to add on to the exist data 
# with in it. 
  
# path to file where results will be stored
resultPath<- "C:/Users/evan/Desktop/results.csv"
  
# you can use the help function to see what parameters function take in. 
help("readWave")

# length of what you want analyzed 
from<-0
to <- 30
unit<- "seconds"

# read the wav file from memory (the un specified "header" and toWaveMC" 
# parameter will default to FALSE and NULL respectively as seen in documentation 
# you can pull up using the help fucntion)
waveObject <- readWave(path, from, to, unit)

# FUNCTIONS 
# you can call all of these functions. Make sure you give the required 
# parameters and store the data if the function returns any. 

# HELP 
# Rememvber, you can always use the help function to access the documentation
# for the function, like below
help("acoustic_complexity")

# SOUNDSCAPE ECOLOGY FUNCTIONS
# soundecology functions (capture the results)
resultsACI <- acoustic_complexity(waveObject)
resultsADI <- acoustic_diversity(waveObject)
resultsAEI <- acoustic_evenness(waveObject)
resultsNDSI<- ndsi(waveObject)
resultsBAI <- bioacoustic_index(waveObject)

# this function writes the results to the specified file with in the function
multiple_sounds(directory = path, resultfile  =resultPath, soundindex = "ndsi", no_cores="max", flac = false, from = from, to = to, units= unit) 

# explain the concept of the ... in methods 

# Take 5 samples of the file file.wav between 1 - 4 kHz, from 10 to 30 seconds.
measure_signals(wavfile="file.wav", wl=2048, min_freq=1, max_freq=4,
                dBFS_range=30, min_time=10, max_time=30, sample_size=5,
                resultfile="results.csv", plot_range=70)


# SEEWAVE FUNCTIONS
# seewave common graphing functions 
# (these also return useful data that can be capture and analyzed) 
spectro(waveObject)
soundscapespec	#Soundscape frequency spectrum of a time wave
spec	#Frequency spectrum of a time wave
specprop	#Spectral properties
spectro	#2D-spectrogram of a time wave
spectro3D	#3D-spectrogram of a time wave

# seewave functions to look at to do your own analysis
rms(waveObject) #Root Mean Square (amplitudes)
seedata(waveObject)
M(waveObject)#Median of the amplitude envelope
acoustat(waveObject)#Statistics on time and frequency STFT contours
H(waveObject)#Total entropy
fund(waveObject)#Fundamental frequency track
NDSI(waveObject)#Normalized Difference Soundscape Index
dBscale(waveObject)#dB colour scale for a spectrogram 
displaydBweight(waveObject)#dB weightings
meandB(waveObject)#Mean of dB values

# explain concept of documentation, googling, and trial and error
# DOCUMENTATION 
# CRAN and websites, help function

# TROUBLE SHOOTING AND GOOGLE
# 
# Google is a programmers best friend; it is how you can find 
# out how to code when you do not know how to proceed. 
# stackoverflow 

# CRAN WEBSITES 
# https://cran.r-project.org/web/packages/tuneR/ 
# https://cran.r-project.org/web/packages/seewave/
# https://cran.r-project.org/web/packages/soundecology/

# SEEWAVE WEBSITE 
# http://rug.mnhn.fr/seewave/
