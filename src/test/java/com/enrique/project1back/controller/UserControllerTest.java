package com.enrique.project1back.controller;

import com.enrique.project1back.model.User;
import com.enrique.project1back.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    User user1NullId = new User(
            null,
            "Susana Torio",
            "P455w0rd",
            "storio@gmail.com",
            LocalDate.of(1994, 8, 9));

    User user1 = new User(
            "21",
            "Susana Torio",
            "P455w0rd",
            "storio@gmail.com",
            LocalDate.of(1994, 8, 9));

    User user2 = new User(
            "4",
            "Encarna Vales",
            "evales123",
            "evales@gmail.com",
            LocalDate.of(1981, 9, 17));

    @DisplayName("POST add user: Success")
    @Test
    public void testPostAddUser() throws Exception {
        doReturn(user1).when(userService).add(user1NullId);

        this.mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user1NullId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", is(user1.getId())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())))
                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.password", is(user1.getPassword())))
                .andExpect(jsonPath("$.birthday", is(user1.getBirthday()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))));
    }

    @DisplayName("GET user by email: Success")
    @Test
    public void testGetUserByEmail() throws Exception {
        doReturn(user1).when(userService).findByEmail(user1.getEmail());

        this.mockMvc.perform(get("/user/findByEmail")
                        .param("email", user1.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(user1.getId())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())))
                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.password", is(user1.getPassword())))
                .andExpect(jsonPath("$.birthday", is(user1.getBirthday()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))));
    }

    @DisplayName("GET user by id: Success")
    @Test
    public void testGetUserById() throws Exception {
        doReturn(user1).when(userService).findById(user1.getId());

        this.mockMvc.perform(get("/user/findById")
                        .param("id", user1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(user1.getId())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())))
                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.password", is(user1.getPassword())))
                .andExpect(jsonPath("$.birthday", is(user1.getBirthday()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))));
    }

    @DisplayName("GET all users: Success")
    @Test
    public void testGetAll() throws Exception {
        doReturn(List.of(user1, user2)).when(userService).findAll();

        this.mockMvc.perform(get("/user/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(user1.getId())))
                .andExpect(jsonPath("$[0].email", is(user1.getEmail())))
                .andExpect(jsonPath("$[0].name", is(user1.getName())))
                .andExpect(jsonPath("$[0].password", is(user1.getPassword())))
                .andExpect(jsonPath("$[0].birthday", is(user1.getBirthday()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))))
                .andExpect(jsonPath("$[1].id", is(user2.getId())))
                .andExpect(jsonPath("$[1].email", is(user2.getEmail())))
                .andExpect(jsonPath("$[1].name", is(user2.getName())))
                .andExpect(jsonPath("$[1].password", is(user2.getPassword())))
                .andExpect(jsonPath("$[1].birthday", is(user2.getBirthday()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))));
    }

    @DisplayName("PATCH update name: Success")
    @Test
    public void testPatchName() throws Exception {
        User updatedUser = new User(
                user1.getId(),
                "Helena Nito",
                user1.getPassword(),
                user1.getEmail(),
                user1.getBirthday()
        );
        doReturn(updatedUser).when(userService).updateName(user1.getId(), updatedUser.getName());

        this.mockMvc.perform(patch("/user")
                        .param("id", user1.getId())
                        .param("name", updatedUser.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(updatedUser.getId())))
                .andExpect(jsonPath("$.email", is(updatedUser.getEmail())))
                .andExpect(jsonPath("$.name", is(updatedUser.getName())))
                .andExpect(jsonPath("$.password", is(updatedUser.getPassword())))
                .andExpect(jsonPath("$.birthday", is(updatedUser.getBirthday()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))));
    }

    @DisplayName("PUT update user: Success")
    @Test
    public void testPutUpdate() throws Exception {
        User updatedUser = new User(
                user1.getId(),
                "Helena Nito",
                "hnito4803",
                "hnito@hotmail.com",
                LocalDate.of(1987, 4, 9)
        );
        doReturn(updatedUser).when(userService).update(updatedUser);

        this.mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(updatedUser.getId())))
                .andExpect(jsonPath("$.email", is(updatedUser.getEmail())))
                .andExpect(jsonPath("$.name", is(updatedUser.getName())))
                .andExpect(jsonPath("$.password", is(updatedUser.getPassword())))
                .andExpect(jsonPath("$.birthday", is(updatedUser.getBirthday()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))));
    }

    @DisplayName("DELETE user by id: Success")
    @Test
    public void testDeleteUserById() throws Exception {
        doReturn(1).when(userService).deleteById(user1.getId());

        this.mockMvc.perform(delete("/user")
                        .param("id", user1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
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