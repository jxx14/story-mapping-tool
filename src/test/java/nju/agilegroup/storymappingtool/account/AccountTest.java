package nju.agilegroup.storymappingtool.account;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AccountTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testLogin() throws Exception {
        //正确登陆
        Map<String, Object> map1 = new HashMap<>();
        map1.put("email", "modify2@qq.com");
        map1.put("password", "njuagile123");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();


        //邮箱不正确
        Map<String, Object> map2 = new HashMap<>();
        map2.put("email", "19252@qq.com");
        map2.put("password", "njuagile");
        MvcResult result2 = mockMvc.perform(post("http://localhost:8090/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map2)))
                .andExpect(status().isOk())
                .andReturn();

        //密码不正确
        Map<String, Object> map3 = new HashMap<>();
        map3.put("email", "1483809252@qq.com");
        map3.put("password", "njua");
        MvcResult result3 = mockMvc.perform(post("http://localhost:8090/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map3)))
                .andExpect(status().isOk())
                .andReturn();



        //用户已经登陆
        Map<String, Object> map4 = new HashMap<>();
        map4.put("email", "modify2@qq.com");
        map4.put("password", "njuagile123");
        MvcResult result4 = mockMvc.perform(post("http://localhost:8090/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1))
                .session((MockHttpSession) result3.getRequest().getSession()))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testSignUp() throws Exception {
        //用户名存在，邮箱不存在
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "Agile7");
        map1.put("email", "1@qq.com");
        map1.put("password", "njuagile123");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/signUp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();

        //用户名不存在，邮箱存在
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "mo");
        map2.put("email", "1234567897@qq.com");
        map2.put("password", "njuagile123");
        MvcResult result2 = mockMvc.perform(post("http://localhost:8090/signUp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map2)))
                .andExpect(status().isOk())
                .andReturn();



        //用户名和邮箱都已经存在
        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "Agile7");
        map3.put("email", "1234567897@qq.com");
        map3.put("password", "njuagile123");
        MvcResult result3 = mockMvc.perform(post("http://localhost:8090/signUp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map3)))
                .andExpect(status().isOk())
                .andReturn();

        //用户名和邮箱都不存在
        Map<String, Object> map4 = new HashMap<>();
        map4.put("name", "testName");
        map4.put("email", "testName@qq.com");
        map4.put("password", "njuagile123");
        MvcResult result4 = mockMvc.perform(post("http://localhost:8090/signUp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map4)))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testListUserInfo()throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("email", "modify2@qq.com");
        map1.put("password", "njuagile123");
        MvcResult result = mockMvc.perform(post("http://localhost:8090/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/listUserInfo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .session((MockHttpSession) result.getRequest().getSession()))
                .andExpect(status().isOk())
                .andReturn();

    }


    @Test
    public void testGetTeams()throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("email", "modify2@qq.com");
        map1.put("password", "njuagile123");
        MvcResult result = mockMvc.perform(post("http://localhost:8090/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/getTeams")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .session((MockHttpSession) result.getRequest().getSession()))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testLogout()throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("email", "modify2@qq.com");
        map1.put("password", "njuagile123");
        MvcResult result = mockMvc.perform(post("http://localhost:8090/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/logout")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .session((MockHttpSession) result.getRequest().getSession()))
                .andExpect(status().isOk())
                .andReturn();

    }


   @Test
   public void testModifyUserInfo()throws Exception{
       Map<String, Object> map1 = new HashMap<>();
       map1.put("email", "modify2@qq.com");
       map1.put("password", "njuagile123");
       MvcResult result = mockMvc.perform(post("http://localhost:8090/user/login")
               .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(JSONObject.toJSONString(map1)))
               .andExpect(status().isOk())
               .andReturn();

       Map<String, Object> map2 = new HashMap<>();
       map2.put("email", "modify2@qq.com");
       map2.put("name","modify3");
       MvcResult result1 = mockMvc.perform(post("http://localhost:8090/modifyUserInfo")
               .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(JSONObject.toJSONString(map2))
               .session((MockHttpSession) result.getRequest().getSession()))
               .andExpect(status().isOk())
               .andReturn();
   }

    @Test
    public void testGetUsers()throws Exception{
        MvcResult result = mockMvc.perform(get("/getUsers")).andReturn();
    }
}
