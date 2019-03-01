package nju.agilegroup.storymappingtool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {


    @RequestMapping(value = "/storymap")
    public String getStoryMapView(){
        return "storymap";
    }

    @RequestMapping(value = "/user")
    public String getUserView() {
        return "user";
    }

    @RequestMapping(value = "/map-list")
    public String getMapListView() {
        return "map_list";
    }

    @RequestMapping(value = "/map-create")
    public String getMapCreateView() {
        return "map_create";
    }

    @RequestMapping(value = "/team-create")
    public String getTeamCreateView(){
        return "team_create";
    }

    @RequestMapping(value = "/member-invite")
    public String getMemberInviteView(){
        return "member_invite";
    }

    @RequestMapping(value = "/member-remove")
    public String getMemberRemoveView(){
        return "member_remove";
    }

    @RequestMapping(value = "/login")
    public String getLoginView(){
        return "login";
    }

    @RequestMapping(value = "/register")
    public String getRegisterView(){
        return "register";
    }
}
