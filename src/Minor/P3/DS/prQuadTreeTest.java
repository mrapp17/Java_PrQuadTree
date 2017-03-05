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
		assertTrue(testTree.insert(testPoint_2));
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
		assertTrue(testTree.insert(testPoint_2));
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
		assertEquals(currNodeLeaf.Elements.get(0), testPoint_NW);
		
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
