package nju.agilegroup.storymappingtool.dao;


import nju.agilegroup.storymappingtool.model.StoryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StoryCardDAO extends JpaRepository<StoryCard, Integer>{
    @Query(value = "select * from story_card s where s.story_map_id = ?1", nativeQuery = true)
    List<StoryCard> findByStoryMapId(int mapId);

    @Modifying
    @Transactional
    @Query(value = "delete from story_card where task_id = ?1", nativeQuery = true)
    void deleteByTask(int taskId);

    @Modifying
    @Transactional
    @Query(value = "delete from story_card where releases = ?1 and story_map_id = ?2", nativeQuery = true)
    void deleteByRelease(int release, int mapId);

    @Modifying
    @Transactional
    @Query(value = "update story_card set releases = (releases-1) where releases > ?1 and story_map_id = ?2", nativeQuery = true)
    int updateRelease(int release, int mapId);

    @Modifying
    @Transactional
    @Query(value = "delete from story_card where story_map_id = ?1", nativeQuery = true)
    void deleteByMapId(int mapId);
}
