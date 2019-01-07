package nju.agilegroup.storymappingtool.dao;

import nju.agilegroup.storymappingtool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountDAO  extends JpaRepository<User, Integer> {

    List<User> findAllByEmailAndPassword(String email, String password);

    List<User> findAllByEmail(String email);

    //查看所有团队成员
    @Query(value = "SELECT * FROM  user u WHERE u.team_id =?1",nativeQuery = true)
    List<User> getTeamMember (int team_id);
}
