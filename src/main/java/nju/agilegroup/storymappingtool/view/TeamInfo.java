package nju.agilegroup.storymappingtool.view;

import nju.agilegroup.storymappingtool.model.Team;

public class TeamInfo {

    private String name;
    private int leader;
    private String description;

    public TeamInfo() {
    }


    public TeamInfo(String name, int leader, String description) {
        this.name = name;
        this.leader = leader;
        this.description = description;
    }

    public Team toTeam() {
        return new Team(name, leader,description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
