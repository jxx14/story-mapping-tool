package nju.agilegroup.storymappingtool.service;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.TeamInfo;

import javax.servlet.http.HttpSession;

public interface TeamService {
    //参数id，查看团队信息
    ResultInfo<Object> getTeamInfo(HttpSession session, int id);

    //参数团队信息，增加团队
    ResultInfo<Object> addTeam(HttpSession session, TeamInfo teamInfo);

    //参数团队信息，修改团队
    ResultInfo<Object> modifyTeamInfo(HttpSession session, TeamInfo teamInfo,int teamID);

    ResultInfo<Object> addMember(String userName, int teamID);

    ResultInfo<Object> deleteMember(String userName, int teamID);

}