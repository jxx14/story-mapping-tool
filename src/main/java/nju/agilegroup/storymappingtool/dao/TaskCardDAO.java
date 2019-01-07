package nju.agilegroup.storymappingtool.dao;


import nju.agilegroup.storymappingtool.model.TaskCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskCardDAO extends JpaRepository<TaskCard, Integer> {
    @Query(value = "select * from task_card s where s.story_map_id = ?1", nativeQuery = true)
    List<TaskCard> findByStoryMapId(int mapId);
}
