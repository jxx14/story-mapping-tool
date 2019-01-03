package nju.agilegroup.storymappingtool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {


    @RequestMapping(value = "/storymap")
    public String getStoryMapView(){
        return "storymap";
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
