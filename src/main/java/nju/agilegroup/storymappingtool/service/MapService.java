package nju.agilegroup.storymappingtool.service;


import nju.agilegroup.storymappingtool.view.MapInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;

import javax.servlet.http.HttpSession;

public interface MapService {
    ResultInfo<Object> getMap(HttpSession session, int id);

    ResultInfo<Object> createMap(HttpSession session, MapInfo mapInfo);

    ResultInfo<Object> modifyMap(HttpSession session, MapInfo mapInfo);
}
