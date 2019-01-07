package nju.agilegroup.storymappingtool.dao;


import nju.agilegroup.storymappingtool.model.StoryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoryCardDAO extends JpaRepository<StoryCard, Integer>{
    @Query(value = "select * from story_card s where s.story_map_id = ?1", nativeQuery = true)
    List<StoryCard> findByStoryMapId(int mapId);

}
