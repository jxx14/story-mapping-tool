package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.AccountDAO;
import nju.agilegroup.storymappingtool.dao.TeamDAO;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Tool {
    private static AccountDAO accountDAO;
    @Autowired
    public Tool(AccountDAO accountDAO) {
        Tool.accountDAO = accountDAO;
    }

    public static Set<AccountInfo> usersToInfos(Team team){
        Set<AccountInfo> ifs = new HashSet<>();
        for (User user1 : team.getUsers()) {
            AccountInfo info = new AccountInfo();
            info.setName(user1.getName());
            info.setEmail(user1.getEmail());
            ifs.add(info);
        }
        return ifs;
    }

    public static AccountInfo userToInfo(User user){
        AccountInfo info = new AccountInfo();
        info.setName(user.getName());
        info.setEmail(user.getEmail());
        return info;
    }

    public static TeamInfo teamToInfo(Team team){
        TeamInfo info =new TeamInfo();
        info.setDescription(team.getDescription());
        info.setName(team.getName());
        return info;
    }



    public static Set<TeamInfo> teamsToInfos(Set<Team> teams) {
        Set<TeamInfo> ifs = new HashSet<>();
        for (Team team : teams) {
            TeamInfo info = new TeamInfo();
            info.setName(team.getName());
            info.setDescription(team.getDescription());

            int leader_id = team.getLeader_id();
            User leader = accountDAO.getUserById(leader_id);
            String leader_name = leader.getName();
            System.out.println(leader.getName());

            info.setLeader_name(leader_name);
            info.setLeader(leader_id);
            info.setAccountInfos(Tool.usersToInfos(team));

            ifs.add(info);
        }
        return ifs;
    }





}
