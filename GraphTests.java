package homework2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the graph
 * and shortest path finding algorithm implementations of homework assignment
 * #2.
 */
public class GraphTests extends ScriptFileTests {

	// black-box test are inherited from super
	public GraphTests(java.nio.file.Path testFile) {
		super(testFile);
	}

	private Graph<WeightedNode> graphToTest = new Graph<WeightedNode>("graphToTest");

	/**
	 * get the graph to be tested.
	 * @return the graph to be tested.
	 */
	protected Graph<WeightedNode> getGraph() {
		return graphToTest;
	}

	/**
	 * test adding nodes to graph, test the case where vertex
	 * already exist in the graph.(fall in the if statement)
	 * @throws VertexAlreadyExistException
	 */
	@Test(expected = VertexAlreadyExistException.class)
	public void addNodeTest1() throws VertexAlreadyExistException {
		WeightedNode n1 = new WeightedNode("n1",1);
		getGraph().addNode(n1); // add new node n1 no exception is thrown
		getGraph().addNode(n1); // the node already exist throw exception
	}

	/**
	 * test adding nodes to graph, test the case where vertex
	 * was added successfully to the graph.(skip if statement)
	 * @throws VertexAlreadyExistException
	 */
	@Test(timeout = 100)
	public void addNodeTest() throws VertexAlreadyExistException {
		WeightedNode n1 = new WeightedNode("n1",1);
		assertTrue("vertex not added successfully",getGraph().addNode(n1));
	}

	/**
	 * test adding edges to graph, test the case where parent
	 * not exist.(fall in the first if statement)
	 * @throws EdgeAlreadyExistException
	 * @throws ParentNotExistException
	 * @throws ChildNotExistException
	 * @throws VertexAlreadyExistException
	 */
	@Test(expected = ParentNotExistException.class)
	public void addEdgeTest1() throws ParentNotExistException,
			ChildNotExistException, EdgeAlreadyExistException,
			VertexAlreadyExistException {
		WeightedNode v1 = new WeightedNode("v1",1);
		WeightedNode v2 = new WeightedNode("v2",10);
		getGraph().addNode(v1);     // add v1 to the graph
		getGraph().addEdge(v2, v1); // v2(the parent) not exist in the graph.
		// throw ParentNotExistException
	}

	/**
	 * test adding edges to graph, test the case where child
	 * not exist.(skip first if statement, fall in the second if statement)
	 * @throws EdgeAlreadyExistException
	 * @throws ParentNotExistException
	 * @throws ChildNotExistException
	 * @throws VertexAlreadyExistException
	 */
	@Test(expected = ChildNotExistException.class)
	public void addEdgeTest2() throws ParentNotExistException,
			ChildNotExistException, EdgeAlreadyExistException,
			VertexAlreadyExistException {
		WeightedNode b1 = new WeightedNode("b1",1);
		WeightedNode b2 = new WeightedNode("b2",10);
		getGraph().addNode(b2);     // add b2 to the graph
		getGraph().addEdge(b2, b1); // b1(the child) not exist in the graph.
		// throw ChildNotExistException
	}

	/**
	 * test adding edges to graph, test the case where child
	 * and parent both exist.(skip all if statements)
	 * @throws EdgeAlreadyExistException
	 * @throws ParentNotExistException
	 * @throws ChildNotExistException
	 * @throws VertexAlreadyExistException
	 */
	@Test(timeout = 100)
	public void addEdgeTest3() throws ParentNotExistException,
			ChildNotExistException, EdgeAlreadyExistException,
			VertexAlreadyExistException {
		WeightedNode s1 = new WeightedNode("s1",5);
		WeightedNode s2 = new WeightedNode("s2",10);
		getGraph().addNode(s1);     // add s1 to the graph
		getGraph().addNode(s2);     // add s1 to the graph
		assertTrue("edge not added successfully",getGraph().addEdge(s1, s2));

	}

	/**
	 * test listNode method, test the case where graph is empty.
	 */
	@Test(timeout = 100)
	public void listNodeTest1() {
		assertTrue("graph is not empty",getGraph().listNodes().isEmpty());
	}

	/**
	 * test listNode method, test the case where graph is not empty.
	 * @throws VertexAlreadyExistException
	 */
	@Test(timeout = 100)
	public void listNodeTest2() throws VertexAlreadyExistException {
		WeightedNode n3 = new WeightedNode("n3",3);
		getGraph().addNode(n3);
		assertFalse("graph has not nodes",getGraph().listNodes().isEmpty());
	}

	/**
	 * test listNode method, test the case where parent not exist(fall in
	 * the first if statement).
	 * @throws VertexAlreadyExistException
	 * @throws EdgeAlreadyExistException
	 * @throws ChildNotExistException
	 * @throws ParentNotExistException
	 */
	@Test(expected = ParentNotExistException.class)
	public void listChildrenTest1() throws VertexAlreadyExistException,
			ParentNotExistException, ChildNotExistException, EdgeAlreadyExistException {
		WeightedNode n4 = new WeightedNode("n4",88);
		WeightedNode n5 = new WeightedNode("n5",88);
		getGraph().addNode(n4);
		getGraph().addEdge(n5, n4);
	}

	/**
	 * test listNode method, test the case where list children is
	 * returned successfully(skip first if statement)
	 * @throws VertexAlreadyExistException
	 * @throws EdgeAlreadyExistException
	 * @throws ChildNotExistException
	 * @throws ParentNotExistException
	 */
	@Test(timeout = 100)
	public void listChildrenTest2() throws VertexAlreadyExistException,
			ParentNotExistException, ChildNotExistException, EdgeAlreadyExistException {
		WeightedNode v4 = new WeightedNode("v4",88);
		WeightedNode v5 = new WeightedNode("v5",88);
		getGraph().addNode(v4);
		getGraph().addNode(v5);
		getGraph().addEdge(v5, v4);
		List<WeightedNode> v5Children = getGraph().listChildren(v5);
		assertTrue("wrong children list",v5Children.get(0).equals(v4));
	}
	public void pathfindTest() throws VertexAlreadyExistException,
	ParentNotExistException, ChildNotExistException, EdgeAlreadyExistException {
		WeightedNode s1 = new WeightedNode("s1",1);
		WeightedNode s2 = new WeightedNode("s2",2);
		WeightedNode s3 = new WeightedNode("s2",3);
		getGraph().addNode(s1);     // add s1 to the graph
		getGraph().addNode(s2);// add s2 to the graph
		getGraph().addNode(s3);
		getGraph().addEdge(s1, s2);
		getGraph().addEdge(s2, s1);
		getGraph().addEdge(s3, s1);
		Set<Path<WeightedNode, WeightedNodePath> > starts =
				new HashSet<Path<WeightedNode, WeightedNodePath> >();
		Set<WeightedNode> goals = new HashSet<WeightedNode>();
			starts.add(new WeightedNodePath(s1));
	     	starts.add(new WeightedNodePath(s2));
		goals.add(s1);
		goals.add(s2);
		goals.add(s3);
		Path<WeightedNode, WeightedNodePath> shortest =
				PathFinder.findPath(graphToTest, starts, goals);
		assertEquals(shortest,PathFinder.findPath(graphToTest, starts, goals));
	}

}
