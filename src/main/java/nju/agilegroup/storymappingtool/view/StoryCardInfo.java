package nju.agilegroup.storymappingtool.view;


import nju.agilegroup.storymappingtool.model.StoryCard;

import java.util.Comparator;

public class StoryCardInfo {
    private int id;
    private int storyMapId;
    private String name;
    private String content;
    private String createAt;
    private String creator;
    private double position;

    private int status;
    private int worktime;
    private int release;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWorktime() {
        return worktime;
    }

    public void setWorktime(int worktime) {
        this.worktime = worktime;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public static class StoryComparator implements Comparator<StoryCard>{

        @Override
        public int compare(StoryCard o1, StoryCard o2) {
            if(o1.getPosition() > o2.getPosition()){
                return 1;
            }
            else{
                return -1;
            }
        }
    }
}
