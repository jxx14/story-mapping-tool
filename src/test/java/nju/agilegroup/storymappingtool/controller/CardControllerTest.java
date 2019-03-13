package nju.agilegroup.storymappingtool.controller;

import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CardControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }


    @Test
    public void getMap() throws Exception{
           MvcResult result = mockMvc.perform(post("/getMapCards?mapId=1"))
                   .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void createActivityCard() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "test");
        map1.put("content", "");
        map1.put("creatorId", 1);
        map1.put("mapId", 40);
        map1.put("position", 100);
        MvcResult result = mockMvc.perform(post("/createActivity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void createTaskCard() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "test");
        map1.put("content", "");
        map1.put("creatorId", 1);
        map1.put("mapId", 40);
        map1.put("position", 100);
        map1.put("parent", 117);
        MvcResult result = mockMvc.perform(post("/createTask")
               .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(JSONObject.toJSONString(map1)))
               .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void createStoryCard() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "test");
        map1.put("content", "");
        map1.put("creatorId", 1);
        map1.put("mapId", 40);
        map1.put("position", 100);
        map1.put("parent", 146);
        map1.put("worktime", 5);
        map1.put("status", 0);
        map1.put("release", 1);
        MvcResult result = mockMvc.perform(post("/createStory")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

   @Test
    public void modifyActivityCard() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 117);
        map1.put("name", "test");
        map1.put("content", "");
        map1.put("creatorId", 1);
        map1.put("mapId", 10);
        map1.put("position", 100);
        MvcResult result = mockMvc.perform(post("/modifyActivity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andReturn();
       System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void modifyTaskCard() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 146);
        map1.put("name", "test");
        map1.put("content", "");
        map1.put("creatorId", 1);
        map1.put("mapId", 10);
        map1.put("position", 100);
        map1.put("parent", 30);
        MvcResult result = mockMvc.perform(post("/modifyTask")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void modifyStoryCard() throws Exception {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 101);
        map1.put("name", "test");
        map1.put("content", "");
        map1.put("creatorId", 1);
        map1.put("mapId", 10);
        map1.put("position", 100);
        map1.put("parent", 29);
        map1.put("worktime", 5);
        map1.put("status", 0);
        map1.put("release", 1);
        MvcResult result = mockMvc.perform(post("/modifyStory")
                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                 .content(JSONObject.toJSONString(map1)))
                 .andExpect(status().isOk())
                 .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void getRoles() throws Exception{
        MvcResult result = mockMvc.perform(get("/getRoles?mapId=1"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void modifyRole() throws Exception{
         Map<String, Object> map1 = new HashMap<>();
         map1.put("mapId", 11);
         map1.put("name", "test(测试专用请勿删除)");
         map1.put("avatar", "1");
         map1.put("creatorId", 1);
         map1.put("id", 10);
         MvcResult result1 = mockMvc.perform(post("/modifyRole")
                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                 .content(JSONObject.toJSONString(map1)))
                 .andExpect(status().isOk())
                 .andReturn();
    }

    @Test
    public void roleToActivity() throws Exception {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("mapId", 11);
        map1.put("name", "test");
        map1.put("avatar", "1");
        map1.put("creatorId", 1);
        MvcResult result1 = mockMvc.perform(post("/addRoleToActivity?activity=117")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();

        Map<String, Object> map2 = new HashMap<>();
        map1.put("id", 11);
        MvcResult result = mockMvc.perform(post("/removeRoleFromActivity?activity=117")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDelete() throws Exception{
        MvcResult result = mockMvc.perform(get("/deleteStory?id=0")).andReturn();
        result = mockMvc.perform(get("/deleteActivity?id=0")).andReturn();
        result = mockMvc.perform(get("/deleteTask?id=0")).andReturn();
    }
}