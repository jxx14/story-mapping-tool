package nju.agilegroup.storymappingtool.dao;


import nju.agilegroup.storymappingtool.model.ActivityCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityCardDAO extends JpaRepository<ActivityCard, Integer> {
    @Query(value = "select * from activity_card s where s.story_map_id = ?1", nativeQuery = true)
    List<ActivityCard> findByStoryMapId(int mapId);
}
