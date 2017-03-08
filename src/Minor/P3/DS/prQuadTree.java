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
   private boolean deleteSuccessStat;
   private ArrayList<T> foundList;
    
   // Initialize quadtree to empty state.
   public prQuadTree(long xMin, long xMax, long yMin, long yMax) {
      this.xMin = xMin;
      this.xMax = xMax;
      this.yMin = yMin;
      this.yMax = yMax;
      root = null;
      insertSuccessStat = false;
      deleteSuccessStat = false;
      foundList = new ArrayList<T>();
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
	   deleteSuccessStat = false;
	   root = deleteHelper(root,elem, xMin, xMax, yMin, yMax);
	   return deleteSuccessStat;
	}

   // Pre:  xLo < xHi and yLo < yHi
   // Returns a collection of (references to) all elements x such that x is 
   //in the tree and x lies at coordinates within the defined rectangular 
   // region, including the boundary of the region.
   public ArrayList<T> find(long xLo, long xHi, long yLo, long yHi) {
   		foundList.clear();
   		findListHelper(root, xLo, xHi, yLo, yHi);
		return foundList;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private prQuadNode deleteHelper(prQuadNode currNode, T elem, long xLo, long xHi, long yLo, long yHi) {
		//Check if node is empty, or if elem is null
		if(currNode == null || elem == null)
			return null;
		else if(currNode.getClass() == prQuadTree.prQuadLeaf.class){
			prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNode;
			if(currNodeLeaf.Elements.get(0).equals(elem)){
				deleteSuccessStat = true;
				return null;
			}
			else
				return currNode;
		}
		else if(currNode.getClass() == prQuadTree.prQuadInternal.class){
			int nonNullCount = 0;
			long xMid = (long) (xHi + xLo) /2;
		    long yMid = (long) (yHi + yLo) /2;
			prQuadTree.prQuadInternal currNodeInt = (prQuadTree.prQuadInternal) currNode;
			//Deterine direction and remove node
			Direction deleteQuad = elem.inQuadrant(xLo, xHi, yLo, yHi);
			if(deleteQuad == Direction.NE){
				currNodeInt.NE = deleteHelper(currNodeInt.NE, elem, xMid, xHi, yMid, yHi);
			}
			else if(deleteQuad == Direction.NW){
				currNodeInt.NW = deleteHelper(currNodeInt.NW, elem, xLo, xMid, yMid, yHi);
			}
			else if(deleteQuad == Direction.SW){
				currNodeInt.SW = deleteHelper(currNodeInt.SW, elem, xLo, xMid, yLo, yMid);
			}
			else if(deleteQuad == Direction.SE){
				currNodeInt.SE = deleteHelper(currNodeInt.SE, elem, xMid, xHi, yLo, yMid);
			}
			//Determine how many null children this node possesses
			if(currNodeInt.NE != null)
				++nonNullCount;
			if(currNodeInt.NW != null)
				++nonNullCount;
			if(currNodeInt.SW != null)
				++nonNullCount;
			if(currNodeInt.SE != null)
				++nonNullCount;
			//If there is greater than 1 non null node return return currNodeInt
			if(nonNullCount > 1){
				return currNodeInt;
			}
			//If there is one non null child return that child
			else if(nonNullCount == 1){
				if(currNodeInt.NE != null)
					return currNodeInt.NE;
				if(currNodeInt.NW != null)
					return currNodeInt.NW;
				if(currNodeInt.SW != null)
					return currNodeInt.SW;
				if(currNodeInt.SE != null)
					return currNodeInt.SE;
			}
			//If there are no nonNull children return null
			else {
				return null;
			}
		}
		//ERROR
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void findListHelper(prQuadNode currNode, long xLo, long xHi, long yLo, long yHi) {
		if(currNode == null)
			return;
		else if(currNode.getClass() == prQuadTree.prQuadLeaf.class){
			prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNode;
			if(currNodeLeaf.Elements.get(0).inBox(xLo, xHi, yLo, yHi))
				foundList.add(currNodeLeaf.Elements.get(0));
			return;
		}
		else if(currNode.getClass() == prQuadTree.prQuadInternal.class){
			prQuadInternal currNodeInt = (prQuadTree.prQuadInternal) currNode;
			if(currNodeInt.NE != null)
				findListHelper(currNodeInt.NE,xLo,xHi,yLo,yHi);
			if(currNodeInt.NW != null)
				findListHelper(currNodeInt.NW,xLo,xHi,yLo,yHi);
			if(currNodeInt.SW != null)
				findListHelper(currNodeInt.SW,xLo,xHi,yLo,yHi);
			if(currNodeInt.SE != null)
				findListHelper(currNodeInt.SE,xLo,xHi,yLo,yHi);
			return;
		}
		
	}
}
	