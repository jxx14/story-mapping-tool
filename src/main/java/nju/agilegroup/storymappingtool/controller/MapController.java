package nju.agilegroup.storymappingtool.controller;


import nju.agilegroup.storymappingtool.service.MapService;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class MapController {
    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService){
        this.mapService = mapService;
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public ResultInfo<Object> getMap(HttpServletRequest request, @RequestParam int userId){
        HttpSession session = request.getSession();
        return mapService.getMap(session, userId);
    }
}
