package nju.agilegroup.storymappingtool.view;


public class MapInfo {
    //修改时只需要传入id和要修改的字段
    //创建传入时不填
    private int id;
    //必填
    private String name;
    //必填
    private String description;
    //不填
    private String creatorName;
    //必填
    private int creator;
    //必填
    //如果修改时要把release设为0，请传入-1
    private int release;
    //不填
    private String teamName;
    //必填
    private int team;
    //不填
    private String creatAt;


    public MapInfo() {
    }

    public MapInfo(int id, String name, String description, String creatorName, int creator, int release, String teamName, int team, String creatAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creatorName = creatorName;
        this.creator = creator;
        this.release = release;
        this.teamName = teamName;
        this.team = team;
        this.creatAt = creatAt;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public String getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(String creatAt) {
        this.creatAt = creatAt;
    }
}
