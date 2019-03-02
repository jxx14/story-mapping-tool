package nju.agilegroup.storymappingtool.dao;


import nju.agilegroup.storymappingtool.model.ActivityCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivityCardDAO extends JpaRepository<ActivityCard, Integer> {
    @Query(value = "select * from activity_card s where s.story_map_id = ?1", nativeQuery = true)
    List<ActivityCard> findByStoryMapId(int mapId);

    @Modifying
    @Transactional
    @Query(value = "delete from activity_card where story_map_id = ?1", nativeQuery = true)
    void deleteByMapId(int mapId);
}
