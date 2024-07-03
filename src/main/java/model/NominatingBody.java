package model;

public class NominatingBody {
    private String name;
    private Company company;
    private int degree;

    public NominatingBody(String name, Company company) {
        this.name = name;
        this.company = company;
    }
    public NominatingBody(String name, int degree) {
        this.name = name;
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }

    public int getDegree() {
        return degree;
    }
}

