package model;

public class NominatingBody {
    private String body;
    private Company company;

    public NominatingBody(String body, Company company) {
        this.body = body;
        this.company = company;
    }

    public String getBody() {
        return body;
    }

    public Company getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return "NominatingBody{" +
                "body='" + body + '\'' +
                ", company=" + company +
                '}';
    }
}

