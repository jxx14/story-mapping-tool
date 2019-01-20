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
           MvcResult result1 = mockMvc.perform(post("http://localhost:8090/getMap")
                   .contentType(MediaType.APPLICATION_JSON_UTF8)
                   .param("mapId", "1"))
                   .andExpect(status().isOk())
                   .andReturn();
    }

    @Test
    public void createActivityCard() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "agilegroup");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/createActivityCard")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createTaskCard() throws Exception{
           Map<String, Object> map1 = new HashMap<>();
           map1.put("name", "agilegroup");
           MvcResult result1 = mockMvc.perform(post("http://localhost:8090/createTaskCard")
                   .contentType(MediaType.APPLICATION_JSON_UTF8)
                   .content(JSONObject.toJSONString(map1)))
                   .andExpect(status().isOk())
                   .andReturn();
    }

    @Test
    public void createStoryCard() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "agilegroup");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/createStoryCard")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();
    }

   @Test
    public void modifyActivityCard() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "agilegroup");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/modifyActivityCard")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void modifyTaskCard() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "agilegroup");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/modifyTaskCard")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void modifyStoryCard() throws Exception {
         Map<String, Object> map1 = new HashMap<>();
         map1.put("name", "agilegroup");
         MvcResult result1 = mockMvc.perform(post("http://localhost:8090/modifyStoryCard")
                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                 .content(JSONObject.toJSONString(map1)))
                 .andExpect(status().isOk())
                 .andReturn();
    }


    @Test
    public void getRoles() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "agilegroup");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/getRoles")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("mapId", "1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void modifyRole() throws Exception{
         Map<String, Object> map1 = new HashMap<>();
         map1.put("name", "agilegroup");
         MvcResult result1 = mockMvc.perform(post("http://localhost:8090/modifyRole")
                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                 .content(JSONObject.toJSONString(map1)))
                 .andExpect(status().isOk())
                 .andReturn();
    }
}