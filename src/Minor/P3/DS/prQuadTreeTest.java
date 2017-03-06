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

	@SuppressWarnings("rawtypes")
	@Test
	public void testInsert() {
		prQuadTree<Point> testTree = new prQuadTree<Point>(-100,100,-100,100);
		//Add initial point, check that root node is now a leaf, and its data matches
		Point testPoint_1 = new Point(5,5);
		assertTrue(testTree.insert(testPoint_1));	
		assertEquals(testTree.root.getClass(),prQuadTree.prQuadLeaf.class);
		prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) testTree.root;
		assertEquals(currNodeLeaf.Elements.get(0),testPoint_1);
		
		//Add a new point which will fall in the NW quadrant, check that root is internal
		Point testPoint_2 = new Point(-5,5);
		assertTrue(testTree.insert(testPoint_2));
		assertEquals(testTree.root.getClass(), prQuadTree.prQuadInternal.class);
		prQuadTree.prQuadInternal currNodeInternal = (prQuadTree.prQuadInternal) testTree.root;
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.NE;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_1);
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NW;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_2);
		
		//Add a new point which will fall in the SW quadrant, check that root is internal
		Point testPoint_3 = new Point(-5,-5);
		assertTrue(testTree.insert(testPoint_3));
		assertEquals(testTree.root.getClass(), prQuadTree.prQuadInternal.class);
		currNodeInternal = (prQuadTree.prQuadInternal) testTree.root;
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.NE;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_1);
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NW;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_2);
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SW;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_3);
		
		//Add a new point which will fall in the SE quadrant, check that root is internal
		Point testPoint_4 = new Point(5,-5);
		assertTrue(testTree.insert(testPoint_4));
		assertEquals(testTree.root.getClass(), prQuadTree.prQuadInternal.class);
		currNodeInternal = (prQuadTree.prQuadInternal) testTree.root;
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.NE;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_1);
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NW;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_2);
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SW;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_3);
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SE;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_4);
		
		//Test that a repeat point can not be added
		Point testPoint_5 = new Point(5,-5);
		assertFalse(testTree.insert(testPoint_5));
		assertEquals(testTree.root.getClass(), prQuadTree.prQuadInternal.class);
		currNodeInternal = (prQuadTree.prQuadInternal) testTree.root;
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.NE;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_1);
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NW;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_2);
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SW;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_3);
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SE;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_4);
		//Test that adding a point resulting in two branches works correctly
		testTree.root = null;
		Point testPoint_B1 = new Point(10,10);
		Point testPoint_B2 = new Point(30,30);
		assertTrue(testTree.insert(testPoint_B1));
		assertTrue(testTree.insert(testPoint_B2));
		
		assertEquals(testTree.root.getClass(), prQuadTree.prQuadInternal.class);
		currNodeInternal = (prQuadTree.prQuadInternal) testTree.root;
		assertEquals(currNodeInternal.getClass(), prQuadTree.prQuadInternal.class);
		currNodeInternal = (prQuadTree.prQuadInternal) currNodeInternal.NE;
		assertEquals(currNodeInternal.getClass(), prQuadTree.prQuadInternal.class);
		currNodeInternal = (prQuadTree.prQuadInternal) currNodeInternal.SW;
		
		assertEquals(currNodeInternal.SW.getClass(), prQuadTree.prQuadLeaf.class);
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.SW;
		assertEquals(testPoint_B1, currNodeLeaf.Elements.get(0));
		
		assertEquals(currNodeInternal.NE.getClass(), prQuadTree.prQuadLeaf.class);
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.NE;
		assertEquals(testPoint_B2, currNodeLeaf.Elements.get(0));
		
		//Test that addition at a boundary is inserted correctly
		//clear the tree, insert at (0,0), (0,75) (75,0) (0,-75) (-75,0)
		testTree.root = null;
		Point testPoint_Origin = new Point();
		Point testPoint_NW = new Point(0,75);
		Point testPoint_NESE = new Point(75,0);
		Point testPoint_SE = new Point(0,-75);
		Point testPoint_SW = new Point(-75,0);
		assertTrue(testTree.insert(testPoint_Origin));
		assertTrue(testTree.insert(testPoint_NW));
		assertTrue(testTree.insert(testPoint_NESE));
		assertTrue(testTree.insert(testPoint_SE));
		assertTrue(testTree.insert(testPoint_SW));
		//Test class of root
		assertEquals(testTree.root.getClass(), prQuadTree.prQuadInternal.class);
		currNodeInternal = (prQuadTree.prQuadInternal) testTree.root;
		
		//Test NW addition
		assertEquals(currNodeInternal.NW.getClass(), prQuadTree.prQuadLeaf.class);
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.NW;
		assertEquals(testPoint_NW, currNodeLeaf.Elements.get(0));
		
		//Test SW Addition
		assertEquals(currNodeInternal.SW.getClass(), prQuadTree.prQuadLeaf.class);
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.SW;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_SW);
		
		//Test SE Addition
		assertEquals(currNodeInternal.SE.getClass(), prQuadTree.prQuadLeaf.class);
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.SE;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_SE);
		
		//Test class of NE Node
		assertEquals(currNodeInternal.NE.getClass(), prQuadTree.prQuadInternal.class);
		currNodeInternal = (prQuadTree.prQuadInternal) currNodeInternal.NE;
		
		//Test SE Addition
		assertEquals(currNodeInternal.SE.getClass(), prQuadTree.prQuadLeaf.class);
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.SE;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_NESE);
		
		//Test SW Addition
		assertEquals(currNodeInternal.SW.getClass(), prQuadTree.prQuadLeaf.class);
		currNodeLeaf = (prQuadTree.prQuadLeaf)currNodeInternal.SW;
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_Origin);
	}
	
	@Test
	public void testFindT() {
		prQuadTree<Point> testTree = new prQuadTree<Point>(-100,100,-100,100);
		//Elements marked 1 will be found, elements marked 2 will no be found in the tree
		Point testPoint_NE_1 = new Point(25,25);
		Point testPoint_NE_2 = new Point(75,75);
		Point testPoint_NW_1 = new Point(-25,25);
		Point testPoint_NW_2 = new Point(-75,75);
		Point testPoint_SE_1 = new Point(-25,-25);
		Point testPoint_SE_2 = new Point(-75,-75);
		Point testPoint_SW_1 = new Point(25,-75);
		Point testPoint_SW_2 = new Point(25,-75);
		//Test find on empty tree
		assertEquals(null, testTree.find(testPoint_NE_1));
		//Test find on tree with one leaf node at root
		testTree.insert(testPoint_NE_1);
		assertEquals(testPoint_NE_1, testTree.find(testPoint_NE_1));
		//Test find on tree with 4 filled leaf nodes
		testTree.insert(testPoint_NW_1);
		testTree.insert(testPoint_SE_1);
		testTree.insert(testPoint_SW_1);
		assertEquals(testPoint_NE_1, testTree.find(testPoint_NW_1));
		assertEquals(testPoint_SE_1, testTree.find(testPoint_SE_1));
		assertEquals(testPoint_SW_1, testTree.find(testPoint_SW_1));
		//Test that elements not in tree are not found
		assertEquals(null, testTree.find(testPoint_NE_2));
		assertEquals(null, testTree.find(testPoint_NW_2));
		assertEquals(null, testTree.find(testPoint_SE_2));
		assertEquals(null, testTree.find(testPoint_SW_2));
		//Test that find works correctly with a stalky branch
		Point testPoint_NE_3 = new Point(20,20);
		assertTrue(testTree.insert(testPoint_NE_3));
		assertEquals(testPoint_NE_3, testTree.find(testPoint_NE_3));
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
