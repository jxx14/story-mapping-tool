package nju.agilegroup.storymappingtool.dao;


import nju.agilegroup.storymappingtool.model.StoryMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface MapDAO extends JpaRepository<StoryMap, Integer>{
    @Query(value = "select * from storymap s where s.team_id = ?1", nativeQuery = true)
    List<StoryMap> findByTeam(int teamId);

    @Query(value = "select * from storymap s where s.team_id = ?1", nativeQuery = true)
    Set<StoryMap> findTeamMaps(int teamId);


}
