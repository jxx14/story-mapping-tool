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

import static nju.agilegroup.storymappingtool.service.AccountServiceImpl.USER_KEY;

@Service
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private AccountDAO accountDAO;


    @Override
    public ResultInfo<Object> getTeamInfo(HttpSession session, int id) {
        Team team =teamDAO.getTeamById(id);

        TeamInfo info =new TeamInfo();
        info.setDescription(team.getDescription());
        info.setName(team.getName());
        return new ResultInfo<>(true,"team information",info);
    }


    @Override
    public ResultInfo<Object> addTeam(HttpSession session, TeamInfo teamInfo) {
        Team team = new Team();
        team.setDescription(teamInfo.getDescription());
        team.setName(teamInfo.getName());

        String email=(String)session.getAttribute(USER_KEY);
        User user = accountDAO.getUserByEmail(email);
        team.setLeaderId(user.getId());

        team.getUsers().add(user);
        teamDAO.saveAndFlush(team);
        return new ResultInfo<>(true,"create team",Tool.teamToInfo(team));
    }

    @Override
    public ResultInfo<Object> modifyTeamInfo(HttpSession session, TeamInfo teamInfo,int teamId) {
        Team team = teamDAO.getTeamById(teamId);
        team.setDescription(teamInfo.getDescription());
        team.setName(teamInfo.getName());
        teamDAO.saveAndFlush(team);

        return new ResultInfo<>(true,"modify team information", Tool.teamToInfo(team));
    }

    @Override
    public ResultInfo<Object> addMember(String userName, int teamID) {
        Team team = teamDAO.getTeamById(teamID);
        User user = accountDAO.getUserByName(userName);

        team.getUsers().add(user);
        teamDAO.saveAndFlush(team);

        return new ResultInfo<>(true, "success",Tool.usersToInfos(team.getUsers()));
    }


    @Override
    public ResultInfo<Object> deleteMember(String userName,int teamID) {
        Team team = teamDAO.getTeamById(teamID);
        User user = accountDAO.getUserByName(userName);

        team.getUsers().remove(user);
        teamDAO.saveAndFlush(team);

        return new ResultInfo<>(true, "success",Tool.usersToInfos(team.getUsers()));
    }

}
