package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.AccountDAO;
import nju.agilegroup.storymappingtool.dao.TeamDAO;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService{

    private final TeamDAO teamDAO;
    private final AccountDAO accountDAO;

    @Autowired
       public TeamServiceImpl(TeamDAO teamDAO, AccountDAO accountDAO) {
        this.teamDAO = teamDAO;
        this.accountDAO = accountDAO;
    }


    @Override
    public ResultInfo<Object> getTeamInfo(HttpSession session, int id) {
        return new ResultInfo<>(true,"team information",teamDAO.getTeamById(id));
    }

//    @Override
//    public ResultInfo<List<StoryMap>> getTeamStoryMaps(HttpSession session, int id) {
//        return new ResultInfo<>(true, "团队storyMap", teamDAO.getStoryMaps(id));
//    }



    @Override
    public ResultInfo<Object> addTeam(HttpSession session, TeamInfo teamInfo) {
        return new ResultInfo<>(true,"create team",teamDAO.save(teamInfo.toTeam()));
    }

    @Override
    public ResultInfo<Object> modifyTeamInfo(HttpSession session, TeamInfo teamInfo,int TeamID) {
        Team team = teamDAO.getTeamById(TeamID);
        team.setDescription(teamInfo.getDescription());
        team.setName(teamInfo.getName());
        return new ResultInfo<>(true,"modify team information", teamDAO.saveAndFlush(team));
    }

    @Override
    public ResultInfo<Object> addMember(String userName, String teamName) {
        Team team = teamDAO.getTeamByName(teamName);
        User user = accountDAO.getUserByName(userName);

        team.getUsers().add(user);
        teamDAO.saveAndFlush(team);

        return new ResultInfo<>(true, "success",userToInfo(team));
    }


    @Override
    public ResultInfo<Object> deleteMember(String userName, String teamName) {
        Team team = teamDAO.getTeamByName(teamName);
        User user = accountDAO.getUserByName(userName);

        team.getUsers().remove(user);
        teamDAO.saveAndFlush(team);

        return new ResultInfo<>(true, "success",userToInfo(team));
    }


    private static List<AccountInfo> userToInfo(Team team){
        List<AccountInfo> ifs = new ArrayList<>();
        for (User user1 : team.getUsers()) {
            AccountInfo info = new AccountInfo();
            info.setName(user1.getName());
            info.setEmail(user1.getEmail());
            ifs.add(info);
        }
        return ifs;
    }

}
