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
        return new ResultInfo<>(true,"团队信息",teamDAO.getTeamById(id));
    }

//    @Override
//    public ResultInfo<List<StoryMap>> getTeamStoryMaps(HttpSession session, int id) {
//        return new ResultInfo<>(true, "团队storyMap", teamDAO.getStoryMaps(id));
//    }



    @Override
    public ResultInfo<Object> addTeam(HttpSession session, TeamInfo teamInfo) {
        return new ResultInfo<>(true,"添加团队",teamDAO.save(teamInfo.toTeam()));
    }

    @Override
    public ResultInfo<Object> modifyTeamInfo(HttpSession session, TeamInfo teamInfo,int TeamID) {
        Team team = teamDAO.getTeamById(TeamID);
        team.setDescription(teamInfo.getDescription());
        team.setName(teamInfo.getName());
        return new ResultInfo<>(true,"修改团队", teamDAO.saveAndFlush(team));
    }
}
