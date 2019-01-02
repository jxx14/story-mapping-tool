package nju.agilegroup.storymappingtool.service;


import nju.agilegroup.storymappingtool.view.ResultInfo;

import javax.servlet.http.HttpSession;

public interface MapService {
    ResultInfo<Object> getMap(HttpSession session, int id);
}
