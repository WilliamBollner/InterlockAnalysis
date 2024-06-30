package model;

public class NominatingBody {
    private String name;
    private Company company;

    public NominatingBody(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return "NominatingBody{" +
                "body='" + name + '\'' +
                ", company=" + company +
                '}';
    }
}

