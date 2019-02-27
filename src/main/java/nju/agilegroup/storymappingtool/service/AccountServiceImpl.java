package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.AccountDAO;
import nju.agilegroup.storymappingtool.dao.TeamDAO;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    public static final String USER_KEY = "USER_NAME";

    @Autowired
    private  AccountDAO accountDAO;
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private JavaMailSender javaMailSender;


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

    @Override
    public ResultInfo<Object> sendMail(HttpSession session,String email) {
        User user = accountDAO.getUserByEmail(email);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("<html><head><title></title></head><body>");
        stringBuilder.append("亲爱的用户"+user.getName()+",点击下面的链接重新设置密码：");
        stringBuilder.append("http://localhost:8090/Password_reset\n");
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        //multipart模式
        try {
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);//收件人邮箱user.getMail()
            mimeMessageHelper.setFrom("jiangxiangxiang0@126.com");//发件人邮箱
            mimeMessage.setSubject("找回密码");
            //启用html
            mimeMessageHelper.setText(stringBuilder.toString(),true);
            javaMailSender.send(mimeMessage);
            session.setAttribute("email", email);
            return new ResultInfo<>(true," sent email successfully","");

        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResultInfo<>(true,"fail to sent email",null);
        }

    }

    @Override
    public ResultInfo<Object> resetPassword(HttpSession session,String password) {
        String email=(String)session.getAttribute("email");
        User user =  accountDAO.getUserByEmail(email);
        user.setPassword(password);
        accountDAO.saveAndFlush(user);
        System.out.println(user.getEmail());
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        session.invalidate();
        return new ResultInfo<>(true,"reset password successfully",null);
    }
}
