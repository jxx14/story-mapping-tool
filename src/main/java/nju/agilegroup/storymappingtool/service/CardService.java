package nju.agilegroup.storymappingtool.service;


import nju.agilegroup.storymappingtool.view.CardInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.RoleInfo;

import javax.servlet.http.HttpSession;

public interface CardService {
    ResultInfo<Object> getCardsOfMap(HttpSession session, int id);

    ResultInfo<Object> createActivityCard(HttpSession session, CardInfo cardInfo);

    ResultInfo<Object> modifyActivityCard(HttpSession session, CardInfo cardInfo);

    ResultInfo<Object> deleteActivityCard(HttpSession session, CardInfo cardInfo);

    ResultInfo<Object> createTaskCard(HttpSession session, CardInfo cardInfo);

    ResultInfo<Object> modifyTaskCard(HttpSession session, CardInfo cardInfo);

    ResultInfo<Object> deleteTaskCard(HttpSession session, CardInfo cardInfo);

    ResultInfo<Object> createStoryCard(HttpSession session, CardInfo cardInfo);

    ResultInfo<Object> modifyStoryCard(HttpSession session, CardInfo cardInfo);

    ResultInfo<Object> deleteStoryCard(HttpSession session, CardInfo cardInfo);

    ResultInfo<Object> getRoles(HttpSession session, int mapId);

    ResultInfo<Object> createRole(HttpSession session, RoleInfo roleInfo);

    ResultInfo<Object> modifyRole(HttpSession session, RoleInfo roleInfo);

    ResultInfo<Object> addRoleToActivity(HttpSession session, RoleInfo roleInfo, int activiyId);

    ResultInfo<Object> removeRoleFromActivity(HttpSession session, RoleInfo roleInfo, int activiyId);
}
