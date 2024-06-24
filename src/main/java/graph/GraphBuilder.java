package graph;

import model.BoardMember;
import model.Company;
import model.NominatingBody;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;

public class GraphBuilder {
    public Graph buildGraph(List<Object> data) {
        Graph graph = new SingleGraph("Company Graph");

        int edgeCounter = 0;  // Counter to create unique edge IDs

        for (Object obj : data) {
            if (obj instanceof BoardMember) {
                BoardMember member = (BoardMember) obj;
                String companyName = member.getCompany().getName();

                if (graph.getNode(companyName) == null) {
                    graph.addNode(companyName);
                    graph.getNode(companyName).setAttribute("ui.label", companyName);
                }

                String memberName = member.getName();
                if (graph.getNode(memberName) == null) {
                    graph.addNode(memberName);
                    graph.getNode(memberName).setAttribute("ui.label", memberName);
                }

                // Create a unique edge ID
                String edgeId = memberName + "-" + companyName + "-" + edgeCounter++;
                graph.addEdge(edgeId, memberName, companyName, true);
            }
        }
        return graph;
    }
}