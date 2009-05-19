package rtree;

import components.IComponent;

public class RTree {

	private Node root = null;
	
	// Max entries for a node
	private int M;

	public RTree(int m, int M) {
		this.M = M;
		root = new Node();
	}

	private Node ChooseLeaf(Node node,IComponent component) {

		if (node != null) {

			if (node.isLeaf()) {
				return node;
			} else {
				Node selected = node.getChilds().get(0);
				for ( Node child : node.getChilds()){
					if ( selected.enlargment(component) > child.enlargment(component)){
						selected = child;
					}
				}
				
				return ChooseLeaf(selected, component);
			}
		}
		return null;
	}
	
	private void AdjustTree(Node node, Node splited){
		if ( node == root ){
			return;
		}
		Node parent = node.getParent();
		
		parent.Enclose();
		
		if ( parent.getChilds().size() > M ){
			parent.SplitNode(splited);
		}
		
		parent.addChild(splited);
		
		
		
	}
	
		
	public void Insert(IComponent component){
		InternalInsert(root, component);
	}
	
	private void InternalInsert(Node root, IComponent component){
		Node leaf = ChooseLeaf(root, component);
		Node splited = null;
		if ( leaf.getComponents().size() > M ){
			Node newNode = new Node();
			newNode.addComponents(component);
			splited = leaf.SplitNode(newNode);
		} else{
			leaf.addComponents(component);
		}
		
		AdjustTree(leaf,splited);
	}
	
}
