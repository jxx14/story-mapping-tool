package nju.agilegroup.storymappingtool.dao;

import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.service.TeamService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountDAO  extends JpaRepository<User, Integer> {

    List<User> findAllByEmailAndPassword(String email, String password);

    List<User> findAllByEmail(String email);

    User getUserByEmail(String email);

    //查看所有团队成员
    @Query(value = "SELECT * FROM  user u WHERE u.team_id =?1",nativeQuery = true)
    List<User> getTeamMember (int team_id);

//    //查看用户参加的所有团队
//    @Query(value = "SELECT t.* FROM  user u join team t WHERE u.team_id=t.id and u.email=?1",nativeQuery = true)
//    List<Team> getTeams (String email);

}
