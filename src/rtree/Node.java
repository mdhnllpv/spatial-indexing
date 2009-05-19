package rtree;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import components.IComponent;

public class Node {
	
	private Rectangle bound = new Rectangle();
	
	private List<Node> childs = new ArrayList<Node>();
	
	private List<IComponent> components = new ArrayList<IComponent>();
	
	private Node parent;
	
	public boolean isLeaf(){
		return childs.size() == 0;
	}
		
	
	/**
	 * 
	 * @param component component
	 * @return the enlargement needed to bound the component
	 */
	public double enlargment(IComponent component){
		Rectangle newRectangle = (Rectangle) bound.createUnion(component.getBound());
		return newRectangle.getHeight() * newRectangle.getWidth() - bound.getHeight() * bound.getWidth();
	}
	
	public List<Node> getChilds(){
		return childs;
	}
	
	public void addChild(Node child){
		childs.add(child);
	}

	public void addComponents(IComponent component) {
		bound.add(component.getBound());
		this.components.add(component);
	}


	public List<IComponent> getComponents() {
		return components;
	}


	public void setBound(Rectangle bound) {
		this.bound = bound;
	}


	public Rectangle getBound() {
		return bound;
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}
	
	public Node getParent(){
		return this.parent;
	}
	
	public Node SplitNode(Node node){
		return null;
	}
	
	/**
	 * Adjust the node bound so it enclosed all children bounds
	 */
	public void Enclose(){
		for ( Node child : childs ){
			bound.add(child.bound);
		}
	}
		
}
