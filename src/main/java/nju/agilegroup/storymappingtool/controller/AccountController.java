package nju.agilegroup.storymappingtool.controller;

import nju.agilegroup.storymappingtool.service.AccountService;
import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
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

    @RequestMapping(value = "/listUserInfo", method = RequestMethod.POST)
    public ResultInfo<Object> listUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return accountService.getUserInfo(session);
    }

    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST)
    public ResultInfo<Object> modifyUserInfo(HttpServletRequest request, @RequestBody AccountInfo accountInfo) {
        HttpSession session = request.getSession();
        return accountService.modify(session, accountInfo);
    }

    @RequestMapping(value = "/getTeams", method = RequestMethod.POST)
    public ResultInfo<Object> getTeams(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return accountService.getTeams(session);
    }

    //未实现
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public ResultInfo<Object> resetPwd(HttpServletRequest request,@RequestParam int userId) {
        HttpSession session = request.getSession();
        String password = request.getParameter("password");
        return accountService.resetPWd(session,password,userId);
    }

}