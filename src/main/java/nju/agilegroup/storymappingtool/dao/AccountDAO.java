package nju.agilegroup.storymappingtool.dao;

import nju.agilegroup.storymappingtool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountDAO  extends JpaRepository<User, Integer> {

    List<User> findAllByEmailAndPassword(String email, String password);

    List<User> findAllByEmail(String email);

    List<User> findAllByName(String name);

    User getUserByEmail(String email);

    User getUserByName(String name);

    //查看所有团队成员
    @Query(value = "SELECT u.* FROM  user u Join team_users tu WHERE tu.teams_id=?1 AND u.id=tu.users_id",nativeQuery = true)
    List<User> getTeamMembers (int team_id);

}
