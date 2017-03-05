package Minor.P3.DS;

import static org.junit.Assert.*;

import org.junit.Test;

public class prQuadTreeTest {

	@Test
	public void testPrQuadTreeConstructor() {
		prQuadTree<Point> testTree = new prQuadTree<Point>(-100,100,-90,90);
		assertNull(testTree.root);
		assertEquals(testTree.xMin,-100);
		assertEquals(testTree.xMax,100);
		assertEquals(testTree.yMin,-90);
		assertEquals(testTree.yMax,90);
	}

	@Test
	public void testInsert() {
		prQuadTree<Point> testTree = new prQuadTree<Point>(-100,100,-100,100);
		//Add initial point, check that root node is now a leaf, and its data matches
		Point testPoint_1 = new Point(5,5);
		assertTrue(testTree.insert(testPoint_1));	
		assertEquals(testTree.root.getClass(),prQuadTree.prQuadLeaf.class);
		prQuadTree.prQuadLeaf currNode = (prQuadTree.prQuadLeaf) testTree.root;
		assertEquals(currNode.Elements.get(0),testPoint_1);
		
		//Try to add repeated point, should fail
		Point testPoint_2 = new Point(5,5);
		assertFalse(testTree.insert(testPoint_2));	
		//Add an element into the NW Quadrant, check that root is now internal, navigate to NW
		//Check that NW is now a leaf, check that its data matches, do the same for the NE
		Point testPoint_3 = new Point(-5,5);
		assertTrue(testTree.insert(testPoint_3));
		assertEquals(testTree.root.getClass(),prQuadTree.prQuadInternal.class);
		//Temp variable cast to Internal then check for data element
		prQuadTree.prQuadInternal temp = (prQuadTree.prQuadInternal) testTree.root;
		prQuadTree.prQuadNode newTemp = temp.NW;
		//PrQuadNode.getClass if that equals PrInternal or prLeaf
		//Add a SW and SE point, check that they are both leaves, and data matches
		//Add a new Element in each quadrant, check that each former leaf is now internal, check leaf nodes data
		
		//Add a new element very close to (5,5) check the correct number of internal nodes were created
		//Point testPoint_4 = new Point(5,-5);
		//Point testPoint_5 = new Point(-5,-5);
		
		//Check root node is of the type it should be, then cast it to an Internal Node
		//Pull the nodes from the root and cast as appropriate Nodes	
	}
	
	@Test
	public void testFindT() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindLongLongLongLong() {
		fail("Not yet implemented");
	}

}
