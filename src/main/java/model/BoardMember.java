package model;

public class BoardMember {
    private String name;
    private String position;
    private Company company;

    public BoardMember(String name, String position, Company company) {
        this.name = name;
        this.position = position;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public Company getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return "BoardMember{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", company=" + company +
                '}';
    }
}