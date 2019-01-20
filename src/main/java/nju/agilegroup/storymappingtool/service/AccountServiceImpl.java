package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.AccountDAO;
import nju.agilegroup.storymappingtool.dao.TeamDAO;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String USER_KEY = "USER_NAME";

    @Autowired
    private  AccountDAO accountDAO;
    @Autowired
    private TeamDAO teamDAO;


    @Override
    public ResultInfo<Object> login(HttpSession session, AccountInfo account) {
        if (session.getAttribute(USER_KEY) != null) {
            return new ResultInfo<>(true, "You are already logged in,no need to repeat login", Tool.userToInfo(accountDAO.getUserByEmail((String)session.getAttribute(USER_KEY))) );
        }

        String email = account.getEmail();
        String password = account.getPassword();
        List<User> accounts = accountDAO.findAllByEmailAndPassword(email, password);
        boolean isAccountValid = accounts != null && accounts.size() == 1;


        if (isAccountValid) {
            session.setAttribute(USER_KEY, email);
            return new ResultInfo<>(true, "Login successfully", Tool.userToInfo(accounts.get(0)) );
        } else{
            return new ResultInfo<>(false, "Account and password do not match", null);
        }
    }

    @Override
    public ResultInfo<Object> logout(HttpSession session) {
        session.invalidate();
        return new ResultInfo<>(true, "Logout successfully", null);
    }

    @Override
    public ResultInfo<Object> signUp(AccountInfo account) {
        String email = account.getEmail();
        String name = account.getName();
        List<User> usersGetByEmail = accountDAO.findAllByEmail(email);
        List<User> usersGetByName = accountDAO.findAllByName(name);
        if (!usersGetByEmail.isEmpty()) {
            return new ResultInfo<>(false, "The mailbox has been registered", null);
        }else if (!usersGetByName.isEmpty()) {
            return new ResultInfo<>(false, "Username already exists", null);
        }

        accountDAO.save(account.toUser());

        User user = accountDAO.getUserByEmail(email);

        Team team = new Team();
        team.setName(account.getName()+"'s team");
        team.setDescription(account.getName()+"'s team");
        team.setLeaderId(user.getId());
        teamDAO.saveAndFlush(team);

        team.getUsers().add(user);
        teamDAO.saveAndFlush(team);
        return new ResultInfo<>(true, "Registered successfully", null);


    }

    @Override
    public ResultInfo<Object> joinTeam(String userName, String teamName) {
        Team team = teamDAO.getTeamByName(teamName);
        User user = accountDAO.getUserByName(userName);

        user.getTeams().add(team);
        accountDAO.saveAndFlush(user);

        return new ResultInfo<>(true, "join team successfully",Tool.teamsToInfos(user.getTeams()));
    }


    @Override
    public ResultInfo<Object> leaveTeam(String userName, String teamName) {
        Team team = teamDAO.getTeamByName(teamName);
        User user = accountDAO.getUserByName(userName);
        user.getTeams().remove(team);
        accountDAO.saveAndFlush(user);

        return new ResultInfo<>(true, "leave team successfully", Tool.teamsToInfos(user.getTeams()));
    }

    @Override
    public ResultInfo<Object> getUserInfo(HttpSession session) {
        String email=(String)session.getAttribute(USER_KEY);
        User user = accountDAO.getUserByEmail(email);
        return new ResultInfo<>(true,"user information",Tool.userToInfo(user) );
    }

    @Override
    public ResultInfo<Object> modify(HttpSession session, AccountInfo account) {
        String email=(String)session.getAttribute(USER_KEY);
        User user = accountDAO.getUserByEmail(email);
        user.setEmail(account.getEmail());
        user.setName(account.getName());
        accountDAO.saveAndFlush(user);

        return new ResultInfo<>(true,"modify user information", Tool.userToInfo(user));
    }

    @Override
    public ResultInfo<Object> getTeamMembers(HttpSession session, int id) {
        Team team = teamDAO.getTeamById(id);
        return new ResultInfo<>(true, "teamMembers",Tool.usersToInfos(team));
    }

    @Override
    public ResultInfo<Object> getTeams(HttpSession session) {
        String email=(String)session.getAttribute(USER_KEY);
        User user =  accountDAO.getUserByEmail(email);
        Set<Team> teams = teamDAO.getTeamsByUserId(user.getId());

        return new ResultInfo<>(true, "teams",Tool.teamsToInfos(teams));
    }

}
