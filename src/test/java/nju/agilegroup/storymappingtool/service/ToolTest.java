package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.AccountDAO;
import nju.agilegroup.storymappingtool.dao.MapDAO;
import nju.agilegroup.storymappingtool.dao.TeamDAO;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ToolTest {
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private MapDAO mapDAO;


    @Test
    public void userToInfo() {
        User user =accountDAO.getUserById(1);
        Tool.userToInfo(user);
    }


    @Test
    public void teamToInfo() {
        Team team = teamDAO.getTeamById(1);
        Tool.teamToInfo(team);
    }

    @Test
    public void mapToInfos() {
        Tool.mapToInfos(mapDAO.findTeamMaps(1));
    }


//    @Test
//    public void teamsToInfos() {
//        User user =accountDAO.getUserById(1);
//        Tool.teamsToInfos(user.getTeams());
//    }
//
//
//    @Test
//    public void usersToInfos() {
//        Team team = teamDAO.getTeamById(1);
//        Tool.usersToInfos(team);
//    }

}