#Multiple sounds
#
# Script to process all .wav files in a directory and save
# the requested index to a .csv file.
#
#

multiple_sounds_EditforLS <- function(directoryArr,wave_FilesArr, resultfile, soundindex = c("ndsi", "acoustic_complexity", "acoustic_diversity", "acoustic_evenness", "bioacoustic_index", "H","rms"), no_cores = 1, fromArr, toArr, units = "seconds", logfile="" , progressfile="progress.txt", ...){
  
  ## checks and requires  ---- 
  
  if (any(soundindex %in% c("ndsi", "acoustic_complexity", "acoustic_diversity", "acoustic_evenness", "bioacoustic_index", "H","rms")) == FALSE){
    stop(paste("Unknown function", soundindex))
  }
  
  if (length(fromArr) != length(toArr)){
      stop("The arguments 'fromArr' and 'toArr' must have the same length")
  }
  
  if (length(directoryArr) != length(wave_FilesArr)){
      stop("The arguments 'directoryArr' and 'wave_FilesArr' must have the same length.")
    }
  if (is.na(units)==TRUE){
      stop("'units' must be specified.")
  }
  if(logfile=="")
  {
    warning("The logfile was not specified. Computed values will not be able to be recovered if the function fails.", immediate. = T)
  }
  
  
  require(tuneR)
  require(parallel)
  require(soundecology)
  require(seewave)
  
  ## progress start  ---- 
  total <- length(toArr) 
  current <- 0
  df <- data.frame(current = current , total = total)
  write.table(df , progressfile, row.names = F, quote=F, sep=" | ")
  

  ## last checks ---- 
  #How many cores this machine has?
  #require(parallel)
  thismachine_cores <- detectCores()
  
  if (no_cores == 0){
    stop("Number of cores can not be 0.")
  }else if (no_cores < -1){
    stop("Number of cores can not be negative.")
  }else if (no_cores == "max"){
    no_cores = thismachine_cores
  }else if (no_cores == -1){
    no_cores = thismachine_cores - 1
  }else if (no_cores > thismachine_cores){
    #Don't try to use more than the number of cores in the machine
    warning(paste(" The number of cores to use can not be more than the
                  cores in this computer: ", detectCores()), immediate.=TRUE)
    no_cores <- thismachine_cores
  }
  
  wav_files <- wave_FilesArr
  
  if (length(wav_files)==0 || length(directoryArr) == 0) {
    stop(paste("Could not find any .wav files in the specified directory or directory was empty"))
  }
  
  
  ## FUNCTIONS START ---- 
  
  
  #Bioacoustic index
  if (soundindex == "bioacoustic_index"){
    ## BAI ---- 
    
    fileheader <- c("FILENAME,SAMPLINGRATE,BIT,DURATION,CHANNELS,INDEX,FFT_W,MIN_FREQ,MAX_FREQ,LEFT_CHANNEL,RIGHT_CHANNEL,from,to")
    
    getfunctionforcluster <- function(directoryPath, soundfile, inCluster = TRUE, to, from,  ...){
      #If launched in cluster, require the package for each node created
      if (inCluster == TRUE){
        require(soundecology)
      }
      
      #Get args
      args <- list(...)
      
      if(!is.null(args[['min_freq']])) {
        min_freq = args[['min_freq']]
      }else{
        min_freq = formals(bioacoustic_index)$min_freq
      }
      if(!is.null(args[['max_freq']])) {
        max_freq = args[['max_freq']]
      }else{
        max_freq = formals(bioacoustic_index)$max_freq
      }
      if(!is.null(args[['fft_w']])) {
        fft_w = args[['fft_w']]
      }else{
        fft_w = formals(bioacoustic_index)$fft_w
      }
      
      
      if (.Platform$OS.type == "windows"){
        soundfile_path = paste(directoryPath, sep="")
      }else{
        soundfile_path = paste(directoryPath, sep="")
      }
      
  
      require(tuneR)
      library(tuneR)
      
      if (is.na(from)==FALSE){
        this_soundfile <- readWave(soundfile_path, from = from, to = to, units = "seconds")
      }else{
        this_soundfile <- readWave(soundfile_path)
      }
      
      return_list <- bioacoustic_index(this_soundfile, ...)
      
      if (this_soundfile@stereo == TRUE){
        no_channels = 2
      }else{
        no_channels = 1
      }
      
      prog <- read.table(progressfile, header = T,  sep="|")
      prog$current <- prog$current + 1
      write.table(prog , progressfile, row.names = F, quote=F, sep=" | ")
      
      
      print(fileheader)
      print(paste( soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", fft_w, ",", min_freq, ",", max_freq, ",", return_list$left_area, ",", return_list$right_area, ",", from, ",", to, sep=""))
      return(paste("\n", soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", fft_w, ",", min_freq, ",", max_freq, ",", return_list$left_area, ",", return_list$right_area, ",", from, ",", to, sep=""))
    }
  }else if (soundindex == "acoustic_diversity"){
    ## ADI ---- 
    
    fileheader <- c("FILENAME,SAMPLINGRATE,BIT,DURATION,CHANNELS,INDEX,MAX_FREQ,DB_THRESHOLD,FREQ_STEPS,LEFT_CHANNEL,RIGHT_CHANNEL,from,to")
    
    getfunctionforcluster <- function(directoryPath, soundfile, inCluster = TRUE, to, from,  ...){
      #If launched in cluster, require the package for each node created
      if (inCluster == TRUE){
        require(soundecology)
      }
      
      #Get args
      args <- list(...)
      
      if(!is.null(args[['db_threshold']])) {
        db_threshold = args[['db_threshold']]
      }else{
        db_threshold = formals(acoustic_diversity)$db_threshold
      }
      if(!is.null(args[['max_freq']])) {
        max_freq = args[['max_freq']]
      }else{
        max_freq = formals(acoustic_diversity)$max_freq
      }
      if(!is.null(args[['freq_step']])) {
        freq_step = args[['freq_step']]
      }else{
        freq_step = formals(acoustic_diversity)$freq_step
      }
    
      
      if (.Platform$OS.type == "windows"){
        soundfile_path = paste(directoryPath, sep="")
      }else{
        soundfile_path = paste(directoryPath, sep="")
      }
      
      
      require(tuneR)
      library(tuneR)
      
      if (is.na(from)==FALSE){
        this_soundfile <- readWave(soundfile_path, from = from, to = to, units = "seconds")
      }else{
        this_soundfile <- readWave(soundfile_path)
      }			
      
      return_list <- acoustic_diversity(this_soundfile, ...)
      
      if (this_soundfile@stereo == TRUE){
        no_channels = 2
      }else{
        no_channels = 1
      }
      
      prog <- read.table(progressfile, header = T,  sep="|")
      prog$current <- prog$current + 1
      write.table(prog , progressfile, row.names = F, quote=F, sep=" | ")
      
      
      print(fileheader)
      print(paste( soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", max_freq, ",", db_threshold, ",", freq_step, ",", return_list$adi_left, ",", return_list$adi_right, ",", from, ",", to, sep=""))
      return(paste("\n", soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", max_freq, ",", db_threshold, ",", freq_step, ",", return_list$adi_left, ",", return_list$adi_right, ",", from, ",", to, sep=""))
    }
  }else if (soundindex == "acoustic_complexity"){
    ## ACI ---- 
    
    fileheader <- c("FILENAME,SAMPLINGRATE,BIT,DURATION,CHANNELS,INDEX,FFT_W,MIN_FREQ,MAX_FREQ,J,LEFT_CHANNEL,RIGHT_CHANNEL,from,to")
    
    getfunctionforcluster <- function(directoryPath, soundfile, inCluster = TRUE, to, from,  ...){
      #If launched in cluster, require the package for each node created
      if (inCluster == TRUE){
        require(soundecology)
      }
      
      #Get args
      args <- list(...)
      
      if(!is.null(args[['max_freq']])) {
        max_freq = args[['max_freq']]
      }else{
        max_freq = formals(acoustic_complexity)$max_freq
      }
      if(!is.null(args[['min_freq']])) {
        min_freq = args[['min_freq']]
      }else{
        min_freq = 1
      }
      if(!is.null(args[['j']])) {
        j = args[['j']]
      }else{
        j = formals(acoustic_complexity)$j
      }
      if(!is.null(args[['fft_w']])) {
        fft_w = args[['fft_w']]
      }else{
        fft_w = formals(acoustic_complexity)$fft_w
      }
      
      
      if (.Platform$OS.type == "windows"){
        soundfile_path = paste(directoryPath, sep="")
      }else{
        soundfile_path = paste(directoryPath, sep="")
      }
      
      
      require(tuneR)
      library(tuneR)
      
      if (is.na(from)==FALSE){
        this_soundfile <- readWave(soundfile_path, from = from, to = to, units = "seconds")
      }else{
        this_soundfile <- readWave(soundfile_path)
      }
      
      return_list <- acoustic_complexity(this_soundfile, ...)
      
      if (this_soundfile@stereo == TRUE){
        no_channels = 2
      }else{
        no_channels = 1
      }
      
      
      prog <- read.table(progressfile, header = T,  sep="|")
      prog$current <- prog$current + 1
      write.table(prog , progressfile, row.names = F, quote=F, sep=" | ")
      
      print(fileheader)
      print(paste( soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", fft_w, ",", min_freq, ",", max_freq, ",", j, ",", return_list$AciTotAll_left, ",", return_list$AciTotAll_right, ",", from, ",", to, sep=""))
      return(paste("\n", soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", fft_w, ",", min_freq, ",", max_freq, ",", j, ",", return_list$AciTotAll_left, ",", return_list$AciTotAll_right, ",", from, ",", to, sep=""))
    }
  }else if (soundindex == "ndsi"){
    ## NDSI ---- 
    
    fileheader <- c("FILENAME,SAMPLINGRATE,BIT,DURATION,CHANNELS,INDEX,FFT_W,ANTHRO_MIN,ANTHRO_MAX,BIO_MIN,BIO_MAX,LEFT_CHANNEL,RIGHT_CHANNEL,from,to")
    
    getfunctionforcluster <- function(directoryPath, soundfile, inCluster = TRUE, to, from,  ...){
      #If launched in cluster, require the package for each node created
      if (inCluster == TRUE){
        require(soundecology)
      }
      
      #Get args
      args <- list(...)
      
      if(!is.null(args[['fft_w']])) {
        fft_w = args[['fft_w']]
      }else{
        fft_w = formals(ndsi)$fft_w
      }
      if(!is.null(args[['anthro_min']])) {
        anthro_min = args[['anthro_min']]
      }else{
        anthro_min = formals(ndsi)$anthro_min
      }
      if(!is.null(args[['anthro_max']])) {
        anthro_max = args[['anthro_max']]
      }else{
        anthro_max = formals(ndsi)$anthro_max
      }
      if(!is.null(args[['bio_min']])) {
        bio_min = args[['bio_min']]
      }else{
        bio_min = formals(ndsi)$bio_min
      }
      if(!is.null(args[['bio_max']])) {
        bio_max = args[['bio_max']]
      }else{
        bio_max = formals(ndsi)$bio_max
      }
      
      if (.Platform$OS.type == "windows"){
        soundfile_path = paste(directoryPath, sep="")
      }else{
        soundfile_path = paste(directoryPath, sep="")
      }
      
      
      require(tuneR)
      library(tuneR)
      
      if (is.na(from)==FALSE){
        this_soundfile <- readWave(soundfile_path, from = from, to = to, units = "seconds")
      }else{
        this_soundfile <- readWave(soundfile_path)
      }
        
      return_list <- ndsi(this_soundfile, ...)
      
      if (this_soundfile@stereo == TRUE){
        no_channels = 2
      }else{
        no_channels = 1
      }
      
      prog <- read.table(progressfile, header = T,  sep="|")
      prog$current <- prog$current + 1
      write.table(prog , progressfile, row.names = F, quote=F, sep=" | ")
      
      print(fileheader)
      print(paste( soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", fft_w, ",", anthro_min, ",", anthro_max, ",", bio_min, ",", bio_max, ",", return_list$ndsi_left, ",", return_list$ndsi_right, ",", from, ",", to, sep=""))
      return(paste("\n", soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", fft_w, ",", anthro_min, ",", anthro_max, ",", bio_min, ",", bio_max, ",", return_list$ndsi_left, ",", return_list$ndsi_right, ",", from, ",", to, sep=""))
    }
  }else if (soundindex == "H"){
    ## H ---- 
    
    fileheader <- c("FILENAME,SAMPLINGRATE,BIT,DURATION,CHANNELS,INDEX,WL,ENVT,MSMOOTH,KSMOOTH,LEFT_CHANNEL,RIGHT_CHANNEL,from,to")
    
    getfunctionforcluster <- function(directoryPath, soundfile, inCluster = TRUE, to, from,  ...){
      #If launched in cluster, require the package for each node created
      if (inCluster == TRUE){
        require(soundecology)
      }
      
      #Get args
      args <- list(...)
      
      if(!is.null(args[['wl']])) {
        wl = args[['wl']]
      }else{
        wl = formals(H)$wl
      }
      if(!is.null(args[['envt']])) {
        envt = args[['envt']]
      }else{
        envt = formals(H)$envt
      }
      if(!is.null(args[['msmooth']])) {
        msmooth = args[['msmooth']]
      }else{
        msmooth = formals(H)$msmooth
        
        if(is.null(msmooth)) {
          msmooth = "NULL"
        }	
      }
      if(!is.null(args[['ksmooth']])) {
        ksmooth = args[['ksmooth']]
      }else{
        ksmooth = formals(H)$ksmooth
        
        if(is.null(ksmooth)) {
          ksmooth = "NULL"
        }
      }
      
      if (.Platform$OS.type == "windows"){
        soundfile_path = paste(directoryPath, sep="")
      }else{
        soundfile_path = paste(directoryPath, sep="")
      }
      
      
      require(tuneR)
      library(tuneR)
      
      if (is.na(from)==FALSE){
        this_soundfile <- readWave(soundfile_path, from = from, to = to, units = "seconds")
      }else{
        this_soundfile <- readWave(soundfile_path)
      }
        
      if (this_soundfile@stereo == TRUE) {
        left<-channel(this_soundfile, which = c("left"))
        right<-channel(this_soundfile, which = c("right"))
        left_res <- H(left, ...)
        right_res <- H(right, ...)
      }else{
        left<-channel(this_soundfile, which = c("left"))
        left_res <- H(left, ...)
        right_res <- NA
      }
      
      if (this_soundfile@stereo == TRUE){
        no_channels = 2
      }else{
        no_channels = 1
      }
      
      prog <- read.table(progressfile, header = T,  sep="|")
      prog$current <- prog$current + 1
      write.table(prog , progressfile, row.names = F, quote=F, sep=" | ")
      
      print(fileheader)
      print(paste( soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", wl, ",", envt, ",", msmooth, ",", ksmooth, ",", left_res, ",", right_res, ",", from, ",", to, sep=""))
      return(paste("\n", soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", wl, ",", envt, ",", msmooth, ",", ksmooth, ",", left_res, ",", right_res, ",", from, ",", to, sep=""))
    }
  }else if (soundindex == "acoustic_evenness"){ 
    ## accoustic_eveness ----  
    #Acoustic evenness
    
    fileheader <- c("FILENAME,SAMPLINGRATE,BIT,DURATION,CHANNELS,INDEX,MAX_FREQ,DB_THRESHOLD,FREQ_STEPS,LEFT_CHANNEL,RIGHT_CHANNEL,from,to")
    
    getfunctionforcluster <- function(directoryPath, soundfile, inCluster = TRUE, to, from,  ...){
      #If launched in cluster, require the package for each node created
      if (inCluster == TRUE){
        require(soundecology)
      }
      
      #Get args
      args <- list(...)
      
      if(!is.null(args[['db_threshold']])) {
        db_threshold = args[['db_threshold']]
      }else{
        db_threshold = formals(acoustic_evenness)$db_threshold
      }
      if(!is.null(args[['max_freq']])) {
        max_freq = args[['max_freq']]
      }else{
        max_freq = formals(acoustic_evenness)$max_freq
      }
      if(!is.null(args[['freq_step']])) {
        freq_step = args[['freq_step']]
      }else{
        freq_step = formals(acoustic_evenness)$freq_step
      }
      
      if (.Platform$OS.type == "windows"){
        soundfile_path = paste(directoryPath, sep="")
      }else{
        soundfile_path = paste(directoryPath, sep="")
      }
      
      
      require(tuneR)
      library(tuneR)
      
      if (is.na(from)==FALSE){
        this_soundfile <- readWave(soundfile_path, from = from, to = to, units = "seconds")
      }else{
        this_soundfile <- readWave(soundfile_path)
      }
      
      return_list <- acoustic_evenness(this_soundfile, ...)
      
      if (this_soundfile@stereo == TRUE){
        no_channels = 2
      }else{
        no_channels = 1
      }
      
      
      prog <- read.table(progressfile, header = T,  sep="|")
      prog$current <- prog$current + 1
      write.table(prog , progressfile, row.names = F, quote=F, sep=" | ")
      
      print(fileheader)
      print(paste( soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", max_freq, ",", db_threshold, ",", freq_step, ",", return_list$aei_left, ",", return_list$aei_right, ",", from, ",", to, sep=""))
      return(paste("\n", soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", max_freq, ",", db_threshold, ",", freq_step, ",", return_list$aei_left, ",", return_list$aei_right, ",", from, ",", to, sep=""))
    }
  }else if (soundindex == "rms"){
    ## rms ---- 
    
    #fileheader <- c("FILENAME,SAMPLINGRATE,BIT,DURATION,CHANNELS,INDEX,mean,meandb(seewave),rms,from,to")
    fileheader <- c("FILENAME,SAMPLINGRATE,BIT,DURATION,CHANNELS,INDEX,rms,from,to")
    
    getfunctionforcluster <- function(directoryPath, soundfile, inCluster = TRUE, to, from,  ...){
      #If launched in cluster, require the package for each node created
      if (inCluster == TRUE){
        require(soundecology)
      }
      
      #Get args
      args <- list(...)
      
      if(!is.null(args[['db_threshold']])) {
        db_threshold = args[['db_threshold']]
     }else{
       db_threshold = formals(acoustic_evenness)$db_threshold
     }
      if(!is.null(args[['max_freq']])) {
        max_freq = args[['max_freq']]
      }else{
        max_freq = formals(acoustic_evenness)$max_freq
      }
      if(!is.null(args[['freq_step']])) {
        freq_step = args[['freq_step']]
      }else{
        freq_step = formals(acoustic_evenness)$freq_step
      }
      
      if (.Platform$OS.type == "windows"){
        soundfile_path = paste(directoryPath, sep="")
      }else{
        soundfile_path = paste(directoryPath, sep="")
      }
      
      
      require(tuneR)
      require(seewave)
      library(seewave)
      library(tuneR)
      
      if (is.na(from)==FALSE){
        this_soundfile <- readWave(soundfile_path, from = from, to = to, units = "seconds")
      }else{
        this_soundfile <- readWave(soundfile_path)
      }
      dataDB<- spec(this_soundfile, ...)[,"y"]
     
     #not sure if computing mean or meandB is worth it.
     # return_list <- c(mean(dataDB), meandB(dataDB), rms(env(this_soundfile)))
      return_list <-  rms(env(this_soundfile))
      
      if (this_soundfile@stereo == TRUE){
        no_channels = 2
      }else{
        no_channels = 1
      }
      
      prog <- read.table(progressfile, header = T,  sep="|")
      prog$current <- prog$current + 1
      write.table(prog , progressfile, row.names = F, quote=F, sep=" | ")
      
      print(fileheader)
      print(paste( soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", return_list[1], ",", from, ",", to, sep=""))
      #return(paste("\n", soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", return_list[1], ",", return_list[2], ",", return_list[3], ",", from, ",", to, sep=""))
      return(paste("\n", soundfile, ",", this_soundfile@samp.rate, ",", this_soundfile@bit, ",", round(length(this_soundfile@left)/this_soundfile@samp.rate, 2), ",", no_channels, ",", soundindex, ",", return_list[1], ",", from, ",", to, sep=""))
    }
  }
  
  ## running functions part ----  
  #Start timer
  time0 <- proc.time()
  
  cat(fileheader, file = resultfile, append = FALSE)

  #Allways uses parallel... unsure if one 1 core that this slows down the code, it probably does...
    #require(parallel)
    no_files <- length(wav_files)
    
    if (no_cores > no_files){
      no_cores <- no_files
      cat("\n The number of cores to use has been reduced because there are less files than cores available\n")
    }
    
    cat(paste("\n Running the function ", soundindex, "() on ", no_files, " files using ", no_cores, " cores", "\n\n", sep=""))
    
    cl <- makeCluster(no_cores, type = "PSOCK", outfile=logfile)
    
    clusterExport(cl, c('directoryArr', 'wave_FilesArr'))
    wrapper <- function(int1) {
      getfunctionforcluster(directoryPath = directoryArr[int1], soundfile = wave_FilesArr[int1], inCluster =TRUE,to= toArr[int1], from =fromArr[int1],...)
    }
    res <- clusterMap(cl, wrapper, 1:length(directoryArr))
    
    write.table(res, file = resultfile, append = TRUE, quote = FALSE, col.names = FALSE, row.names = FALSE)
    
    #pause to allow all to end
    Sys.sleep(1)
    
    stopCluster(cl)
  
  
  #Stop timer
  time1 <- proc.time() - time0
  cat(paste(" The analysis of ", length(wav_files), " files took ", round(time1["elapsed"], 2), " seconds\n\n", sep = ""))
}
