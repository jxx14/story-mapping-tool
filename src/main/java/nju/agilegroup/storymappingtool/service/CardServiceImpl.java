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

            List<Role> roles = roleDAO.findByActivtiyId(activityCard.getId());
            List<RoleInfo> roleInfos = rolesToInfo(roles);
            activityCardInfo.setRoles(roleInfos);

            // 对工作时长进行求和
            int activityWorktime = 0;
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

                    // 对工作时长进行求和
                    int taskWorktime = 0;
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
                            taskWorktime += storyInfo.getWorktime();
                        }
                    }

                    taskInfo.setStorys(storys);
                    taskInfo.setWorktime(taskWorktime);
                    tasks.add(taskInfo);
                    activityWorktime += taskWorktime;
                }
            }

            activityCardInfo.setTasks(tasks);
            activityCardInfo.setWorktime(activityWorktime);
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
        card = activityCardDAO.save(card);

        List<Role> roles = roleDAO.findByActivtiyId(card.getId());
        ActivityCardInfo activityCardInfo = activityCardToInfo(card, roles);

        return new ResultInfo<>(true, "success", activityCardInfo);
    }

    @Override
    public ResultInfo<Object> modifyActivityCard(HttpSession session, CardInfo cardInfo) {
        ActivityCard card = activityCardDAO.findOne(cardInfo.getId());
        if(cardInfo.getName() != null)
            card.setName(cardInfo.getName());
        //这里无法把已有值归0
        if(cardInfo.getPosition() != 0.0)
            card.setPosition(cardInfo.getPosition());

        card = activityCardDAO.save(card);

        //生成返回的信息
        List<Role> activityRoles = roleDAO.findByActivtiyId(card.getId());
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
        TaskCard card = taskCardDAO.findOne(cardInfo.getId());
        if(cardInfo.getName() != null)
            card.setName(cardInfo.getName());

        if(cardInfo.getPosition() != 0)
            card.setPosition(cardInfo.getPosition());

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
        StoryCard card = storyCardDAO.findOne(cardInfo.getId());
        if(cardInfo.getName() != null)
            card.setName(cardInfo.getName());
        if(cardInfo.getWorktime() != 0)
            card.setWorktime(cardInfo.getWorktime());
        if(cardInfo.getStatus() != 0)
            card.setStatus(cardInfo.getStatus());

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
            info.setId(roles.get(i).getId());
            info.setName(roles.get(i).getName());
            info.setAvatar(roles.get(i).getAvatar());
            info.setMapId(roles.get(i).getMapId());
            roleInfos.add(info);
        }

        return new ResultInfo<>(true, "success", roleInfos);
    }

    @Override
    public ResultInfo<Object> modifyRole(HttpSession session, RoleInfo roleInfo) {
        Role role = roleDAO.findOne(roleInfo.getId());
        if(roleInfo.getName() != null)
            role.setName(roleInfo.getName());

        if(roleInfo.getAvatar() != null)
            role.setAvatar(roleInfo.getAvatar());

        role = roleDAO.save(role);
        roleInfo.setId(role.getId());
        roleInfo.setMapId(role.getMapId());
        roleInfo.setName(role.getName());
        roleInfo.setAvatar(role.getAvatar());
        return new ResultInfo<>(true, "success", roleInfo);
    }

    @Override
    public ResultInfo<Object> addRoleToActivity(HttpSession session, RoleInfo roleInfo, int activiyId) {
        ActivityCard card = activityCardDAO.findOne(activiyId);

        if(card == null)
            return new ResultInfo<>(false, "fail", "活动不存在");
        Role role = new Role();
        role.setMapId(roleInfo.getMapId());
        role.setName(roleInfo.getName());
        role.setAvatar(roleInfo.getAvatar());
        role.setActivityId(activiyId);
        roleDAO.save(role);

        List<Role> roles = roleDAO.findByActivtiyId(activiyId);

        return new ResultInfo<>(true, "success", activityCardToInfo(card, roles));
    }

    @Override
    public ResultInfo<Object> removeRoleFromActivity(HttpSession session, RoleInfo roleInfo, int activiyId) {
        roleDAO.delete(roleInfo.getId());

        ActivityCard card = activityCardDAO.findOne(activiyId);
        List<Role> roles = roleDAO.findByActivtiyId(activiyId);
        return new ResultInfo<>(true, "success", activityCardToInfo(card, roles));
    }

    private static List<RoleInfo> rolesToInfo(List<Role> activityRoles){
        List<RoleInfo> roleInfos = new ArrayList<>();

        for(int i=0; i<activityRoles.size(); i++){
            Role role = activityRoles.get(i);
            RoleInfo roleInfo = new RoleInfo();
            roleInfo.setId(role.getId());
            roleInfo.setName(role.getName());
            roleInfo.setAvatar(role.getAvatar());
            roleInfo.setMapId(role.getMapId());
            roleInfos.add(roleInfo);
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

        List<RoleInfo> roleInfos = rolesToInfo(activityRoles);
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
