package nju.agilegroup.storymappingtool.view;


import nju.agilegroup.storymappingtool.model.ActivityCard;

import java.util.Comparator;
import java.util.List;

public class ActivityCardInfo {
    private int id;
    private int storyMapId;
    private String name;
    private String content;
    private String createAt;
    private String creator;
    private double position;
    private List<RoleInfo> roles;

    private List<TaskCardInfo> tasks;

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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public List<RoleInfo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleInfo> roles) {
        this.roles = roles;
    }

    public List<TaskCardInfo> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskCardInfo> tasks) {
        this.tasks = tasks;
    }


    public static class ActivityComparator implements Comparator<ActivityCard>{

        @Override
        public int compare(ActivityCard o1, ActivityCard o2) {
            if(o1.getPosition() > o2.getPosition()){
                return 1;
            }
            else{
                return -1;
            }
        }
    }
}
