package nju.agilegroup.storymappingtool.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "leader_id")
    private int leader_id;

    @Column(name = "description")
    private String description;


//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "teams")
//    private Set<User> users;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<User> users = new HashSet<>();


    public Team(){}

    public Team(String name, int leader_id, String description) {
        this.name = name;
        this.leader_id = leader_id;
        this.description = description;
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

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader(int leader_id) {
        this.leader_id = leader_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", leader_id=" + leader_id +
                ", description='" + description + '\'' +
                '}';
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
