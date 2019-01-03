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
        System.out.println(account.getEmail());
        return accountService.signUp(account);
    }
}
