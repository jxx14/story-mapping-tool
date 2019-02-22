package nju.agilegroup.storymappingtool.model;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "activity_card")
public class ActivityCard {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "story_map_id")
    private int storyMapId;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private Timestamp createAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "position")
    private double position;

    public ActivityCard(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoryMapId() {
        return storyMapId;
    }

    public void setStoryMapId(int storyMapId) {
        this.storyMapId = storyMapId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
