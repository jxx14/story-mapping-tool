package nju.agilegroup.storymappingtool.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by ZHR on 2019/3/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ViewControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void test() throws Exception {
        MvcResult result = mockMvc.perform(get("/storymap")).andReturn();
        result = mockMvc.perform(get("/user")).andReturn();
        result = mockMvc.perform(get("/map-list")).andReturn();
        result = mockMvc.perform(get("/map-create")).andReturn();
        result = mockMvc.perform(get("/team-create")).andReturn();
        result = mockMvc.perform(get("/member-invite")).andReturn();
        result = mockMvc.perform(get("/member-remove")).andReturn();
        result = mockMvc.perform(get("/login")).andReturn();
        result = mockMvc.perform(get("/register")).andReturn();
        result = mockMvc.perform(get("/password-reset")).andReturn();

    }
}
