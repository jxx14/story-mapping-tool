package nju.agilegroup.storymappingtool.view;


import java.util.List;

public class CardInfo {
    // 创建时不用填，修改已有项时需要填
    private int id;
    // 必填
    private String name;
    // 必填
    private String content;
    // 必填
    private int creatorId;
    // 必填
    private int mapId;
    // 必填，小的在前大的在后
    private double position;
    // 卡片类型为task和story时需要填写父节点的ID
    private int parent;

    //这三个卡片类型为storyCard时需要填
    private int status;
    private int worktime;
    private int release;

    //创建时输入角色名称的列表即可，activity以外的卡片请将该字段设为roles:[]，否则可能会报错
    //该字段现已废弃
    private List<Integer> roles;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
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

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}
