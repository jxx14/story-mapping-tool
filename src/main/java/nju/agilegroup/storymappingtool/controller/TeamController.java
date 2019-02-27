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
    @Autowired
    private TeamService teamService;
    @Autowired
    private AccountService accountService;

    //参数id，查看团队信息
    @RequestMapping(value = "/getTeamInfo")
    public ResultInfo<Object> getTeamInfo(HttpServletRequest request, @RequestParam int teamID) {
        HttpSession session = request.getSession();
        return teamService.getTeamInfo(session,teamID);
    }


    //参数团队信息，增加团队
    @RequestMapping(value = "/addTeam")
    public ResultInfo<Object> addTeam(HttpServletRequest request, @RequestBody TeamInfo teamInfo) {
        HttpSession session = request.getSession();
        return teamService.addTeam(session, teamInfo);
    }


    //参数id，查看团队成员
    @RequestMapping(value = "/getTeamMembers")
    public ResultInfo<Object> getTeamMembers(HttpServletRequest request, @RequestParam int teamID) {
        HttpSession session = request.getSession();
        return accountService.getTeamMembers(session, teamID);
    }


    //参数团队信息，修改团队
    @RequestMapping(value = "/modifyTeamInfo")
    public ResultInfo<Object> modifyTeamInfo(HttpServletRequest request, @RequestBody TeamInfo teamInfo, @RequestParam int teamId) {
        HttpSession session = request.getSession();
        return teamService.modifyTeamInfo(session, teamInfo,teamId);
    }


    //新增成员，data里最是新团队的成员列表
    @RequestMapping(value = "/addMember")
    public ResultInfo<Object> addMember(@RequestParam String userName,@RequestParam int teamID) {
        return teamService.addMember(userName,teamID);
    }

    //删除成员,data里是最新团队成员列表
    @RequestMapping(value = "/deleteMember")
    public ResultInfo<Object> deleteMember(@RequestParam String userName,@RequestParam int teamID) {
        return teamService.deleteMember(userName,teamID);
    }

}
