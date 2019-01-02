package nju.agilegroup.storymappingtool.model;


import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "storymap")
public class StoryMap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name = "create_at")
    private Timestamp createAt;

    // 后续进行外键关联，可以方便查询
    @Column(name = "creator_id")
    private int creator;

    @Column(name = "release_num")
    private int release;

    public StoryMap(){

    }

    public StoryMap(int id, String name, String description, Timestamp createAt, int creator, int release){
        this.id = id;
        this.name = name;
        this.description = description;
        this.createAt = createAt;
        this.creator = creator;
        this.release = release;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }
}