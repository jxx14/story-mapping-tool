package nju.agilegroup.storymappingtool.view;

import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;

import java.util.Set;

public class TeamInfo {

    private int id;
    private String name;
    private String description;
    private int leader;
    private String leader_name;
    private Set<AccountInfo> accountInfos;


    public TeamInfo() {
    }


    public Team toTeam() {
        return new Team(name, leader,description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public Set<AccountInfo> getAccountInfos() {
        return accountInfos;
    }

    public void setAccountInfos(Set<AccountInfo> accountInfos) {
        this.accountInfos = accountInfos;
    }

    public TeamInfo(int id, String name, String description, int leader, String leader_name, Set<AccountInfo> accountInfos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.leader = leader;
        this.leader_name = leader_name;
        this.accountInfos = accountInfos;
    }
}
