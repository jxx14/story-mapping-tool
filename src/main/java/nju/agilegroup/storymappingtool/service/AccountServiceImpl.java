package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.AccountDAO;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.view.AccountInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String USER_KEY = "USER_NAME";

    private final AccountDAO accountDAO;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public ResultInfo<Object> login(HttpSession session, AccountInfo account) {
        if (session.getAttribute(USER_KEY) != null) {
            return new ResultInfo<>(true, "您已经登录，无需再次登录", null);
        }

        String email = account.getEmail();
        String password = account.getPassword();
        List<User> accounts = accountDAO.findAllByEmailAndPassword(email, password);
        boolean isAccountValid = accounts != null && accounts.size() == 1;

        if (isAccountValid) {
            session.setAttribute(USER_KEY, email);
            return new ResultInfo<>(true, "登录成功", null);
        } else {
            return new ResultInfo<>(false, "账户名和密码不匹配", null);
        }
    }

    @Override
    public ResultInfo<Object> logout(HttpSession session) {
        session.invalidate();
        return new ResultInfo<>(true, "成功登出", null);
    }

    @Override
    public ResultInfo<Object> signUp(AccountInfo account) {
        String email = account.getEmail();
        List<User> users = accountDAO.findAllByEmail(email);
        if (!users.isEmpty()) {
            return new ResultInfo<>(false, "该邮箱已经被注册过", null);
        }

        accountDAO.save(account.toUser());
        return new ResultInfo<>(true, "成功注册账号", null);
    }

    @Override
    public ResultInfo<Object> getUserInfo(int id) {
        User user = accountDAO.getUserById(id);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setEmail(user.getEmail());
        accountInfo.setName(user.getName());
        return new ResultInfo<>(true,"查看用户信息",accountInfo );
    }

    @Override
    public ResultInfo<Object> modify(HttpSession session, AccountInfo account, int id) {
        User user = accountDAO.getUserById(id);
        user.setEmail(account.getEmail());
        user.setName(account.getName());
        return new ResultInfo<>(true,"修改用户信息", accountDAO.saveAndFlush(user));
    }

    @Override
    public ResultInfo<Object> resetPWd(HttpSession session, String password, int id) {
        return null;
    }

    @Override
    public ResultInfo<Object> getTeamMembers(HttpSession session, int id) {
        List<User> users = accountDAO.getTeamMember(id);
        for (User user : users) {
            System.out.println(user.toString());
        }
        List<AccountInfo> ifs = new ArrayList<>();

        for (User user : users) {
            AccountInfo info = new AccountInfo();
            info.setName(user.getName());
            info.setEmail(user.getEmail());
            ifs.add(info);
        }
        return new ResultInfo<>(true, "success", ifs);
    }
}
