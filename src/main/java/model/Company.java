package model;

import java.util.HashSet;
import java.util.Set;

public class Company {
    private String name;
    private String acronym;
    private double influenceScore;
    private Set<BoardMember> boardMembers = new HashSet<>();
    private Set<NominatingBody> nominatingBodies = new HashSet<>();

    public Company(String name) {
        this.name = name;
    }
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

    public double getInfluenceScore() {
        return influenceScore;
    }

    public void setInfluenceScore(double influenceScore) {
        this.influenceScore = influenceScore;
    }

    public Set<BoardMember> getBoardMembers() {
        return boardMembers;
    }

    public void addBoardMember(BoardMember boardMember) {
        boardMembers.add(boardMember);
    }

    public Set<NominatingBody> getNominatingBodies() {
        return nominatingBodies;
    }

    public void addNominatingBody(NominatingBody nominatingBody) {
        nominatingBodies.add(nominatingBody);
    }
}