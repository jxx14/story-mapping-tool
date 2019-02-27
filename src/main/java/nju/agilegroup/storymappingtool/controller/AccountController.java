package nju.agilegroup.storymappingtool.controller;

import nju.agilegroup.storymappingtool.service.AccountService;
import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AccountController {
    @Autowired
    private  AccountService accountService;

    @RequestMapping(value = "/user/login")
    public ResultInfo<Object> login(HttpServletRequest request, @RequestBody AccountInfo account) {
        HttpSession session = request.getSession();
        return accountService.login(session, account);

    }

    @RequestMapping(value = "/logout")
    public ResultInfo<Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return accountService.logout(session);
    }

    @RequestMapping(value = "/signUp")
    public ResultInfo<Object> signUp(@RequestBody AccountInfo account) {
        return accountService.signUp(account);
    }

    @RequestMapping(value = "/listUserInfo")
    public ResultInfo<Object> listUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return accountService.getUserInfo(session);
    }

    @RequestMapping(value = "/modifyUserInfo")
    public ResultInfo<Object> modifyUserInfo(HttpServletRequest request, @RequestBody AccountInfo accountInfo) {
        HttpSession session = request.getSession();
        return accountService.modify(session, accountInfo);
    }

    @RequestMapping(value = "/getTeams")
    public ResultInfo<Object> getTeams(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return accountService.getTeams(session);
    }

    //加入团队，返回值data里是所加入的所有团队
    @RequestMapping(value = "/joinTeam")
    public ResultInfo<Object> joinTeam(@RequestParam String userName,@RequestParam String teamName) {
        return accountService.joinTeam(userName,teamName);
    }


    //离开团队
    @RequestMapping(value = "/leaveTeam")
    public ResultInfo<Object> leaveTeam(@RequestParam String userName,@RequestParam String teamName) {
        return accountService.leaveTeam(userName,teamName);
    }


    //找回密码，发送验证邮件到用户邮箱
    @RequestMapping("/sendMail")
    public ResultInfo<Object> mail(HttpSession session,@RequestParam String mail) {
        return accountService.sendMail(session,mail);
    }

    //重设密码，用户在邮箱中接受验证，完成找回密码
    @RequestMapping("/resetPassword")
    public ResultInfo<Object> resetPassword(HttpSession session,@RequestParam String password) {
        return accountService.resetPassword(session,password);
    }

}