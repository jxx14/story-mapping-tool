package nju.agilegroup.storymappingtool.dao;


import nju.agilegroup.storymappingtool.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleDAO extends JpaRepository<Role, Integer>{
    @Query(value = "select * from role s where s.map_id = ?1", nativeQuery = true)
    List<Role> findByMapId(int mapId);
}
