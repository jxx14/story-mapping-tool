package nju.agilegroup.storymappingtool.controller;

import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
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
public class TeamControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void getTeamInfo ()  throws Exception{
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/getTeamInfo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("teamID","1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void addTeam() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("email", "modify2@qq.com");
        map.put("password", "njuagile123");
        MvcResult result = mockMvc.perform(post("http://localhost:8090/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map)))
                .andExpect(status().isOk())
                .andReturn();


        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "test");
        map1.put("description", "test description");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/addTeam")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .session((MockHttpSession) result.getRequest().getSession())
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();
    }

     @Test
    public void getTeamMembers() throws Exception {

       MvcResult result1 = mockMvc.perform(post("http://localhost:8090/getTeamMembers")
               .contentType(MediaType.APPLICATION_JSON_UTF8)
               .param("teamID","1"))
               .andExpect(status().isOk())
               .andReturn();
    }



    @Test
    public void addMember()  throws Exception{
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/addMember")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("userName", "modify3")
                .param("teamID","2"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deleteMember()  throws Exception{
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/deleteMember")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("userName", "modify3")
                .param("teamID","2"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void modifyTeamInfo()  throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "test");
        map1.put("description", "test description");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/modifyTeamInfo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1))
                .param("teamId","1"))
                .andExpect(status().isOk())
                .andReturn();
    }

}