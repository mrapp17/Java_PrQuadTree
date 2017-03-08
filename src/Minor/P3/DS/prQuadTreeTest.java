package Minor.P3.DS;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
		Point testPoint_SW_2 = new Point(75,-75);
		//Test find on empty tree
		assertEquals(null, testTree.find(testPoint_NE_1));
		//Test find on tree with one leaf node at root
		testTree.insert(testPoint_NE_1);
		assertEquals(testPoint_NE_1, testTree.find(testPoint_NE_1));
		//Test find on tree with 4 filled leaf nodes
		testTree.insert(testPoint_NW_1);
		testTree.insert(testPoint_SE_1);
		testTree.insert(testPoint_SW_1);
		assertEquals(testPoint_NW_1, testTree.find(testPoint_NW_1));
		assertEquals(testPoint_SE_1, testTree.find(testPoint_SE_1));
		assertEquals(testPoint_SW_1, testTree.find(testPoint_SW_1));
		//Test that elements not in tree are not found
		assertEquals(null, testTree.find(testPoint_NE_2));
		assertEquals(null, testTree.find(testPoint_NW_2));
		assertEquals(null, testTree.find(testPoint_SE_2));
		assertEquals(null, testTree.find(testPoint_SW_2));
		//Test that find works correctly with a stalky branch
		Point testPoint_NE_3 = new Point(24,24);
		assertTrue(testTree.insert(testPoint_NE_3));
		assertEquals(testPoint_NE_3, testTree.find(testPoint_NE_3));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testDelete() {
		//Test that delete will not succeed on an empty tree
		prQuadTree<Point> testTree = new prQuadTree<Point>(-100,100,-100,100);
		Point testPoint_1 = new Point(25,25);
		Point testPoint_2 = new Point(-25,25);
		assertFalse(testTree.delete(null));
		assertFalse(testTree.delete(testPoint_1));
		
		//Test delete on single element tree, root node is a leaf
		testTree.insert(testPoint_1);
		assertFalse(testTree.delete(testPoint_2));
		assertEquals(testPoint_1, testTree.find(testPoint_1));
		assertTrue(testTree.delete(testPoint_1));
		assertEquals(null, testTree.find(testPoint_1));
		
		//Test that after a remove the removed element can be reinserted
		testTree.insert(testPoint_1);
		assertTrue(testTree.delete(testPoint_1));
		assertFalse(testTree.delete(testPoint_1));
		assertEquals(null, testTree.find(testPoint_1));
		testTree.insert(testPoint_1);
		assertEquals(testPoint_1, testTree.find(testPoint_1));
		
		//Test delete on a tree with 4 elements, check tree format after each remove
		Point testPoint_3 = new Point(25,25);
		Point testPoint_4 = new Point(-25,25);
		Point testPoint_5 = new Point(-25,-25);
		Point testPoint_6 = new Point(25,-25);
		testTree.insert(testPoint_3);
		testTree.insert(testPoint_4);
		testTree.insert(testPoint_5);
		testTree.insert(testPoint_6);
		prQuadTree.prQuadInternal currNodeInternal = (prQuadTree.prQuadInternal) testTree.root;
		//Check NE Quadrant
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.NE.getClass());
		prQuadTree.prQuadLeaf currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NE;
		assertEquals(testPoint_3, currNodeLeaf.Elements.get(0));
		assertTrue(testTree.delete(testPoint_3));
		assertEquals(null,currNodeInternal.NE);
		//Check NW Quadrant
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.NW.getClass());
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.NW;
		assertEquals(testPoint_4, currNodeLeaf.Elements.get(0));
		assertTrue(testTree.delete(testPoint_4));
		assertEquals(null,currNodeInternal.NW);
		//Check SW Quadrant (root should be a leaf after)
		assertEquals(prQuadTree.prQuadLeaf.class, currNodeInternal.SW.getClass());
		currNodeLeaf = (prQuadTree.prQuadLeaf) currNodeInternal.SW;
		assertEquals(testPoint_5, currNodeLeaf.Elements.get(0));
		assertTrue(testTree.delete(testPoint_5));
		assertEquals(prQuadTree.prQuadLeaf.class, testTree.root.getClass());
		//Check SE Quadrant (root should be null after)
		currNodeLeaf = (prQuadTree.prQuadLeaf)testTree.root;
		assertEquals(testPoint_6,currNodeLeaf.Elements.get(0));
		assertTrue(testTree.delete(testPoint_6));
		assertEquals(null,testTree.root);
		
		//Test delete on a stalky tree, ensure compression happens correctly
		testTree.root = null;
		Point testPoint_7 = new Point(25,25);
		Point testPoint_8 = new Point(75,75);
		testTree.insert(testPoint_7);
		testTree.insert(testPoint_8);
		assertEquals(prQuadTree.prQuadInternal.class, testTree.root.getClass());
		assertTrue(testTree.delete(testPoint_7));
		assertEquals(prQuadTree.prQuadLeaf.class, testTree.root.getClass());
		assertEquals(null, testTree.find(testPoint_7));
		assertTrue(testTree.delete(testPoint_8));
		assertEquals(null, testTree.root);		
	}

	@Test
	public void testFindList() {
		ArrayList<Point> testArrayList = new ArrayList<Point>();
		prQuadTree<Point> testTree = new prQuadTree<Point>(-100,100,-100,100);
		
		//Test on empty tree
		testArrayList = testTree.find(-100, 100, -100, 100);
		assertTrue(testArrayList.isEmpty());
		
		//Test on root is leaf
		Point testPoint_NE1 = new Point(25,25);
		testTree.insert(testPoint_NE1);
		testArrayList = testTree.find(-100, 100, -100, 100);
		assertTrue(testArrayList.contains(testPoint_NE1));
		testArrayList = testTree.find(-100,0,-100,100);
		assertTrue(testArrayList.isEmpty());
		
		//Test basic operation with root being internal
		Point testPoint_NW1 = new Point(-25,25);
		Point testPoint_SW1 = new Point(-25,-25);
		Point testPoint_SE1 = new Point(25,-25);
		testTree.insert(testPoint_NW1);
		testTree.insert(testPoint_SW1);
		testTree.insert(testPoint_SE1);
		
		//Test on entire area
		testArrayList = testTree.find(-100, 100, -100, 100);
		assertTrue(testArrayList.contains(testPoint_NE1));
		assertTrue(testArrayList.contains(testPoint_NW1));
		assertTrue(testArrayList.contains(testPoint_SW1));
		assertTrue(testArrayList.contains(testPoint_SE1));
		
		//test on area with all four points on border
		testArrayList = testTree.find(-25, 25, -25, 25);
		assertTrue(testArrayList.contains(testPoint_NE1));
		assertTrue(testArrayList.contains(testPoint_NW1));
		assertTrue(testArrayList.contains(testPoint_SW1));
		assertTrue(testArrayList.contains(testPoint_SE1));
		
		//Test on area with zero points
		testArrayList = testTree.find(-10, 10, -10, 10);
		assertFalse(testArrayList.contains(testPoint_NE1));
		assertFalse(testArrayList.contains(testPoint_NW1));
		assertFalse(testArrayList.contains(testPoint_SW1));
		assertFalse(testArrayList.contains(testPoint_SE1));
		
		//Test each Quadrant
		//NE
		testArrayList = testTree.find(0, 100, 0, 100);
		assertEquals(1,testArrayList.size());
		assertTrue(testArrayList.contains(testPoint_NE1));
		assertFalse(testArrayList.contains(testPoint_NW1));
		assertFalse(testArrayList.contains(testPoint_SW1));
		assertFalse(testArrayList.contains(testPoint_SE1));
		
		//NW
		testArrayList = testTree.find(-100, 0, 0, 100);
		assertEquals(1,testArrayList.size());
		assertFalse(testArrayList.contains(testPoint_NE1));
		assertTrue(testArrayList.contains(testPoint_NW1));
		assertFalse(testArrayList.contains(testPoint_SW1));
		assertFalse(testArrayList.contains(testPoint_SE1));
		
		//SW
		testArrayList = testTree.find(-100, 0, -100, 0);
		assertEquals(1,testArrayList.size());
		assertFalse(testArrayList.contains(testPoint_NE1));
		assertFalse(testArrayList.contains(testPoint_NW1));
		assertTrue(testArrayList.contains(testPoint_SW1));
		assertFalse(testArrayList.contains(testPoint_SE1));
		
		//SE
		testArrayList = testTree.find(0, 100, -100, 0);
		assertEquals(1,testArrayList.size());
		assertFalse(testArrayList.contains(testPoint_NE1));
		assertFalse(testArrayList.contains(testPoint_NW1));
		assertFalse(testArrayList.contains(testPoint_SW1));
		assertTrue(testArrayList.contains(testPoint_SE1));
	}
}
