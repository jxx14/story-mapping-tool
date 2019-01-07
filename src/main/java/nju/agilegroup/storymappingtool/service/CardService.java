package nju.agilegroup.storymappingtool.service;


import nju.agilegroup.storymappingtool.view.CardInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;

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

    ResultInfo<Object> createRole(HttpSession session);

}
