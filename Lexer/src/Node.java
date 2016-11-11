package lexer;

import java.util.Set;

public class Node {

	private Set<Node> childrenNodes;
	private Node fatherNode;
	private String name;
	/**
	 * @return the childrenNodes
	 */
	public Set<Node> getChildrenNodes() {
		return childrenNodes;
	}
	/**
	 * @param childrenNodes the childrenNodes to set
	 */
	public void setChildrenNodes(Set<Node> childrenNodes) {
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
	

	
	
}
