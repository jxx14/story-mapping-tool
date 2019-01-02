package nju.agilegroup.storymappingtool.service;

import nju.agilegroup.storymappingtool.dao.MapDAO;
import nju.agilegroup.storymappingtool.model.StoryMap;
import nju.agilegroup.storymappingtool.view.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2019/1/2.
 */
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

        List<StoryMap> maps = mapDAO.findByCreator(id);
        String data = "";
        for(int i=0; i<maps.size(); i++){
            data = data + maps.get(i).getName() + ";";
        }
        return new ResultInfo<>(false, "您尚未登录，请登录后再试", data);
    }
}
