package Minor.P3.DS;
import java.util.ArrayList;
//import java.io.FileWriter;
//import java.io.IOException;


public class prQuadTree< T extends Compare2D<? super T> > {
	
	abstract class prQuadNode {
		
	}
	class prQuadLeaf extends prQuadNode {
		
		public prQuadLeaf() {
		   Elements = new ArrayList<T>(0);
		}
		
		public prQuadLeaf( T data ) {
			Elements = new ArrayList<T>(0);
			Elements.add(data);
		}

		public ArrayList<T> Elements;
	}
   class prQuadInternal extends prQuadNode {
    	
  	   public prQuadInternal() {
	       NW = null;
	       SW = null;
	       SE = null;
	       NE = null;
  	   }

  	   public prQuadNode NW, SW, SE, NE;
   }
    
   prQuadNode root;
   long xMin, xMax, yMin, yMax;
   
   private boolean insertSuccessStat;
   private ArrayList<T> foundList;
    
   // Initialize quadtree to empty state.
   public prQuadTree(long xMin, long xMax, long yMin, long yMax) {
      this.xMin = xMin;
      this.xMax = xMax;
      this.yMin = yMin;
      this.yMax = yMax;
      root = null;
   }
    
   // Pre:   elem != null
   // Post:  If elem lies within the tree's region, and elem is not already 
   //        present in the tree, elem has been inserted into the tree.
   // Return true iff elem is inserted into the tree. 
   public boolean insert(T elem) {
	   insertSuccessStat = false;
	   if(!elem.inBox(xMin, xMax, yMin, yMax))
		   return insertSuccessStat;
	   prQuadLeaf newNode = new prQuadLeaf(elem);
	   root = insertHelper(root, newNode, xMin, xMax, yMin, yMax);
	   return insertSuccessStat;
	}
   
   // Pre:  elem != null
   // Returns reference to an element x within the tree such that 
   // elem.equals(x)is true, provided such a matching element occurs within
   // the tree; returns null otherwise.
   public T find(T elem) {
	   return findHelper(root, elem, xMin, xMax, yMin, yMax);
	}

   // Pre:  elem != null
   // Post: If elem lies in the tree's region, and a matching element occurs
   //       in the tree, then that element has been removed.
   // Returns true iff a matching element has been removed from the tree.   
   public boolean delete(T elem) {
		if(elem == null)
			return false;
		else
			return true;
	}

   // Pre:  xLo < xHi and yLo < yHi
   // Returns a collection of (references to) all elements x such that x is 
   //in the tree and x lies at coordinates within the defined rectangular 
   // region, including the boundary of the region.
   public ArrayList<T> find(long xLo, long xHi, long yLo, long yHi) {
		foundList.clear();
		return null;
	}
	
	// Additonal methods should be added below:
   @SuppressWarnings({ "rawtypes", "unchecked" })
   private prQuadNode insertHelper(prQuadNode currNode, prQuadLeaf newNode,long xLo, long xHi, long yLo, long yHi){
	   //If the current node is null then create new leaf node and return that
	   if(currNode == null) {
		   insertSuccessStat = true;
		   return newNode;
	   }
	   else if (currNode.getClass() == prQuadTree.prQuadLeaf.class){
		   prQuadTree.prQuadLeaf temp = (prQuadTree.prQuadLeaf) currNode;
		   if(temp.Elements.get(0).equals(newNode.Elements.get(0)))
			   return temp;
		   prQuadInternal retNode = new prQuadInternal();
		   insertHelper(retNode,temp, xLo, xHi, yLo, yHi);
		   insertHelper(retNode,newNode, xLo, xHi, yLo, yHi);
		   insertSuccessStat = true;
		   return retNode;
	   }
	   else if(currNode.getClass() == prQuadTree.prQuadInternal.class){
		   //Calculate middle boundaries
		   long xMid = (long) (xHi + xLo) /2;
		   long yMid = (long) (yHi + yLo) /2;
		   //Create a temporary internal node reference
		   prQuadInternal temp = (prQuadInternal) currNode;
		   //Check if new object belongs in NE,NW,SW,or NE quadrant
		   Direction insertQuad = newNode.Elements.get(0).inQuadrant(xLo, xHi, yLo, yHi);
		   if(insertQuad == Direction.NE){
			   temp.NE = insertHelper(temp.NE,newNode, xMid, xHi, yMid, yHi);
			   return temp;
		   }
		   else if(insertQuad == Direction.NW){
			   temp.NW = insertHelper(temp.NW,newNode, xLo, xMid, yMid, yHi);
			   return temp;
		   }
		   else if(insertQuad == Direction.SW){
			   temp.SW = insertHelper(temp.SW,newNode, xLo, xMid, yLo, yMid);
			   return temp;
		   }
		   else if(insertQuad == Direction.SE){
			   temp.SE = insertHelper(temp.SE,newNode, xMid, xHi, yLo, yMid);
			   return temp;
		   }
		   else{
			   //Everything is broken throw an error or something and ret null
			   return null;
		   }
	   }
	   else{
		   //ERROR
		   return null;
	   }
   }


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private T findHelper(prQuadNode currNode, T elem, long xLo, long xHi, long yLo, long yHi){
		if(currNode == null)
			return null;
		else if(currNode.getClass() == prQuadTree.prQuadLeaf.class){
			prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNode;
			if(currNodeLeaf.Elements.get(0).equals(elem))
				return (T) currNodeLeaf.Elements.get(0);
		}
		else if(currNode.getClass() == prQuadTree.prQuadInternal.class){
			long xMid = (long) (xHi + xLo) /2;
			long yMid = (long) (yHi + yLo) /2;
			
			prQuadInternal temp = (prQuadInternal) currNode;
			//Determine which direction within this node it WOULD go, then call the helper method in that direction
			Direction elemDirection = elem.inQuadrant(xLo, xHi, yLo, yHi);
			if(elemDirection == Direction.NE){
				return findHelper(temp.NE, elem, xMid, xHi, yMid, yHi);
			}
			else if(elemDirection == Direction.NW){
				return findHelper(temp.NW, elem, xLo, xMid, yMid, yHi);
			}
			else if(elemDirection == Direction.SW){
				return findHelper(temp.SW, elem, xLo, xMid, yLo, yMid);
			}
			else if(elemDirection == Direction.SE){
				return findHelper(temp.SE, elem, xMid, xHi, yLo, yMid);
			}
			else{
				//ERROR
				return null;
			}
		}
		return null;
	}	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	