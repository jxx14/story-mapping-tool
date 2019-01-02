package nju.agilegroup.storymappingtool.dao;

import nju.agilegroup.storymappingtool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDAO  extends JpaRepository<User, Integer> {

    List<User> findAllByEmailAndPassword(String email, String password);

    List<User> findAllByEmail(String email);
}
