package nju.agilegroup.storymappingtool.controller;


import nju.agilegroup.storymappingtool.service.MapService;
import nju.agilegroup.storymappingtool.view.MapInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class MapController {
    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService){
        this.mapService = mapService;
    }

    /**
     * 获得一个队伍的地图列表
     * 参数为teamId，目前团队功能还未开放，因此teamId=userId，后续将二者分离，大不了直接清库
     * 返回一个List<MapInfo>的JSON，具体可以直接访问getMaps?teamId=1（自己在storymap表插入对应Id的数据）
     * @param request
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/getMaps", method = RequestMethod.GET)
    public ResultInfo<Object> getMap(HttpServletRequest request, @RequestParam int teamId){
        HttpSession session = request.getSession();
        return mapService.getMap(session, teamId);
    }

    /**
     * 创建一个故事地图
     * 参数为通过POST方法上传的json，哪些值要填标记在了类的注释中
     * 失败时返回的是false
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/createMap", method = RequestMethod.POST)
    public ResultInfo<Object> createMap(HttpServletRequest request, @RequestBody MapInfo map){
        HttpSession session = request.getSession();
        return mapService.createMap(session, map);
    }

    /**
     * 修改故事地图
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/modifyMap", method = RequestMethod.POST)
    public ResultInfo<Object> modifyMap(HttpServletRequest request, @RequestBody MapInfo map){
        HttpSession session = request.getSession();
        return mapService.modifyMap(session, map);
    }

    @RequestMapping(value = "/deleteMap")
    public ResultInfo<Object> deleteMap(HttpServletRequest request, @RequestParam int mapId){
        HttpSession session = request.getSession();
        return mapService.deleteMap(session, mapId);

    }
}
