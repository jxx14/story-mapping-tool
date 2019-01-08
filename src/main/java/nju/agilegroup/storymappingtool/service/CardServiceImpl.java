package nju.agilegroup.storymappingtool.service;


import nju.agilegroup.storymappingtool.dao.*;
import nju.agilegroup.storymappingtool.model.*;
import nju.agilegroup.storymappingtool.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService{
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final ActivityCardDAO activityCardDAO;
    private final TaskCardDAO taskCardDAO;
    private final StoryCardDAO storyCardDAO;
    private final RoleDAO roleDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public CardServiceImpl(ActivityCardDAO activityCardDAO, TaskCardDAO taskCardDAO, StoryCardDAO storyCardDAO, RoleDAO roleDAO, AccountDAO accountDAO){
        this.activityCardDAO = activityCardDAO;
        this.taskCardDAO = taskCardDAO;
        this.storyCardDAO = storyCardDAO;
        this.roleDAO = roleDAO;
        this.accountDAO = accountDAO;
    }

    @Override
    public ResultInfo<Object> getCardsOfMap(HttpSession session, int id) {
        //查找这个地图的所有卡片和角色
        List<ActivityCard> activityCards = activityCardDAO.findByStoryMapId(id);
        List<TaskCard> taskCards = taskCardDAO.findByStoryMapId(id);
        List<StoryCard> storyCards = storyCardDAO.findByStoryMapId(id);
        List<Role> activityRoles = roleDAO.findByMapId(id);

        //按照position排序，这样后面遍历就会自动按照position进行排序，无需特别处理
        activityCards.sort(new ActivityCardInfo.ActivityComparator());
        taskCards.sort(new TaskCardInfo.TaskComparator());
        storyCards.sort(new StoryCardInfo.StoryComparator());

        List<ActivityCardInfo> cards = new ArrayList<>();

        for(int a=0; a<activityCards.size(); a++){
            ActivityCard activityCard = activityCards.get(a);
            ActivityCardInfo activityCardInfo = new ActivityCardInfo();
            activityCardInfo.setId(activityCard.getId());
            activityCardInfo.setName(activityCard.getName());
            activityCardInfo.setContent(activityCard.getContent());
            activityCardInfo.setStoryMapId(activityCard.getStoryMapId());
            activityCardInfo.setCreator(activityCard.getCreator().getName());
            activityCardInfo.setCreateAt(sdf.format(activityCard.getCreateAt()));
            activityCardInfo.setPosition(activityCard.getPosition());

            //将role对应到activityCard上，数据库中使用字符串

            //没有设置roles时的处理
            if(activityCard.getRoles() == null)
                activityCard.setRoles("");

            List<RoleInfo> roles = rolesToInfo(activityCard.getRoles(), activityRoles);
            activityCardInfo.setRoles(roles);

            //设置对应的TaskCard
            List<TaskCardInfo> tasks = new ArrayList<>();
            for(int t=0; t<taskCards.size(); t++){
                TaskCard task = taskCards.get(t);
                if(task.getActivityId() == activityCard.getId()){
                    TaskCardInfo taskInfo = new TaskCardInfo();
                    taskInfo.setId(task.getId());
                    taskInfo.setName(task.getName());
                    taskInfo.setContent(task.getContent());
                    taskInfo.setCreator(task.getCreator().getName());
                    taskInfo.setStoryMapId(task.getStoryMapId());
                    taskInfo.setPosition(task.getPosition());
                    taskInfo.setCreateAt(sdf.format(task.getCreateAt()));

                    List<StoryCardInfo> storys = new ArrayList<>();
                    for(int s=0; s<storyCards.size(); s++){
                        StoryCard story = storyCards.get(s);
                        if(story.getTaskId() == task.getId()){
                            StoryCardInfo storyInfo = new StoryCardInfo();
                            storyInfo.setId(story.getId());
                            storyInfo.setStoryMapId(story.getStoryMapId());
                            storyInfo.setName(story.getName());
                            storyInfo.setContent(story.getContent());
                            storyInfo.setCreator(story.getCreator().getName());
                            storyInfo.setCreateAt(sdf.format(story.getCreateAt()));
                            storyInfo.setPosition(story.getPosition());
                            storyInfo.setRelease(story.getRelease());
                            storyInfo.setStatus(story.getStatus());
                            story.setWorktime(story.getWorktime());
                            storys.add(storyInfo);
                        }
                    }

                    taskInfo.setStorys(storys);
                    tasks.add(taskInfo);
                }
            }

            activityCardInfo.setTasks(tasks);
            cards.add(activityCardInfo);
        }

        return new ResultInfo<>(true, "success", cards);
    }

    @Override
    public ResultInfo<Object> createActivityCard(HttpSession session, CardInfo cardInfo) {
        ActivityCard card = new ActivityCard();
        card.setName(cardInfo.getName());
        card.setStoryMapId(cardInfo.getMapId());
        card.setContent(cardInfo.getContent());
        User creator = accountDAO.findOne(cardInfo.getCreatorId());
        card.setCreator(creator);
        card.setPosition(cardInfo.getPosition());
        card.setCreateAt(new Timestamp(System.currentTimeMillis()));

        List<String> roles = cardInfo.getRoles();
        String roleString = "";
        if(roles != null)
            for(int i=0; i<roles.size(); i++){
                roleString += roles.get(i) + " ";
            }
        card.setRoles(roleString);

        card = activityCardDAO.save(card);

        //生成返回的信息
        List<Role> activityRoles = roleDAO.findByMapId(cardInfo.getMapId());
        ActivityCardInfo activityCardInfo = activityCardToInfo(card, activityRoles);

        return new ResultInfo<>(true, "success", activityCardInfo);
    }

    @Override
    public ResultInfo<Object> modifyActivityCard(HttpSession session, CardInfo cardInfo) {
        ActivityCard card = new ActivityCard();
        card.setId(cardInfo.getId());
        card.setName(cardInfo.getName());
        card.setStoryMapId(cardInfo.getMapId());
        card.setContent(cardInfo.getContent());
        User creator = accountDAO.findOne(cardInfo.getCreatorId());
        card.setCreator(creator);
        card.setPosition(cardInfo.getPosition());
        card.setCreateAt(new Timestamp(System.currentTimeMillis()));

        List<String> roles = cardInfo.getRoles();
        String roleString = "";
        if(roles != null)
            for(int i=0; i<roles.size(); i++){
                roleString += roles.get(i) + " ";
            }
        card.setRoles(roleString);

        card = activityCardDAO.save(card);

        //生成返回的信息
        List<Role> activityRoles = roleDAO.findByMapId(cardInfo.getMapId());
        ActivityCardInfo activityCardInfo = activityCardToInfo(card, activityRoles);
        return new ResultInfo<>(true, "success", activityCardInfo);
    }

    @Override
    public ResultInfo<Object> deleteActivityCard(HttpSession session, CardInfo cardInfo) {
        return null;
    }

    @Override
    public ResultInfo<Object> createTaskCard(HttpSession session, CardInfo cardInfo) {
        TaskCard card = new TaskCard();
        card.setName(cardInfo.getName());
        card.setContent(cardInfo.getContent());
        card.setStoryMapId(cardInfo.getMapId());
        card.setPosition(cardInfo.getPosition());
        card.setCreateAt(new Timestamp(System.currentTimeMillis()));
        User creator = accountDAO.findOne(cardInfo.getCreatorId());
        card.setCreator(creator);
        card.setActivityId(cardInfo.getParent());

        card = taskCardDAO.save(card);

        TaskCardInfo taskCardInfo = taskCardToInfo(card);

        return new  ResultInfo<>(true, "success", taskCardInfo);
    }

    @Override
    public ResultInfo<Object> modifyTaskCard(HttpSession session, CardInfo cardInfo) {
        TaskCard card = new TaskCard();
        card.setId(cardInfo.getId());
        card.setName(cardInfo.getName());
        card.setContent(cardInfo.getContent());
        card.setStoryMapId(cardInfo.getMapId());
        card.setPosition(cardInfo.getPosition());
        card.setCreateAt(new Timestamp(System.currentTimeMillis()));
        User creator = accountDAO.findOne(cardInfo.getCreatorId());
        card.setCreator(creator);
        card.setActivityId(cardInfo.getParent());

        card = taskCardDAO.save(card);

        TaskCardInfo taskCardInfo = taskCardToInfo(card);

        return new  ResultInfo<>(true, "success", taskCardInfo);
    }

    @Override
    public ResultInfo<Object> deleteTaskCard(HttpSession session, CardInfo cardInfo) {
        return null;
    }

    @Override
    public ResultInfo<Object> createStoryCard(HttpSession session, CardInfo cardInfo) {
        StoryCard card = new StoryCard();
        card.setName(cardInfo.getName());
        card.setContent(cardInfo.getContent());
        card.setTaskId(cardInfo.getParent());
        card.setWorktime(cardInfo.getWorktime());
        card.setCreateAt(new Timestamp(System.currentTimeMillis()));
        card.setRelease(cardInfo.getRelease());
        card.setStatus(cardInfo.getStatus());
        User creator = accountDAO.findOne(cardInfo.getCreatorId());
        card.setCreator(creator);
        card.setPosition(cardInfo.getPosition());
        card.setStoryMapId(cardInfo.getMapId());

        card = storyCardDAO.save(card);
        StoryCardInfo storyCardInfo = storyCardToInfo(card);

        return new  ResultInfo<>(true, "success", storyCardInfo);
    }

    @Override
    public ResultInfo<Object> modifyStoryCard(HttpSession session, CardInfo cardInfo) {
        StoryCard card = new StoryCard();
        card.setId(cardInfo.getId());
        card.setName(cardInfo.getName());
        card.setContent(cardInfo.getContent());
        card.setTaskId(cardInfo.getParent());
        card.setWorktime(cardInfo.getWorktime());
        card.setCreateAt(new Timestamp(System.currentTimeMillis()));
        card.setRelease(cardInfo.getRelease());
        card.setStatus(cardInfo.getStatus());
        User creator = accountDAO.findOne(cardInfo.getCreatorId());
        card.setCreator(creator);
        card.setPosition(cardInfo.getPosition());
        card.setStoryMapId(cardInfo.getMapId());

        card = storyCardDAO.save(card);
        StoryCardInfo storyCardInfo = storyCardToInfo(card);

        return new  ResultInfo<>(true, "success", storyCardInfo);
    }

    @Override
    public ResultInfo<Object> deleteStoryCard(HttpSession session, CardInfo cardInfo) {
        return null;
    }

    @Override
    public ResultInfo<Object> getRoles(HttpSession session, int mapId) {
        List<Role> roles = roleDAO.findByMapId(mapId);
        List<RoleInfo> roleInfos = new ArrayList<>();

        for(int i=0; i<roles.size(); i++){
            RoleInfo info = new RoleInfo();
            info.setName(roles.get(i).getPk().getName());
            info.setAvatar(roles.get(i).getAvatar());
            info.setMapId(roles.get(i).getPk().getMapId());
            roleInfos.add(info);
        }

        return new ResultInfo<>(true, "success", roleInfos);
    }

    @Override
    public ResultInfo<Object> createRole(HttpSession session, RoleInfo roleInfo) {
        List<Role> roles = roleDAO.findByMapId(roleInfo.getMapId());

        for(int i=0; i<roles.size(); i++){
            if(roles.get(i).getPk().getName().equals(roleInfo.getName()))
                return new ResultInfo<>(false, "fail", "名称已存在，请使用其他角色名");
        }
        Role role = new Role();
        role.setPk(new RolePK(roleInfo.getMapId(), roleInfo.getName()));
        role.setAvatar(roleInfo.getAvatar());

        role = roleDAO.save(role);
        roleInfo.setMapId(role.getPk().getMapId());
        roleInfo.setName(role.getPk().getName());
        roleInfo.setAvatar(role.getAvatar());
        return new ResultInfo<>(true, "success", roleInfo);
    }

    private static List<RoleInfo> rolesToInfo(String roles, List<Role> activityRoles){
        List<RoleInfo> roleInfos = new ArrayList<>();
        String[] roleList = roles.split(" ");

        for(int r=0; r<roleList.length; r++){
            String roleName = roleList[r];
            for(int i=0; i<activityRoles.size(); i++){
                Role role = activityRoles.get(i);
                if(roleName.equals(activityRoles.get(i).getPk().getName())){
                    RoleInfo roleInfo = new RoleInfo();
                    roleInfo.setName(role.getPk().getName());
                    roleInfo.setAvatar(role.getAvatar());
                    roleInfo.setMapId(role.getPk().getMapId());
                    roleInfos.add(roleInfo);
                    break;
                }
            }
        }
        return roleInfos;
    }

    private static ActivityCardInfo activityCardToInfo(ActivityCard card, List<Role> activityRoles){
        ActivityCardInfo activityCardInfo = new ActivityCardInfo();
        activityCardInfo.setId(card.getId());
        activityCardInfo.setName(card.getName());
        activityCardInfo.setContent(card.getContent());
        activityCardInfo.setStoryMapId(card.getStoryMapId());
        activityCardInfo.setCreator(card.getCreator().getName());
        activityCardInfo.setCreateAt(sdf.format(card.getCreateAt()));
        activityCardInfo.setPosition(card.getPosition());

        //将role对应到activityCard上，数据库中使用字符串
        //没有设置roles时的处理
        if(card.getRoles() == null)
            card.setRoles("");

        List<RoleInfo> roleInfos = rolesToInfo(card.getRoles(), activityRoles);
        activityCardInfo.setRoles(roleInfos);

        return activityCardInfo;
    }

    private static TaskCardInfo taskCardToInfo(TaskCard card){
        TaskCardInfo taskCardInfo = new TaskCardInfo();
        taskCardInfo.setId(card.getId());
        taskCardInfo.setName(card.getName());
        taskCardInfo.setCreateAt(sdf.format(card.getCreateAt()));
        taskCardInfo.setContent(card.getContent());
        taskCardInfo.setStoryMapId(card.getStoryMapId());
        taskCardInfo.setPosition(card.getPosition());
        taskCardInfo.setCreator(card.getCreator().getName());

        return taskCardInfo;
    }

    private static StoryCardInfo storyCardToInfo(StoryCard card){
        StoryCardInfo storyCardInfo = new StoryCardInfo();
        storyCardInfo.setId(card.getId());
        storyCardInfo.setName(card.getName());
        storyCardInfo.setContent(card.getContent());
        storyCardInfo.setStoryMapId(card.getStoryMapId());
        storyCardInfo.setStatus(card.getStatus());
        storyCardInfo.setRelease(card.getRelease());
        storyCardInfo.setPosition(card.getPosition());
        storyCardInfo.setCreateAt(sdf.format(card.getCreateAt()));
        storyCardInfo.setCreator(card.getCreator().getName());
        storyCardInfo.setWorktime(card.getWorktime());

        return storyCardInfo;
    }
}
