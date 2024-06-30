package graph;

import model.BoardMember;
import model.Company;
import model.NominatingBody;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {
    public Graph buildGraph(List<Object> data) {
        Graph graph = new SingleGraph("Interlock Graph");
        int edgeCounter = 0;
        Map<String, Integer> edgeCountMap = new HashMap<>();

        for (Object obj : data) {
            if (obj instanceof Company) {
                Company company = (Company) obj;
                if (graph.getNode(company.getName()) == null) {
                    graph.addNode(company.getName());
                    graph.getNode(company.getName()).setAttribute("ui.label", company.getAcronym());
                    graph.getNode(company.getName()).setAttribute("ui.style", "fill-color: lightgray;");
                }
            } else if (obj instanceof BoardMember) {
                BoardMember bm = (BoardMember) obj;
                if (graph.getNode(bm.getName()) == null) {
                    graph.addNode(bm.getName());
                    graph.getNode(bm.getName()).setAttribute("ui.label", bm.getName());
                    graph.getNode(bm.getName()).setAttribute("ui.style", "fill-color: blue;");
                    // Create a unique edge ID
                    String edgeId = bm.getName() + "-" + bm.getCompany().getName() + "-" + edgeCounter++;
                    graph.addEdge(edgeId, bm.getName(), bm.getCompany().getName(), false);
                }
            } else if (obj instanceof NominatingBody) {
                NominatingBody nb = (NominatingBody) obj;
                System.out.println(nb.getName() + " -- " + nb.getCompany().getName());
                if (graph.getNode(nb.getName()) == null) {
                    graph.addNode(nb.getName());
                    graph.getNode(nb.getName()).setAttribute("ui.label", nb.getName());
                    graph.getNode(nb.getName()).setAttribute("ui.style", "fill-color: green;");
                }

                // Increment the edge counter between this NominatingBody and Company
                String edgeKey = nb.getName() + "-" + nb.getCompany().getName();
                int edgeCount = edgeCountMap.getOrDefault(edgeKey, 0) + 1;
                edgeCountMap.put(edgeKey, edgeCount);

                // Create a unique edge ID
                String edgeId = edgeKey;
                if (graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, nb.getName(), nb.getCompany().getName(), false);
                }
            }
        }
        return graph;
    }
}