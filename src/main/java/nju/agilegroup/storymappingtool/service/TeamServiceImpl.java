package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.AccountDAO;
import nju.agilegroup.storymappingtool.dao.TeamDAO;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

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
        Team team =teamDAO.getTeamById(id);

        TeamInfo info =new TeamInfo();
        info.setDescription(team.getDescription());
        info.setName(team.getName());
        return new ResultInfo<>(true,"team information",info);
    }

//    @Override
//    public ResultInfo<List<StoryMap>> getTeamStoryMaps(HttpSession session, int id) {
//        return new ResultInfo<>(true, "团队storyMap", teamDAO.getStoryMaps(id));
//    }



    @Override
    public ResultInfo<Object> addTeam(HttpSession session, TeamInfo teamInfo) {
        Team team = new Team();
        team.setDescription(teamInfo.getDescription());
        team.setName(teamInfo.getName());
        teamDAO.saveAndFlush(team);
        return new ResultInfo<>(true,"create team",Tool.teamToInfo(team));
    }

    @Override
    public ResultInfo<Object> modifyTeamInfo(HttpSession session, TeamInfo teamInfo,int TeamID) {
        Team team = teamDAO.getTeamById(TeamID);
        team.setDescription(teamInfo.getDescription());
        team.setName(teamInfo.getName());
        teamDAO.saveAndFlush(team);

        return new ResultInfo<>(true,"modify team information", Tool.teamToInfo(team));
    }

    @Override
    public ResultInfo<Object> addMember(String userName, String teamName) {
        Team team = teamDAO.getTeamByName(teamName);
        User user = accountDAO.getUserByName(userName);

        team.getUsers().add(user);
        teamDAO.saveAndFlush(team);

        return new ResultInfo<>(true, "success",Tool.usersToInfos(team));
    }


    @Override
    public ResultInfo<Object> deleteMember(String userName, String teamName) {
        Team team = teamDAO.getTeamByName(teamName);
        User user = accountDAO.getUserByName(userName);

        team.getUsers().remove(user);
        teamDAO.saveAndFlush(team);

        return new ResultInfo<>(true, "success",Tool.usersToInfos(team));
    }


}
