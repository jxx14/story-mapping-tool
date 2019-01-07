package nju.agilegroup.storymappingtool.dao;

import nju.agilegroup.storymappingtool.model.StoryMap;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamDAO extends JpaRepository<Team, Integer> {

//    List<Team> findAllByLeader(int leader);//根据leader查询Team

    Team getTeamById(int id);//根据团队id查团队信息

    //查看所有团队成员
    @Query(value = "SELECT * FROM  user u WHERE u.team_id =?1",nativeQuery = true)
    List<User> getTeamMember (int team_id);

    //查看团队的所有storymap
    @Query(value = "SELECT * FROM  storymap s WHERE s.team_id =?1",nativeQuery = true)
    List<StoryMap> getStoryMaps (int team_id);

}
