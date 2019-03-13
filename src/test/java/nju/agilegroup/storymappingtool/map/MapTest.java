package nju.agilegroup.storymappingtool.map;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by ZHR on 2019/1/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MapTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void test1() throws Exception{
        MvcResult result = mockMvc.perform(get("/getMaps?teamId=1")).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void test2() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "test");
        map1.put("description", "");
        map1.put("creator", 30);
        map1.put("team", 49);
        MvcResult result = mockMvc.perform(post("/createMap")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void test3() throws Exception{
        MvcResult result = mockMvc.perform(get("/deleteMap?mapId=79")).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void test4() throws Exception{
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 78);
        map1.put("name", "test");
        map1.put("description", "");
        map1.put("creator", 30);
        map1.put("team", 49);
        MvcResult result = mockMvc.perform(post("/modifyMap")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
