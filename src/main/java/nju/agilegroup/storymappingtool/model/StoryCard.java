package nju.agilegroup.storymappingtool.model;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "story_card")
public class StoryCard {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "story_map_id")
    private int storyMapId;

    @Column(name = "task_id")
    private int taskId;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private Timestamp createAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "position")
    private double position;

    @Column(name = "releases")
    private int release;

    @Column(name = "worktime")
    private int worktime;

    @Column(name = "statu")
    private int status;

    public StoryCard(){

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

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public int getWorktime() {
        return worktime;
    }

    public void setWorktime(int worktime) {
        this.worktime = worktime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
