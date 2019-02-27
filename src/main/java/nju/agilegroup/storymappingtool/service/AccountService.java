package nju.agilegroup.storymappingtool.service;


import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;

import javax.servlet.http.HttpSession;

public interface AccountService {

    ResultInfo<Object> login(HttpSession session, AccountInfo account);

    ResultInfo<Object> logout(HttpSession session);

    ResultInfo<Object> signUp(AccountInfo account);

    ResultInfo<Object> getUserInfo(HttpSession session);

    ResultInfo<Object> modify(HttpSession session,AccountInfo account);

    //参数id，查看团队成员
    ResultInfo<Object> getTeamMembers(HttpSession session, int id);

    ResultInfo<Object> getTeams(HttpSession session);

    ResultInfo<Object> joinTeam(String userName, String teamName);

    ResultInfo<Object> leaveTeam(String userName, String teamName);

    ResultInfo<Object> sendMail(HttpSession session,String email);

    ResultInfo<Object> resetPassword(HttpSession session,String password);
}
