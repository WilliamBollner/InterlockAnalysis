package model;

import java.util.List;

public class BoardMember {
    private String name;
    private List<Company> companies;
    private String position;
    private double influenceScore;

    public BoardMember() {}

    public BoardMember(String name) {
        this.name = name;
    }

    public BoardMember(String name, List<Company> companies) {
        this.name = name;
        this.companies = companies;
    }
    public BoardMember(String name, List<Company> companies, String position) {
        this.name = name;
        this.companies = companies;
        this.position = position;
    }


    public String getName() {
        return name;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public String getPosition() {
        return position;
    }

    public double getInfluenceScore() {
        return influenceScore;
    }

    public void setInfluenceScore(double influenceScore) {
        this.influenceScore = influenceScore;
    }
}