package nju.agilegroup.storymappingtool.view;

import nju.agilegroup.storymappingtool.model.Team;

import java.util.Set;

public class TeamInfo {

    private int id;
    private String name;
    private String description;
    private int leader;
    private String leaderName;
    private Set<AccountInfo> accountInfos;
    private Set<MapInfo> mapInfos;


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

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeader_name(String leaderName) {
        this.leaderName = leaderName;
    }

    public Set<AccountInfo> getAccountInfos() {
        return accountInfos;
    }

    public void setAccountInfos(Set<AccountInfo> accountInfos) {
        this.accountInfos = accountInfos;
    }

    public Set<MapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(Set<MapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    public TeamInfo(int id, String name, String description, int leader, String leaderName, Set<AccountInfo> accountInfos, Set<MapInfo> mapInfos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.leader = leader;
        this.leaderName = leaderName;
        this.accountInfos = accountInfos;
        this.mapInfos = mapInfos;
    }
}
