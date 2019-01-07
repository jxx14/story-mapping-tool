package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.MapDAO;
import nju.agilegroup.storymappingtool.model.StoryMap;
import nju.agilegroup.storymappingtool.view.MapInfo;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapServiceImpl implements MapService{
    private static final String USER_KEY = "USER_NAME";

    private final MapDAO mapDAO;

    @Autowired
    public MapServiceImpl(MapDAO mapDAO){
        this.mapDAO = mapDAO;
    }

    @Override
    public ResultInfo<Object> getMap(HttpSession session, int id) {
//        if(session.getAttribute(USER_KEY) == null){
//            return new ResultInfo<>(false, "您尚未登录，请登录后再试", null);
//        }

        List<StoryMap> maps = mapDAO.findByTeam(id);
        List<MapInfo> infos = new ArrayList<>();

        for(int i=0; i<maps.size(); i++){
            MapInfo info = new MapInfo();
            StoryMap map = maps.get(i);

            info.setId(map.getId());
            info.setName(map.getName());
            info.setCreator(map.getUser().getId());
            info.setCreatorName(map.getUser().getName());
            info.setDescription(map.getDescription());
            info.setRelease(map.getRelease());
            info.setTeam(map.getTeam().getId());
            info.setTeamName(map.getTeam().getName());
            infos.add(info);
        }


        return new ResultInfo<>(true, "success", infos);
    }

    @Override
    public ResultInfo<Object> createMap(HttpSession session, MapInfo mapInfo) {
        return null;
    }
}
