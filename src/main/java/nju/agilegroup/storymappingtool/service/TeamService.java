package nju.agilegroup.storymappingtool.service;
import nju.agilegroup.storymappingtool.model.StoryMap;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.TeamInfo;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TeamService {
    //参数id，查看团队信息
    ResultInfo<Object> getTeamInfo(HttpSession session, int id);

    //参数id，查看团队所拥有的storyMap
    ResultInfo<List<StoryMap>> getTeamStoryMaps(HttpSession session, int id);

    //参数id，查看团队成员
    ResultInfo<List<User>> getTeamMembers(HttpSession session, int id);

    //参数团队信息，增加团队
    ResultInfo<Object> addTeam(HttpSession session, TeamInfo teamInfo);

    //参数团队信息，修改团队
    ResultInfo<Object> modifyTeamInfo(HttpSession session, TeamInfo teamInfo,int teamID);
}