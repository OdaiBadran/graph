package homework2;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * A Graph consists of a finite set of vertices(nodes).
 * vertex u is called a parent of vertex v if there an 
 * edge from u to v , in the same manner v is called a child 
 * of vertex u if there is an edge from u to v , such pair 
 * commonly referred by (u,v).
 * graph is generic.
 * graph is immutable.
 * each graph has a name to be referred to.
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : string      // name of the graph
 * </pre>
 */

public class Graph<N> {
	
	// RepInvariant:
	  	//   (name != null) && (map != null) && 
		//   for(Node parent : map.keySet)
		//		(parent != null)
		//   	for(Node child : map.get(parent))
		//			(child != null)

	  	// Abstraction Function:
		// name is the name given for the graph ,
		// key u which hold value of List {v1,v2,....,vk} 
		// abstracts the vertices pairs (u,v1),(u,v2),....,,(u,vk)
		// meaning that there is an edge from u to each one of vi , 0 <= i <= k.

	/**
	 *  use of a Hashmap in order to store nodes in the graph
	 */
	private final Map<N, List<N>> map_ = new HashMap<>();
	
	/**
	 *  string to hold the name of the graph
	 */
	private final String name_;
	
	
	/**
	 * creates an empty directed graph named with
	 * the given name.
	 * @param name, the name of the graph
	 * @requires name != null
	 * @effect created an empty graph named with the given name. 
	 */
	public Graph(String name) {
		name_ = name;	
		checkRep();
	}
	
	/**
  	 * check to see if the representation invariant is being violated
  	 * @throw AssertionError if representation invariant is violated
  	 */
	private void checkRep() {
		assert (name_ != null);
		assert (map_ != null);
		for(N parentNode : map_.keySet()) {
			assert (parentNode != null);
			for(N childNode : map_.get(parentNode))
				assert (childNode != null);
		}
	}
	
	
	/**
	 * add the given node to this graph
	 * @requires node != null
	 * @modifies this
	 * @param node
	 * @effect add the given node to this graph if not existed
	 * 		   and return true, otherwise throws VertexAlreadyExistException.
	 * @return true if node was added successfully.
	 * @throws VertexAlreadyExistException		  
	 */
	public boolean addNode(N node) throws VertexAlreadyExistException {
		checkRep();
		// check first wither node already exist
		if(map_.containsKey(node)) {
			throw new VertexAlreadyExistException();
			//System.out.println("VERTEX ALREADY EXIST");
		}
		map_.put(node, new LinkedList<N>());
		checkRep();
		return true;
	}
	
	
	/**
	 * add an edge between parent and child such that
	 * the edge is from parent to child.
	 * @requires parent != null && child != null
	 * @modifies this
	 * @param parent, the parent node which edge is from it.
	 * @param child, the child node which edge is directed to it.
	 * @effect if parent not exist throws ParentNotExistException,
	 * 		   if child not exist throws ChildNotExistException,
	 * 		   if edge from parent to child already exit
	 * 		   throws EdgeAlreadyExistException. 
	 *  	   otherwise:
	 * 		   connect parent node with child node with edge 
	 * 		   such that edge is from parent to child & return true
	 * @return true if edge was add successfully from parent to child.
	 * @throws ParentNotExistException
	 * @throws ChildNotExistException
	 * @throws EdgeAlreadyExistException
	 */
	public boolean addEdge(N parent, N child) throws ParentNotExistException,
		ChildNotExistException, EdgeAlreadyExistException {
		
		checkRep();
		// check first wither parent exits :
		if(!map_.containsKey(parent)) {
			throw new ParentNotExistException();
			//System.out.println("PARENT NOT EXIST");
		}
		// check wither child exits :
		if(!map_.containsKey(child)) {
			throw new ChildNotExistException();
			//System.out.println("CHILD NOT EXIST");
			
		}
		// check wither an edge from parent to child already exist	
		if(map_.get(parent).contains(child)) {
			throw new EdgeAlreadyExistException();
			//System.out.println("EDGE ALREADY EXIST");
			
		}
		map_.get(parent).add(child);
		checkRep();
		return true;
	}
	
	/**
	 * Return immutable list of all the vertices in the graph.
	 * no certain order between vertices is guaranteed.
	 * @return immutable list of all the vertices in the graph.
	 */
	public List<N> listNodes() {
		checkRep();
		List<N> allVertices = new LinkedList<>();
		for(N vertex : map_.keySet()) {
			allVertices.add(vertex);
		}
		List<N> allVerticesImmutable = Collections.unmodifiableList(allVertices);
		checkRep();
		return allVerticesImmutable;
	}
	
	
	/**
	 * Return immutable list of all children of a given parent node.
	 * no certain order between vertices is guaranteed.
	 * @requires parentNode != null
	 * @effect if parent node not exist throws ParentNotExistException,
	 * 		   otherwise return list of all children of the given parent.
	 * @return immutable list of all children of the given parent node.
	 * @throws ParentNotExistException
	 */
	public List<N> listChildren(N parentNode) throws ParentNotExistException {
		checkRep();
		// check first wither parent exits :
		if(!map_.containsKey(parentNode)) {
			throw new ParentNotExistException();
			//System.out.println("PARENT NOT EXIST");
		}
		List<N> childrenNodes = map_.get(parentNode);
		List<N> childrenNodesImmutable = Collections.unmodifiableList(
				map_.get(parentNode));
		checkRep();
		return childrenNodesImmutable;
	}
	public List<N> get_edgeSet(N n){
		checkRep();

		return Collections.unmodifiableList(map_.get(n));
	}
}


