package nju.agilegroup.storymappingtool.dao;


import nju.agilegroup.storymappingtool.model.StoryMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapDAO extends JpaRepository<StoryMap, Integer>{
    List<StoryMap> findByCreator(int userId);
}
