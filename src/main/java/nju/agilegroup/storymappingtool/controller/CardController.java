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
     * 返回类型：List<ActivityCardInfo>的json
     * @param request
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/getMapCards")
    public ResultInfo<Object> getMap(HttpServletRequest request, @RequestParam int mapId){
        HttpSession session = request.getSession();
        return cardService.getCardsOfMap(session, mapId);
    }

    /**
     * 创建一个卡片
     * 输入json：cardInfo，需要填写的字段在CardInfo类中
     * 返回对应卡片类型的CardInfo的json
     * PS：输入的CardInfo类只用来上传
     * @param request
     * @param cardInfo
     * @return
     */
    @RequestMapping(value = "/createActivity")
    public ResultInfo<Object> createActivityCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.createActivityCard(session, cardInfo);
    }

    @RequestMapping(value = "/createTask")
    public ResultInfo<Object> createTaskCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.createTaskCard(session, cardInfo);
    }

    @RequestMapping(value = "/createStory")
    public ResultInfo<Object> createStoryCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.createStoryCard(session, cardInfo);
    }

    /**
     * 修改一张卡片，需要传入卡片ID
     * 只需要传入修改部分的字段，其他多余字段无需填写
     * 返回对应卡片类型的CardInfo的json
     * @param request
     * @param cardInfo
     * @return
     */
    @RequestMapping(value = "/modifyActivity")
    public ResultInfo<Object> modifyActivityCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.modifyActivityCard(session, cardInfo);
    }

    @RequestMapping(value = "/modifyTask")
    public ResultInfo<Object> modifyTaskCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.modifyTaskCard(session, cardInfo);
    }

    @RequestMapping(value = "/modifyStory")
    public ResultInfo<Object> modifyStoryCard(HttpServletRequest request, @RequestBody CardInfo cardInfo){
        HttpSession session = request.getSession();
        return cardService.modifyStoryCard(session, cardInfo);
    }

    /**
     * 获取一个地图的所有角色
     * 返回List<RoleInfo>的json
     * @param request
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/getRoles")
    public ResultInfo<Object> getRoles(HttpServletRequest request, @RequestParam int mapId){
        HttpSession session = request.getSession();
        return cardService.getRoles(session, mapId);
    }

    /**
     * 修改已创建的role
     * 只需要填写id和要修改的字段
     * 返回修改后的roleInfo
     * @param request
     * @param roleInfo
     * @return
     */
    @RequestMapping(value = "/modifyRole")
    public ResultInfo<Object> modifyRole(HttpServletRequest request, @RequestBody RoleInfo roleInfo){
        HttpSession session = request.getSession();
        return cardService.modifyRole(session, roleInfo);
    }

    /**
     * 为一个activity添加role
     * url输入activityID，body输入roleID
     * 返回修改后这个activityCardInfo
     * @param request
     * @param roleInfo
     * @return
     */
    @RequestMapping(value = "/addRoleToActivity")
    public ResultInfo<Object> addRoleToActivity(HttpServletRequest request, @RequestParam int activity, @RequestBody RoleInfo roleInfo){
        HttpSession session = request.getSession();
        return cardService.addRoleToActivity(session, roleInfo, activity);
    }

    /**
     * 为一个activity删除role
     * url输入activityID，body输入roleID
     * 返回修改后这个activityCardInfo
     * @param request
     * @param roleInfo
     * @return
     */
    @RequestMapping(value = "/removeRoleFromActivity")
    public ResultInfo<Object> removeRoleFromActivity(HttpServletRequest request, @RequestParam int activity, @RequestBody RoleInfo roleInfo){
        HttpSession session = request.getSession();
        return cardService.removeRoleFromActivity(session, roleInfo, activity);
    }
}
