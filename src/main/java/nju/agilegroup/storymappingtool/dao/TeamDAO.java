package nju.agilegroup.storymappingtool.dao;

import nju.agilegroup.storymappingtool.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamDAO extends JpaRepository<Team, Integer> {

//    List<Team> findAllByLeader(int leader);//根据leader查询Team

    Team getTeamById(int id);//根据团队id查团队信息

    //查看用户参加的所有团队
    @Query(value = "SELECT t.* FROM  user u join team t WHERE u.team_id=t.id and u.email=?1",nativeQuery = true)
    List<Team>getTeamsByUserEmail (String email);
}
