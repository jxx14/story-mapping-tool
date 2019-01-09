package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.TeamDAO;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class TeamServiceImpl implements TeamService{

    private final TeamDAO teamDAO;

    @Autowired
       public TeamServiceImpl(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
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
}
