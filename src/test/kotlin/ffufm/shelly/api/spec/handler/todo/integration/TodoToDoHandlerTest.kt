package ffufm.shelly.api.spec.handler.todo.integration

import com.fasterxml.jackson.databind.ObjectMapper
import ffufm.shelly.api.PassTestBase
import ffufm.shelly.api.repositories.todo.TodoToDoRepository
import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

class TodoToDoHandlerTest : PassTestBase() {
    @Autowired
    private lateinit var todoToDoRepository: TodoToDoRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Before
    @After
    fun cleanRepositories() {
        todoToDoRepository.deleteAll()
    }

    @Test
    @WithMockUser
    fun `test create`() {
        val body: TodoToDo = TodoToDo()
                mockMvc.post("/todos/") {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(body)
                }.andExpect {
                    status { isOk }
                    
                }
    }

    @Test
    @WithMockUser
    fun `test getAll`() {
                mockMvc.get("/todos/") {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON
                    
                }.andExpect {
                    status { isOk }
                    
                }
    }

    @Test
    @WithMockUser
    fun `test remove`() {
        val id: Long = 0
                mockMvc.delete("/todos/{id}/", id) {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON
                    
                }.andExpect {
                    status { isOk }
                    
                }
    }

    @Test
    @WithMockUser
    fun `test update`() {
        val body: TodoToDo = TodoToDo()
        val id: Long = 0
                mockMvc.put("/todos/{id}/", id) {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(body)
                }.andExpect {
                    status { isOk }
                    
                }
    }
}
