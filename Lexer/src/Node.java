package lexer;

import java.util.Vector;

public class Node {

	private Vector<Node> childrenNodes;
	private Node fatherNode;
	private Node brotherNode;
	private String name;
	
	/**
	 * @return the childrenNodes
	 */
	public Vector<Node> getChildrenNodes() {
		return childrenNodes;
	}
	/**
	 * @param childrenNodes the childrenNodes to set
	 */
	public void setChildrenNodes(Vector<Node> childrenNodes) {
		this.childrenNodes = childrenNodes;
	}
	/**
	 * @return the fatherNode
	 */
	public Node getFatherNode() {
		return fatherNode;
	}
	/**
	 * @param fatherNode the fatherNode to set
	 */
	public void setFatherNode(Node fatherNode) {
		this.fatherNode = fatherNode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the brotherNode
	 */
	public Node getBrotherNode() {
		return brotherNode;
	}
	/**
	 * @param brotherNode the brotherNode to set
	 */
	public void setBrotherNode(Node brotherNode) {
		this.brotherNode = brotherNode;
	}
	

	
	
}
