package graph;

import model.BoardMember;
import model.Company;
import model.NominatingBody;
import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {

    public Graph buildGraph(List<Object> data) {
        Graph graph = new MultiGraph("Interlock Graph");
        int edgeCounter = 0;
        Map<String, Integer> edgeCountMap = new HashMap<>();

        for (Object obj : data) {
            if (obj instanceof Company) {
                Company company = (Company) obj;
                if (graph.getNode(company.getName()) == null) {
                    graph.addNode(company.getName());
                    graph.getNode(company.getName()).setAttribute("ui.label", company.getAcronym());
                    graph.getNode(company.getName()).setAttribute("ui.style", "fill-color: pink;");
                    graph.getNode(company.getName()).setAttribute("length", 1);
                }
            } else if (obj instanceof BoardMember) {
                BoardMember bm = (BoardMember) obj;
                if (graph.getNode(bm.getName()) == null) {
                    graph.addNode(bm.getName());
                    graph.getNode(bm.getName()).setAttribute("ui.label", bm.getName());
                    graph.getNode(bm.getName()).setAttribute("ui.style", "fill-color: blue;");
                    graph.getNode(bm.getName()).setAttribute("length", 1);
                }
                String edgeId = bm.getName() + "-" + bm.getCompany().getName() + "-" + edgeCounter++;
                if (graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, bm.getName(), bm.getCompany().getName(), false);
                    graph.getEdge(edgeId).setAttribute("ui.style", "fill-color: gray;");
                }
            } else if (obj instanceof NominatingBody) {
                NominatingBody nb = (NominatingBody) obj;
                if (graph.getNode(nb.getName()) == null) {
                    graph.addNode(nb.getName());
                    graph.getNode(nb.getName()).setAttribute("ui.label", nb.getName());
                    graph.getNode(nb.getName()).setAttribute("ui.style", "fill-color: green;");
                    graph.getNode(nb.getName()).setAttribute("length", 1);
                }
                String edgeId = nb.getName() + "-" + nb.getCompany().getName() + "-" + edgeCounter++;
                if (graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, nb.getName(), nb.getCompany().getName(), false);
                    graph.getEdge(edgeId).setAttribute("ui.style", "fill-color: darkgray;");
                }
            }
        }

        GraphHelper.calculateNBInfluenceByDegrees(graph);
        GraphHelper.calculateCompanyInfluence(graph);

        return graph;
    }
}