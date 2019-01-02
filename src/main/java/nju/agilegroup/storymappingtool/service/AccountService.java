package nju.agilegroup.storymappingtool.service;


import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;

import javax.servlet.http.HttpSession;

public interface AccountService {

    ResultInfo<Object> login(HttpSession session, AccountInfo account);

    ResultInfo<Object> logout(HttpSession session);
}
