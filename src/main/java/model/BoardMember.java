package model;

public class BoardMember {
    private String name;
    private Company company;
    private double pageRank;

    public BoardMember() {}

    public BoardMember(String name) {
        this.name = name;
    }

    public BoardMember(String name, double pageRank) {
        this.name = name;
        this.pageRank = pageRank;
    }

    public BoardMember(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }

    public double getPageRank() {
        return pageRank;
    }
}