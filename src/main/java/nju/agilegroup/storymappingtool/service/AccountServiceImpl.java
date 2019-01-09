package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.AccountDAO;
import nju.agilegroup.storymappingtool.dao.TeamDAO;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import nju.agilegroup.storymappingtool.view.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String USER_KEY = "USER_NAME";

    private final AccountDAO accountDAO;
    private final TeamDAO teamDAO;
    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO, TeamDAO teamDAO) {
        this.accountDAO = accountDAO;
        this.teamDAO = teamDAO;
    }

    @Override
    public ResultInfo<Object> login(HttpSession session, AccountInfo account) {
        if (session.getAttribute(USER_KEY) != null) {
            return new ResultInfo<>(true, "You are already logged in,no need to repeat login", null);
        }

        String email = account.getEmail();
        String password = account.getPassword();
        List<User> accounts = accountDAO.findAllByEmailAndPassword(email, password);
        boolean isAccountValid = accounts != null && accounts.size() == 1;

        if (isAccountValid) {
            session.setAttribute(USER_KEY, email);
            return new ResultInfo<>(true, "Login successfully", null);
        } else {
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
        }if (!usersGetByName.isEmpty()) {
            return new ResultInfo<>(false, "Username already exists", null);
        }
        accountDAO.save(account.toUser());
        return new ResultInfo<>(true, "Registered successfully", null);
    }

    @Override
    public ResultInfo<Object> getUserInfo(HttpSession session) {
        String email=(String)session.getAttribute(USER_KEY);
        User user = accountDAO.getUserByEmail(email);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setEmail(email);
        accountInfo.setName(user.getName());
        return new ResultInfo<>(true,"user information",accountInfo );
    }

    @Override
    public ResultInfo<Object> modify(HttpSession session, AccountInfo account) {
        String email=(String)session.getAttribute(USER_KEY);
        User user = accountDAO.getUserByEmail(email);
        user.setEmail(account.getEmail());
        user.setName(account.getName());
        return new ResultInfo<>(true,"modify user information", accountDAO.saveAndFlush(user));
    }

    @Override
    public ResultInfo<Object> resetPWd(HttpSession session, String password, int id) {
        return null;
    }

    @Override
    public ResultInfo<Object> getTeamMembers(HttpSession session, int id) {
        List<User> users = accountDAO.getTeamMember(id);
        List<AccountInfo> ifs = new ArrayList<>();
        for (User user : users) {
            AccountInfo info = new AccountInfo();
            info.setName(user.getName());
            info.setEmail(user.getEmail());
            ifs.add(info);
        }
        return new ResultInfo<>(true, "success", ifs);
    }

    @Override
    public ResultInfo<Object> getTeams(HttpSession session) {
        String email=(String)session.getAttribute(USER_KEY);
        List<Team> teams = teamDAO.getTeamsByUserEmail(email);
        List<TeamInfo> ifs = new ArrayList<>();
        for (Team team : teams) {
            TeamInfo info = new TeamInfo();
            info.setName(team.getName());
            info.setDescription(team.getDescription());
            info.setLeader(team.getLeader_id());
            ifs.add(info);
        }
        return new ResultInfo<>(true, "success",ifs);
    }
}
