package nju.agilegroup.storymappingtool.controller;

import nju.agilegroup.storymappingtool.service.CardService;
import nju.agilegroup.storymappingtool.view.CardInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
public class CardController {
    @Autowired
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    /**
     * 获得一个地图的所有卡片，卡片以结构化的方式返回
     * 测试URL：http://localhost:8090/getMapCards?mapId=1
     * 按照层次结构返回了卡片列表
     * @param request
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/getMapCards", method = RequestMethod.GET)
    public ResultInfo<Object> getMap(HttpServletRequest request, @RequestParam int mapId){
        HttpSession session = request.getSession();
        return cardService.getCardsOfMap(session, mapId);
    }

    /**
     * 创建一个卡片
     * 输入json：cardInfo，需要填写的字段在CardInfo类中
     * @param request
     * @param cardInfo
     * @return
     */
    @RequestMapping(value = "/createActivity", method = RequestMethod.POST)
    public ResultInfo<Object> createActivityCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.createActivityCard(session, cardInfo);
    }

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ResultInfo<Object> createTaskCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.createTaskCard(session, cardInfo);
    }

    @RequestMapping(value = "/createStory", method = RequestMethod.POST)
    public ResultInfo<Object> createStoryCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.createStoryCard(session, cardInfo);
    }

    /**
     * 修改一张卡片，需要传入卡片ID
     * 这里createAt字段会刷新为修改时间，如果觉得没问题可以在界面上显示为最后修改于……因为不改的话可能需要多查一次表
     * @param request
     * @param cardInfo
     * @return
     */
    @RequestMapping(value = "/modifyActivity", method = RequestMethod.POST)
    public ResultInfo<Object> modifyActivityCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.modifyActivityCard(session, cardInfo);
    }

    @RequestMapping(value = "/modifyTask", method = RequestMethod.POST)
    public ResultInfo<Object> modifyTaskCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.modifyTaskCard(session, cardInfo);
    }

    @RequestMapping(value = "/modifyStory", method = RequestMethod.POST)
    public ResultInfo<Object> modifyStoryCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.modifyStoryCard(session, cardInfo);
    }

    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    public ResultInfo<Object> getRoles(HttpServletRequest request, @RequestParam int mapId){
        HttpSession session = request.getSession();
        return cardService.getRoles(session, mapId);
    }

    @RequestMapping(value = "/createRole", method = RequestMethod.POST)
    public ResultInfo<Object> createRole(HttpServletRequest request, @RequestBody RoleInfo roleInfo){
        HttpSession session = request.getSession();
        return cardService.createRole(session, roleInfo);
    }
}
