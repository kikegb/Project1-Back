package com.enrique.project1back.controller;

import com.enrique.project1back.model.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPostAddUser() throws Exception {
        User user = new User(
                null,
                "Susana Torio",
                "P455w0rd",
                "storio@gmail.com",
                LocalDate.of(1994, 8, 9));

        this.mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    static String asJsonString(final User user) throws JSONException {
        JSONObject object = new JSONObject();
        if (user.getId() != null) {
            object.put("id", user.getId());
        }
        object.put("name", user.getName());
        object.put("password", user.getPassword());
        object.put("email", user.getEmail());
        object.put("birthday", user.getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        return object.toString();
    }
}