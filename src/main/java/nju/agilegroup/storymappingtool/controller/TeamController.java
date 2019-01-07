package nju.agilegroup.storymappingtool.controller;

import nju.agilegroup.storymappingtool.model.StoryMap;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.service.TeamService;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    //参数id，查看团队信息
    @RequestMapping(value = "/getTeamInfo", method = RequestMethod.POST)
    public ResultInfo<Object> getTeamInfo(HttpServletRequest request, @RequestParam int teamID) {
        HttpSession session = request.getSession();
        return teamService.getTeamInfo(session,teamID);
    }

    //参数id，查看团队所拥有的storyMap
    @RequestMapping(value = "/getTeamStoryMaps", method = RequestMethod.POST)
    public ResultInfo<List<StoryMap>> getTeamStoryMaps(HttpServletRequest request, @RequestParam int teamID) {
        HttpSession session = request.getSession();
        return teamService.getTeamStoryMaps(session,teamID);
    }

    //参数id，查看团队成员
    @RequestMapping(value = "/getTeamMembers", method = RequestMethod.POST)
    public ResultInfo<List<User>> getTeamMember(HttpServletRequest request, @RequestParam int teamID) {
        HttpSession session = request.getSession();
        return teamService.getTeamMembers(session, teamID);
    }


    //参数团队信息，增加团队
    @RequestMapping(value = "/addTeam", method = RequestMethod.POST)
    public ResultInfo<Object> addTeam(HttpServletRequest request, @RequestBody TeamInfo teamInfo) {
        HttpSession session = request.getSession();
        return teamService.addTeam(session, teamInfo);
    }

    //参数团队信息，修改团队
    @RequestMapping(value = "/modifyTeamInfo", method = RequestMethod.POST)
    public ResultInfo<Object> modifyTeamInfo(HttpServletRequest request, @RequestBody TeamInfo teamInfo, @RequestParam int teamId) {
        HttpSession session = request.getSession();
        return teamService.modifyTeamInfo(session, teamInfo,teamId);
    }

}
