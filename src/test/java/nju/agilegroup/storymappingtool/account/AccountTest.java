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
        Map<String, Object> map1 = new HashMap<>();
        map1.put("email", "1483809252@qq.com");
        map1.put("password", "njuagile123");
        MvcResult result1 = mockMvc.perform(post("http://localhost:8090/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map1)))
                .andExpect(status().isOk())
                .andReturn();

        String resultJson1 = result1.getResponse().getContentAsString();
        JSONObject object1 = (JSONObject) JSONValue.parse(resultJson1);
        Assert.assertTrue((Boolean) object1.get("success"));


        Map<String, Object> map2 = new HashMap<>();
        map2.put("email", "1483809252@qq.com");
        map2.put("password", "njuagile");
        MvcResult result2 = mockMvc.perform(post("http://localhost:8090/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONObject.toJSONString(map2)))
                .andExpect(status().isOk())
                .andReturn();

        String resultJson2 = result2.getResponse().getContentAsString();
        JSONObject object2 = (JSONObject) JSONValue.parse(resultJson2);
        Assert.assertFalse((Boolean) object2.get("success"));
    }
}
