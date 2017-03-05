package Minor.P3.DS;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;


public class prQuadTree< T extends Compare2D<? super T> > {
	
	abstract class prQuadNode {
		
	}
	class prQuadLeaf extends prQuadNode {
		
		public prQuadLeaf() {
		   // up to you
		}
		
		public prQuadLeaf( T data ) {
		   // up to you
		}

		public ArrayList<T> Elements;
	}
   class prQuadInternal extends prQuadNode {
    	
  	   public prQuadInternal() {
	       // up to you
  	   }

  	   public prQuadNode NW, SW, SE, NE;
   }
    
   prQuadNode root;
   long xMin, xMax, yMin, yMax;
   
   // Additional data members can be declared as you see fit.
    
   // Initialize quadtree to empty state.
   public prQuadTree(long xMin, long xMax, long yMin, long yMax) {
      // up to you
   }
    
   // Pre:   elem != null
   // Post:  If elem lies within the tree's region, and elem is not already 
   //        present in the tree, elem has been inserted into the tree.
   // Return true iff elem is inserted into the tree. 
   public boolean insert(T elem) {
		
		return false;
	}

   // Pre:  elem != null
   // Returns reference to an element x within the tree such that 
   // elem.equals(x)is true, provided such a matching element occurs within
   // the tree; returns null otherwise.
   public T find(T Elem) {
		
		return null;
	}

   // Pre:  elem != null
   // Post: If elem lies in the tree's region, and a matching element occurs
   //       in the tree, then that element has been removed.
   // Returns true iff a matching element has been removed from the tree.   
   public boolean delete(T Elem) {
		
		return false;
	}

   // Pre:  xLo < xHi and yLo < yHi
   // Returns a collection of (references to) all elements x such that x is 
   //in the tree and x lies at coordinates within the defined rectangular 
   // region, including the boundary of the region.
   public ArrayList<T> find(long xLo, long xHi, long yLo, long yHi) {
		
		return null;
	}
	
	// Additonal methods should be added below:
	
}
