package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.AccountDAO;
import nju.agilegroup.storymappingtool.dao.MapDAO;
import nju.agilegroup.storymappingtool.dao.TeamDAO;
import nju.agilegroup.storymappingtool.model.StoryMap;
import nju.agilegroup.storymappingtool.model.Team;
import nju.agilegroup.storymappingtool.model.User;
import nju.agilegroup.storymappingtool.view.MapInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapServiceImpl implements MapService{
    private static final String USER_KEY = "USER_NAME";
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final MapDAO mapDAO;
    private final TeamDAO teamDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public MapServiceImpl(MapDAO mapDAO, TeamDAO teamDAO, AccountDAO accountDAO){
        this.mapDAO = mapDAO;
        this.teamDAO = teamDAO;
        this.accountDAO = accountDAO;
    }

    @Override
    public ResultInfo<Object> getMap(HttpSession session, int id) {
//        if(session.getAttribute(USER_KEY) == null){
//            return new ResultInfo<>(false, "您尚未登录，请登录后再试", null);
//        }

        List<StoryMap> maps = mapDAO.findByTeam(id);
        List<MapInfo> infos = new ArrayList<>();

        for(int i=0; i<maps.size(); i++){
            StoryMap map = maps.get(i);
            MapInfo info = mapToInfo(map);
            infos.add(info);
        }

        return new ResultInfo<>(true, "success", infos);
    }

    @Override
    public ResultInfo<Object> createMap(HttpSession session, MapInfo mapInfo) {
//        if(session.getAttribute(USER_KEY) == null){
//            return new ResultInfo<>(false, "您尚未登录，请登录后再试", null);
//        }

        StoryMap map = new StoryMap();
        // 获取外键的关联对象
        Team team = teamDAO.findOne(mapInfo.getTeam());
        User user = accountDAO.findOne(mapInfo.getCreator());
        map.setName(mapInfo.getName());
        map.setDescription(mapInfo.getDescription());
        map.setRelease(mapInfo.getRelease());
        map.setTeam(team);
        map.setUser(user);
        map.setCreateAt(new Timestamp(System.currentTimeMillis()));

        try{
            map = mapDAO.save(map);
            MapInfo info = mapToInfo(map);

            return new ResultInfo<>(true, "success", info);
        } catch (Exception e){
//            e.printStackTrace();
        }

        return new ResultInfo<>(false, "保存失败", "错误信息");
    }

    private static MapInfo mapToInfo(StoryMap map){
        MapInfo info = new MapInfo();
        String createTime = sdf.format(map.getCreateAt());

        info.setId(map.getId());
        info.setName(map.getName());
        info.setCreator(map.getUser().getId());
        info.setCreatorName(map.getUser().getName());
        info.setDescription(map.getDescription());
        info.setRelease(map.getRelease());
        info.setTeam(map.getTeam().getId());
        info.setTeamName(map.getTeam().getName());
        info.setCreatAt(createTime);
        return info;
    }
}
