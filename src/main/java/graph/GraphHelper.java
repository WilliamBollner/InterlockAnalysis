package graph;

import model.BoardMember;
import model.Company;
import model.NominatingBody;
import org.graphstream.algorithm.APSP;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphHelper {
    private static final String VICE_POSITION = "Vice-presidente (Titular)";
    private static final String PRESIDENT_POSITION = "Presidente (Titular)";

    public static void calculateNBInfluenceByDegrees(Graph graph) {
        int sumDegrees = 0;
        List<NominatingBody> nbList = new ArrayList<>();
        for (Node node : graph) {
            if(node.getAttribute("ui.style").equals("fill-color: green;")) { // means NominatingBody
                NominatingBody nb = new NominatingBody(node.getId(), node.getDegree());
                nbList.add(nb);
                sumDegrees += nb.getDegree();
            }
        }

        Collections.sort(nbList, (nb1, nb2) -> {
            return Integer.compare(nb2.getDegree(), nb1.getDegree()); // desc
        });

        displayOnConsole(nbList, sumDegrees);
    }

    public static List<Company> calculateCompanyInfluence(Graph graph) {
        List<Company> companyList = new ArrayList<>();
        for (Node node : graph) {
            if(node.getAttribute("ui.style").equals("fill-color: pink;")) { // means company
                Company c = new Company(node.getId());
                companyList.add(c);
            }
        }
        List<Company> companiesFilled = fillNBandBMInCompany(companyList, graph);
        for (Company c : companiesFilled) {
            List<String> nbs = new ArrayList<>();
            int sumDegrees = 0;
            int sizeBoardMembers = c.getBoardMembers().size();
            for (NominatingBody nb : c.getNominatingBodies()) {
                if (!nbs.contains(nb.getName())) {
                    sumDegrees += nb.getDegree();
                }
                nbs.add(nb.getName());
            }
            c.setInfluenceScore(sizeBoardMembers * 5 + sumDegrees);
        }
        Collections.sort(companiesFilled, (c1, c2) -> Double.compare(c2.getInfluenceScore(), c1.getInfluenceScore()));
        displayOnConsole(companiesFilled, 0);
        return companiesFilled;
    }

    public static List<BoardMember> calculateBMInfluence(Graph graph, List<Company> companies) {
        List<BoardMember> boardMemberList = new ArrayList<>();

        for (Node node : graph) {
            if (node.getAttribute("ui.style").equals("fill-color: blue;")) { // means BoardMember
                List<Company> companyList = new ArrayList<>();

                Company company = (Company) node.getAttribute("companyName");
                Company company2 = (Company) node.getAttribute("companyName2");

                if (company != null) {
                    for (Company c : companies) {
                        if (company.getName().equals(c.getName())) {
                            company.setInfluenceScore(c.getInfluenceScore());
                            break; // Break after finding the matching company
                        }
                    }
                    companyList.add(company);
                }

                if (company2 != null) {
                    for (Company c : companies) {
                        if (company2.getName().equals(c.getName())) {
                            company2.setInfluenceScore(c.getInfluenceScore());
                            break; // Break after finding the matching company
                        }
                    }
                    companyList.add(company2);
                }

                BoardMember bm = new BoardMember(node.getId(), companyList);
                boardMemberList.add(bm);
            }
        }

        for (BoardMember bm : boardMemberList) {
            int weightPosition = 1;
            if (bm.getPosition() == VICE_POSITION) {
                weightPosition = 5;
            } else if (bm.getPosition() == PRESIDENT_POSITION) {
                weightPosition = 10;
            }

            double sumInfluentialScoreCompanies = 0;
            for (Company c : bm.getCompanies()) {
                if (c != null) {
                    sumInfluentialScoreCompanies += c.getInfluenceScore();
                }
            }

            double influentialScore = sumInfluentialScoreCompanies * weightPosition;
            bm.setInfluenceScore(influentialScore);
        }

        Collections.sort(boardMemberList, (bm1, bm2) -> Double.compare(bm2.getInfluenceScore(), bm1.getInfluenceScore()));
        displayOnConsole(boardMemberList, 0);
        List<BoardMember> influentialBMs = new ArrayList<>();
        for (int i = 0; i < Math.min(boardMemberList.size(), 20); i++) {
            influentialBMs.add(boardMemberList.get(i));
        }
        return influentialBMs;
    }

    public static List<Company> fillNBandBMInCompany(List<Company> companies, Graph graph) {
        for (Edge edge : graph.edges().toList()) {
            String sourceId = edge.getSourceNode().getId();
            String targetId = edge.getTargetNode().getId();

            if (graph.getNode(sourceId).getAttribute("ui.style").equals("fill-color: blue;")) { // BoardMember
                BoardMember bm = new BoardMember(sourceId);
                Company company = findCompanyByName(companies, targetId);
                if (company != null) {
                    company.addBoardMember(bm);
                }
            } else if (graph.getNode(sourceId).getAttribute("ui.style").equals("fill-color: green;")) { // NominatingBody
                NominatingBody nb = new NominatingBody(sourceId, graph.getNode(sourceId).getDegree());
                Company company = findCompanyByName(companies, targetId);
                if (company != null) {
                    company.addNominatingBody(nb);
                }
            }
        }
        return companies;
    }

    private static Company findCompanyByName(List<Company> companies, String name) {
        for (Company company : companies) {
            if (company.getName().equals(name)) {
                return company;
            }
        }
        return null;
    }

    public static <T> void displayOnConsole(List<T> objectsList, int sumDegrees) {
        int i = 1;
        System.out.println("-- Ranking de influência --");
        if (objectsList.get(0) instanceof BoardMember) {
            System.out.println("-- Conselheiros --");
        } else if (objectsList.get(0) instanceof NominatingBody) {
            System.out.println("-- Órgão Indicante --");
        } else {
            System.out.println("-- Empresas --");
        }
        for (T obj : objectsList) {
            if (obj instanceof BoardMember) {
                BoardMember bm = (BoardMember) obj;
                System.out.println(i + " " + bm.getName() + "-- Score de Influência: " + bm.getInfluenceScore() + " -- Conselheiro em: " + bm.getCompanies().size() + " empresa(s)");
                i++;
            } else if (obj instanceof NominatingBody){
                NominatingBody nb = (NominatingBody) obj;
                System.out.println(i + " " + nb.getName() + " -- Grau: " + nb.getDegree() + " -- " + (nb.getDegree() * 100) / sumDegrees +
                        "% " + " do total de indicações");
                i++;
            } else {
                Company c = (Company) obj;
                System.out.println(i + " " + c.getName() + " -- Score de Influência: " + c.getInfluenceScore());
                i++;
            }
        }
    }

    public static void displayShortestPath(Graph graph, List<BoardMember> boardMemberList) {
        for (BoardMember sourceBM : boardMemberList) {
            System.out.println("Menores caminhos para o(a) " + sourceBM.getName());
            APSP apsp = new APSP();
            apsp.init(graph);
            apsp.setDirected(false);
            apsp.setWeightAttributeName("length");
            apsp.compute();
            APSP.APSPInfo info = (APSP.APSPInfo) graph.getNode(sourceBM.getName()).getAttribute(APSP.APSPInfo.ATTRIBUTE_NAME);

            for (Node targetNode : graph) {
                if (targetNode.getAttribute("ui.style").equals("fill-color: pink;")) {
                    System.out.println(info.getShortestPathTo(targetNode.getId()));
                }
            }
        }
    }
}