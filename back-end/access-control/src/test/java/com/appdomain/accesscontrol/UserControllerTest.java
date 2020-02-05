package com.appdomain.accesscontrol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private JacksonJsonParser jsonParser = new JacksonJsonParser();

    @Test
    public void testGetAllUsersFailsWithoutAdminToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/list"))
                .andReturn();
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), mvcResult.getResponse().getStatus() );
    }

    @Test
    public void testGetAllUsersSucceedsWithAdminToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/list")
        .header("Authorization", "Bearer " + getAdminToken()))
                .andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus() );
    }

    private String getAdminToken() throws Exception {
        final MultiValueMap<String,String> adminCredentials = new LinkedMultiValueMap<>();
        adminCredentials.add("grant_type", "password");
        adminCredentials.add("username", "TAdmin0000");
        adminCredentials.add("password", "Password!1");
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .with(httpBasic("front-end","a"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(adminCredentials)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn();
        return jsonParser.parseMap(mvcResult.getResponse().getContentAsString())
                .get("access_token").toString();
    }

}
