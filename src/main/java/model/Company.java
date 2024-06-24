package model;

public class Company {
    private String name;
    private String acronym;

    public Company(String name, String acronym) {
        this.name = name;
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public String getAcronym() {
        return acronym;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                '}';
    }
}