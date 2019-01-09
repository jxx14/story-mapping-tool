package nju.agilegroup.storymappingtool.controller;

import nju.agilegroup.storymappingtool.service.AccountService;
import nju.agilegroup.storymappingtool.service.TeamService;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@RestController
public class TeamController {
    private final TeamService teamService;
    private final AccountService accountService;

    @Autowired
    public TeamController(TeamService teamService, AccountService accountService) {
        this.teamService = teamService;
        this.accountService = accountService;
    }

    //参数id，查看团队信息
    @RequestMapping(value = "/getTeamInfo", method = RequestMethod.POST)
    public ResultInfo<Object> getTeamInfo(HttpServletRequest request, @RequestParam int teamID) {
        HttpSession session = request.getSession();
        return teamService.getTeamInfo(session,teamID);
    }

//    //参数id，查看团队所拥有的storyMap
//    @RequestMapping(value = "/getTeamStoryMaps", method = RequestMethod.POST)
//    public ResultInfo<Objecet> getTeamStoryMaps(HttpServletRequest request, @RequestParam int teamID) {
//        HttpSession session = request.getSession();
//        return teamService.getTeamStoryMaps(session,teamID);
//    }



    //参数团队信息，增加团队
    @RequestMapping(value = "/addTeam", method = RequestMethod.POST)
    public ResultInfo<Object> addTeam(HttpServletRequest request, @RequestBody TeamInfo teamInfo) {
        HttpSession session = request.getSession();
        return teamService.addTeam(session, teamInfo);
    }


    //参数id，查看团队成员
    @RequestMapping(value = "/getTeamMembers", method = RequestMethod.POST)
    public ResultInfo<Object> getTeamMembers(HttpServletRequest request, @RequestParam int teamID) {
        HttpSession session = request.getSession();
        return accountService.getTeamMembers(session, teamID);
    }


    //参数团队信息，修改团队
    @RequestMapping(value = "/modifyTeamInfo", method = RequestMethod.POST)
    public ResultInfo<Object> modifyTeamInfo(HttpServletRequest request, @RequestBody TeamInfo teamInfo, @RequestParam int teamId) {
        HttpSession session = request.getSession();
        return teamService.modifyTeamInfo(session, teamInfo,teamId);
    }


    //新增成员，返回团队的成员列表
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResultInfo<Object> addUser(@RequestParam String userName,@RequestParam String teamName) {
        return teamService.addUser(userName,teamName);
    }

}
