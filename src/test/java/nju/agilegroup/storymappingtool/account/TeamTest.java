package nju.agilegroup.storymappingtool.account;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TeamTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }


    //参数团队信息，增加团队(测试通过)
//    @Test
//    public void testAddTeam() throws Exception {
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("name", "a");
//        map1.put("leader", 2);
//        map1.put("description","小分队");
//        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/addTeam")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(JSONObject.toJSONString(map1)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String resultJson1 = result1.getResponse().getContentAsString();
//        JSONObject object1 = (JSONObject) JSONValue.parse(resultJson1);
//        Assert.assertTrue((Boolean) object1.get("success"));
//    }

    //参数id，查看团队信息
//    @Test
//    public void testGetTeamInfo() throws Exception {
//        Map<String, Object> map1 = new HashMap<>();
//        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/getTeamInfo")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .param("teamID","1"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        System.out.println("--------返回的json = " + result1);
//        String resultJson1 = result1.getResponse().getContentAsString();
//        JSONObject object1 = (JSONObject) JSONValue.parse(resultJson1);
//        Assert.assertTrue((Boolean) object1.get("success"));
//    }


    //参数id，查看团队所拥有的storyMap
//    @Test
//    public void testGetTeamStoryMaps() throws Exception {
//        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/getTeamStoryMaps")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .param("teamID","1"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//        System.out.println(result1);
//        String resultJson1 = result1.getResponse().getContentAsString();
//        JSONObject object1 = (JSONObject) JSONValue.parse(resultJson1);
//        Assert.assertTrue((Boolean) object1.get("success"));
//    }


    //参数id，查看团队成员
//    @Test
//    public void testGetTeamMembers() throws Exception {
//        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/getTeamMembers")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .param("teamID","1"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String resultJson1 = result1.getResponse().getContentAsString();
//        JSONObject object1 = (JSONObject) JSONValue.parse(resultJson1);
//        Assert.assertTrue((Boolean) object1.get("success"));
//    }


    //参数团队信息，修改团队
//    @Test
//    public void testModifyTeamInfo() throws Exception {
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("name", "abed");
//        map1.put("description","敏捷");
//        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/modifyTeamInfo")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .param("teamID","2")
//                .content(JSONObject.toJSONString(map1)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String resultJson1 = result1.getResponse().getContentAsString();
//        JSONObject object1 = (JSONObject) JSONValue.parse(resultJson1);
//        Assert.assertTrue((Boolean) object1.get("success"));
//    }


}
