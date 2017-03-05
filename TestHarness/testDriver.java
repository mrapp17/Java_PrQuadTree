import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.Random;

import Minor.P3.Client.Point;
import Minor.P3.DS.Direction;
import Minor.P3.DS.Compare2D;
import Minor.P3.DS.prQuadTree;
import Minor.P3.DS.Lewis;

// Assumes presence of a file named seed.txt, containing an integer value.

@SuppressWarnings("unused")
public class testDriver {
	
	static int xMin = 0;          // world is [0, 2^15] on all sides
	static int xMax = 1 << 15;
	static int yMin = 0;
	static int yMax = 1 << 15;
	static Vector<Point> data;
	static Vector<Long> seps;     // holds subregion boundaries 
	static long randSeed = -1;
    
	public static void main(String[] args) throws IOException {
		
		String logName = new String("Summary.txt");
		FileWriter Log;
		try {
			Log = new FileWriter(logName);
		}
		catch ( IOException e ) {
			System.out.println("Error creating log file: " + logName);
			System.out.println("Exiting...");
			return;
		}
		
		int numTestsRun    = 0;
		Long randSeed = 0L;
      FileReader sfile = null;
      try {
          sfile = new FileReader("seed.txt");
      }
      catch ( IOException fnf ) {
         System.out.println("Exception opening seed file.");
         System.out.println("Run BSTGenerator first.");
         Log.close();
         return;
      }
      char[] str = new char[30];
      sfile.read( str, 0, 25 );
      String Str = new String(str);
      randSeed = Long.parseLong(Str.trim());
      sfile.close();
        
		seps = new Vector<Long>();
		computePartition(10);
		data = new Vector<Point>();
		generatePoints();
		
		checkScatterAll();
		
		Lewis robbie = new Lewis(xMin, xMax, yMin, yMax, data, randSeed, false);
		
		try {
			try {
			   checkTreeInitialization(robbie);
			   ++numTestsRun;
			   Log.write(" Completed test of quadtree initialization.\n");			}
			catch ( Exception e ) {
			   Log.write("Exception caught while testing tree initialization: " + e + "\n");
			   Log.write("Aborting remaining tests.\n");
			   Log.close();
			   return;
			}

			try {
			   checkInsertion(robbie);
			   Log.write(" Completed test of quadtree insertion.\n");
			   ++numTestsRun;
			}
			catch ( Exception e ) {
			   Log.write("Exception caught while testing insertion: " + e + "\n");
			   Log.write("Aborting remaining tests.\n");
			   Log.close();
			   return;
			}

			try {
			   checkRegionSearch(robbie);
			   ++numTestsRun;
			   Log.write(" Completed test of quadtree region search.\n");
			}
			catch ( Exception e ) {
			   Log.write("Exception caught while testing region search: " + e + "\n");
			}

			try {
			   checkDeletion(robbie);
			   ++numTestsRun;
			   Log.write(" Completed test of quadtree deletion.\n");
			}
			catch ( Exception e ) {
			   Log.write("Exception caught while testing deletion: " + e + "\n");
			}
		} 
		catch (Exception e) {
           Log.write("Exception caught in main: " + e.getMessage() + "\n");
		}
        
		Log.write(" Completed " + numTestsRun + " tests.\n" );
		Log.close();
	}
	
	private static void checkTreeInitialization(Lewis robbie) throws IOException {
		
	    robbie.checkTreeInitialization();
	}
	
	private static void checkInsertion(Lewis robbie) throws IOException {
		
       robbie.checkInsertion();
	}
	
	private static void checkDeletion(Lewis robbie) throws IOException {
		
       robbie.checkDeletion();
	}
	
	private static void checkRegionSearch(Lewis robbie) throws IOException {
		
       robbie.checkRegionSearch();
	}

	private static void generatePoints() throws IOException {
		
		Random source = new Random( randSeed );
		int numPts =  30;// + Math.abs(source.nextInt()) % 6;
		
		int pt = 0;
		while ( pt < numPts ) {
			long x = Math.abs(source.nextInt()) % xMax;
			long y = Math.abs(source.nextInt()) % yMax;
			
			if ( seps.contains(x) ) {
				++x;
			}
			if ( seps.contains(y) ) {
				++y;
			}
			
			Point nxt = new Point(x, y);
			if ( checkScatterOK( nxt, 4L) ) {
			   if ( !data.contains(nxt) ) {
				   ++pt;
			      data.add(nxt);
			   }
			   else {
				   System.out.println("too close");
			   }
			}
         else {
            System.out.println("checkScatterOK() said no");
         }
		}
	}
	
	private static boolean checkScatterOK(Point A, long Min) {
		
		for (int i = 0; i < data.size(); i++) {
			Point N = data.get(i);
			if ( taxiDistance(A, N) < Min )
				return false;
		}
		return true;
	}
	
	private static long taxiDistance(Point A, Point B) {
		
		return Math.abs(A.getX() - B.getX()) + Math.abs(A.getY() - B.getY());
	}
	
	private static long checkScatterAll() {
		
		long minimumSeparation = (1 << 20);
		for (int i = 0; i < data.size(); i++) {
			Point A = data.get(i);
			for (int j = 0; j < data.size(); j++) {
				if ( j != i ) {
					Point B = data.get(j);
					long currSeparation = taxiDistance(A, B);
					if ( currSeparation < minimumSeparation )
						minimumSeparation = currSeparation;
				}
			}
		}
		return minimumSeparation;
	}
	
	private static void computePartition(int Divisions) {
		
		int numParts = 1 << Divisions;
		int Step = (xMax - xMin) / numParts;
		for (int lvl = 0; lvl <= numParts; lvl++) {
			
			long x = xMin + lvl * Step;
			seps.add( x );
		}
	}
}
