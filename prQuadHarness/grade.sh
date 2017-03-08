#! /bin/bash

testLog="testLog.txt"
scoreLog="scoreLog.txt"
reportLog="report.txt"
separator="##############################################################"

########################################################## compare files
compareFiles() {
   refLog=$1
   stuLog=$2
   scoreLog=$3
   if [[ -e $stuLog ]]; then
      java -jar LogComparator.jar $refLog $stuLog > $scoreLog
   else
      echo "$stuLog was not created"
   fi
}

#  Compile your solution:
   echo "Compiling your code..."
   javac testDriver.java
   
   if [[ ! -e "testDriver.class" ]]; then
      echo "Could not find testDriver.class."
      exit -1
   fi
   if [[ ! -e "./Minor/P3/DS/prQuadTree.class" ]]; then
      echo "Could not find prQuadTree.class."
      exit -1
   fi
   if [[ ! -e "./Minor/P3/DS/Compare2D.class" ]]; then
      echo "Could not find Compare2D.class."
      exit -1
   fi
   if [[ ! -e "./Minor/P3/DS/Direction.class" ]]; then
      echo "Could not find Direction.class."
      exit -1
   fi
   if [[ ! -e "./Minor/P3/Client/Point.class" ]]; then
      echo "Could not find Point.class."
      exit -1
   fi
   
#  Generate test data:
#  See if -rand was specified; if so, create random data
   if [[ $# -ge 1 ]]; then
      if [[ "$1" == "-rand" ]]; then
         echo "Running test data generator..."
         java -jar prQuadGenerator.jar
      fi
   fi
   if [[ ! -e "seed.txt" ]]; then
      echo "Could not find seed file: seed.txt."
      echo "You must run first with -rand."
      exit -2
   fi

#  Run student solution on test data:
   echo "Running your solution..."
   java testDriver
   
#  Run comparison tool on the log files:
   echo "Comparing reference logs to logs from your code..."
   echo "Results from comparing test logs:" > $testLog
   echo >> $testLog
   compareFiles "profTestTreeInitialization.txt" "TestTreeInitialization.txt" "initScore.txt"
   compareFiles "profTestInsertion.txt" "TestInsertion.txt" "insertionScore.txt"
   compareFiles "profTestRegionSearch.txt" "TestRegionSearch.txt" "regionScore.txt"
   compareFiles "profTestDeletion.txt" "TestDeletion.txt" "deletionScore.txt"
   
#  Grab score data:
   scoreLog="scoreLog.txt"
   grep "Score" initScore.txt > $scoreLog
   grep "Score" insertionScore.txt >> $scoreLog
   grep "Score" regionScore.txt >> $scoreLog
   grep "Score" deletionScore.txt >> $scoreLog
   
#  Build report file:
   echo "Results from testing:" > $reportLog
   echo >> $reportLog
   
   cat $scoreLog >> $reportLog
   echo $separator >> $reportLog
   
   cat "initScore.txt" >> $reportLog
   echo $separator >> $reportLog
   cat "insertionScore.txt" >> $reportLog
   echo $separator >> $reportLog
   cat "regionScore.txt" >> $reportLog
   echo $separator >> $reportLog
   cat "deletionScore.txt" >> $reportLog
   echo $separator >> $reportLog
   
#  Display score data:
   cat $scoreLog
   echo
   echo "See individual score logs for details..."

   exit 0
  
