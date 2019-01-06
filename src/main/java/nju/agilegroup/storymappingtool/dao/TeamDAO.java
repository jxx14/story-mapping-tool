package nju.agilegroup.storymappingtool.dao;


import nju.agilegroup.storymappingtool.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamDAO extends JpaRepository<Team, Integer> {
}
