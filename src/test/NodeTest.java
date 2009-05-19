package test;

import static org.junit.Assert.assertEquals;

import java.awt.Rectangle;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import rtree.Node;

import components.SpatialObject2DImpl;

public class NodeTest {

	@Test
	public void addSpatialObject() {
		Node node = new Node(1, 5);

		SpatialObject2DImpl obj1 = new SpatialObject2DImpl();
		obj1.setBound(new Rectangle(10, 10, 10, 10));

		SpatialObject2DImpl obj2 = new SpatialObject2DImpl();
		obj2.setBound(new Rectangle(100, 10, 10, 10));

		SpatialObject2DImpl obj3 = new SpatialObject2DImpl();
		obj3.setBound(new Rectangle(10, 100, 10, 10));

		node.addSpatialObject(obj1);
		assertEquals(node.getBound(), new Rectangle(10, 10, 10, 10));
		node.addSpatialObject(obj2);
		assertEquals(node.getBound(), new Rectangle(10, 10, 100, 10));
		node.addSpatialObject(obj3);
		assertEquals(node.getBound(), new Rectangle(10, 10, 100, 100));
	}

	@Test
	public void addChildNode() {
		Node node = new Node(1, 1);

		Node childNode1 = new Node(1, 1);
		childNode1.setBound(new Rectangle(10, 10, 10, 10));

		node.addChild(childNode1);

		assertEquals(new Rectangle(10, 10, 10, 10), node.getBound());

		Node childNode2 = new Node(1, 1);
		childNode2.setBound(new Rectangle(10, 100, 10, 10));

		node.addChild(childNode2);
		assertEquals(node.getBound(), new Rectangle(10, 10, 10, 100));
	}

	@Test
	public void enlargment() {
		Node node = new Node(1, 1);
		node.setBound(new Rectangle(10, 10, 10, 10));
		SpatialObject2DImpl obj = new SpatialObject2DImpl();
		obj.setBound(new Rectangle(20, 20, 10, 10));
		assertEquals(300, node.enlargment(obj), 0);

		Node node2 = new Node(1, 1);
		node2.addSpatialObject(obj);
		assertEquals(300, node.enlargment(node2), 0);

	}

	@Test
	public void splitNode() {
		Node node = new Node(1, 5);

		SpatialObject2DImpl obj1 = new SpatialObject2DImpl();
		obj1.setBound(new Rectangle(10, 10, 10, 10));
		Node node1 = new Node(1,1);
		node1.addSpatialObject(obj1);
		
		SpatialObject2DImpl obj2 = new SpatialObject2DImpl();
		obj2.setBound(new Rectangle(100, 100, 10, 10));
		Node node2 = new Node(1,1);
		node2.addSpatialObject(obj2);

		SpatialObject2DImpl obj3 = new SpatialObject2DImpl();
		obj3.setBound(new Rectangle(90, 90, 10, 10));
		Node node3 = new Node(1,1);
		node3.addSpatialObject(obj3);
		
		SpatialObject2DImpl obj4 = new SpatialObject2DImpl();
		obj4.setBound(new Rectangle(20, 30, 10, 10));
		Node node4 = new Node(1,1);
		node4.addSpatialObject(obj4);
		
		SpatialObject2DImpl obj5 = new SpatialObject2DImpl();
		obj5.setBound(new Rectangle(30, 20, 20, 10));
		Node node5 = new Node(1,1);
		node5.addSpatialObject(obj5);
		
		node.addChild(node1);
		node.addChild(node2);
		node.addChild(node3);
		node.addChild(node4);
		node.addChild(node5);
		
		List<Set<Node>> newnodes = node.SplitNode();
		assertEquals(3,newnodes.get(0).size(),0);
		assertEquals(2,newnodes.get(1).size(),0);
		
	}

}
