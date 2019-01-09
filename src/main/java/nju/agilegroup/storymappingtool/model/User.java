package nju.agilegroup.storymappingtool.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

//    @JoinColumn(name = "teamName")
//    private List<String> teamNames;

    @ManyToMany
    private Set<Team> teams;
//
//    @ManyToMany(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
//    @JoinTable(name = "Teacher_Student",joinColumns =
//            {@JoinColumn(name = "team_id", referencedColumnName = "teams")},inverseJoinColumns =
//            {@JoinColumn(name = "user_id", referencedColumnName ="id")})
//    private Set<Team> teams;



    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
