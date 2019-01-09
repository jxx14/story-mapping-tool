package nju.agilegroup.storymappingtool.dao;

import nju.agilegroup.storymappingtool.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamDAO extends JpaRepository<Team, Integer> {

//    List<Team> findAllByLeader(int leader);//根据leader查询Team

    Team getTeamById(int id);//根据团队id查团队信息

    //查看用户参加的所有团队
    @Query(value = "SELECT DISTINCT t.* FROM  user u join user_teams ut join team t WHERE ut.user_id =?1 and ut.teams_id =t.id\n",nativeQuery = true)
    List<Team>getTeamsByUserId(int user_id);

   Team getTeamByName(String name);

   List<Team> getTeamsByName(String name);
}
