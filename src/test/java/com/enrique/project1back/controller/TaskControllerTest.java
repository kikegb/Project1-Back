package com.enrique.project1back.controller;

import com.enrique.project1back.model.Task;
import com.enrique.project1back.service.TaskService;
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


import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerTest {

    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;


    Task task1 = new Task(
            "2",
            "21",
            "Wash car",
            "Go wash the car, it's disgusting.",
            false);

    Task task2 = new Task(
            "3",
            "21",
            "Take the dog for a walk",
            "Take the dog for a walk, please.",
            true);

    Task task1NullId = new Task(
            null,
            "21",
            "Wash car",
            "Go wash the car, it's disgusting.",
            false);

    @DisplayName("POST add task: Success")
    @Test
    public void testPostAddTask() throws Exception {
        doReturn(task1).when(taskService).add(task1NullId);

        this.mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(task1NullId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", is(task1.getId())))
                .andExpect(jsonPath("$.userId", is(task1.getUserId())))
                .andExpect(jsonPath("$.title", is(task1.getTitle())))
                .andExpect(jsonPath("$.description", is(task1.getDescription())))
                .andExpect(jsonPath("$.done", is(task1.isDone())));
    }

    @DisplayName("GET task by user id: Success")
    @Test
    public void testGetTaskByUserId() throws Exception {
        doReturn(List.of(task1, task2)).when(taskService).findByUserId(task1.getUserId());

        this.mockMvc.perform(get("/task/findByUserId")
                        .param("userId", task1.getUserId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(task1.getId())))
                .andExpect(jsonPath("$[0].userId", is(task1.getUserId())))
                .andExpect(jsonPath("$[0].title", is(task1.getTitle())))
                .andExpect(jsonPath("$[0].description", is(task1.getDescription())))
                .andExpect(jsonPath("$[0].done", is(task1.isDone())))
                .andExpect(jsonPath("$[1].id", is(task2.getId())))
                .andExpect(jsonPath("$[1].userId", is(task2.getUserId())))
                .andExpect(jsonPath("$[1].title", is(task2.getTitle())))
                .andExpect(jsonPath("$[1].description", is(task2.getDescription())))
                .andExpect(jsonPath("$[1].done", is(task2.isDone())));
    }

    @DisplayName("GET task by id: Success")
    @Test
    public void testGetTaskById() throws Exception {
        doReturn(task1).when(taskService).findById(task1.getId());

        this.mockMvc.perform(get("/task/findById")
                        .param("id", task1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(task1.getId())))
                .andExpect(jsonPath("$.userId", is(task1.getUserId())))
                .andExpect(jsonPath("$.title", is(task1.getTitle())))
                .andExpect(jsonPath("$.description", is(task1.getDescription())))
                .andExpect(jsonPath("$.done", is(task1.isDone())));
    }

    @DisplayName("GET all tasks: Success")
    @Test
    public void testGetAll() throws Exception {
        doReturn(List.of(task1, task2)).when(taskService).findAll();

        this.mockMvc.perform(get("/task/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(task1.getId())))
                .andExpect(jsonPath("$[0].userId", is(task1.getUserId())))
                .andExpect(jsonPath("$[0].title", is(task1.getTitle())))
                .andExpect(jsonPath("$[0].description", is(task1.getDescription())))
                .andExpect(jsonPath("$[0].done", is(task1.isDone())))
                .andExpect(jsonPath("$[1].id", is(task2.getId())))
                .andExpect(jsonPath("$[1].userId", is(task2.getUserId())))
                .andExpect(jsonPath("$[1].title", is(task2.getTitle())))
                .andExpect(jsonPath("$[1].description", is(task2.getDescription())))
                .andExpect(jsonPath("$[1].done", is(task2.isDone())));
    }

    @DisplayName("PUT update task: Success")
    @Test
    public void testPutUpdate() throws Exception {
        Task updatedTask = new Task(
                task1.getId(),
                "41",
                "Wash car ASAP",
                "It's time, you can't see through the windows.",
                true);
        doReturn(updatedTask).when(taskService).update(updatedTask);

        this.mockMvc.perform(put("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedTask)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(updatedTask.getId())))
                .andExpect(jsonPath("$.userId", is(updatedTask.getUserId())))
                .andExpect(jsonPath("$.title", is(updatedTask.getTitle())))
                .andExpect(jsonPath("$.description", is(updatedTask.getDescription())))
                .andExpect(jsonPath("$.done", is(updatedTask.isDone())));
    }

    @DisplayName("PATCH update done: Success")
    @Test
    public void testPatchDone() throws Exception {
        Task updatedTask = new Task(
                task1.getId(),
                task1.getUserId(),
                task1.getTitle(),
                task1.getDescription(),
                true
        );
        doReturn(updatedTask).when(taskService).updateDone(task1.getId(), updatedTask.isDone());

        this.mockMvc.perform(patch("/task")
                        .param("id", task1.getId())
                        .param("done", updatedTask.isDone()? "true":"false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(updatedTask.getId())))
                .andExpect(jsonPath("$.userId", is(updatedTask.getUserId())))
                .andExpect(jsonPath("$.title", is(updatedTask.getTitle())))
                .andExpect(jsonPath("$.description", is(updatedTask.getDescription())))
                .andExpect(jsonPath("$.done", is(updatedTask.isDone())));
    }

    @DisplayName("DELETE task by id: Success")
    @Test
    public void testDeleteUserById() throws Exception {
        doReturn(1).when(taskService).deleteById(task1.getId());

        this.mockMvc.perform(delete("/task")
                        .param("id", task1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    static String asJsonString(final Task task) throws JSONException {
        JSONObject object = new JSONObject();
        if (task.getId() != null) {
            object.put("id", task.getId());
        }
        object.put("userId", task.getUserId());
        object.put("title", task.getTitle());
        object.put("description", task.getDescription());
        object.put("done", task.isDone());

        return object.toString();
    }
}