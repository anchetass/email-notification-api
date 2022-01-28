package ffufm.shelly.api.spec.handler.todo.integration

import com.fasterxml.jackson.databind.ObjectMapper
import ffufm.shelly.api.PassTestBase
import ffufm.shelly.api.repositories.todo.TodoToDoRepository
import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import ffufm.shelly.api.spec.handler.utils.EntityGenerator
import ffufm.shelly.api.utils.Statuses
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
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
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(){
        todoToDoRepository.deleteAll()
    }

    @Test
    @WithMockUser
    fun `create todo should return 200`() {
        val user = userUserRepository.save(EntityGenerator.createUser())
        val body = EntityGenerator.createTodo().copy(
            user = user
        )

        mockMvc.post("/todos/${user.id}/") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.asyncDispatch().andExpect { status { isOk() } }
    }

    @Test
    fun `update todo should return 200`() {

        val user = userUserRepository.save(EntityGenerator.createUser())
        val body = todoToDoRepository.save(
            EntityGenerator.createTodo().copy(
                user = user
            )
        )

        val updatedTodo = body.copy(
            description = "Maganda",
            status = "Completed",
        )

        mockMvc.put("/todos/${body.id!!}/") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedTodo)
        }.andExpect { status { isOk() } }
    }


    @Test
    fun `delete todo should return 200`() {

        val user = userUserRepository.save(EntityGenerator.createUser())
        val body = todoToDoRepository.save(
            EntityGenerator.createTodo().copy(
                user = user
            )
        )

        mockMvc.delete("/todos/${body.id!!}/") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.andExpect { status { isOk() } }
    }


    @Test
    fun `get todo should return all todos`() {

        val user = userUserRepository.save(EntityGenerator.createUser())
        val body = EntityGenerator.createTodo().copy(
            user = user
        )

        val maxResults = 100
        val page = 0

        todoToDoRepository.saveAll(
            listOf(
                body,
                body.copy(
                    status = Statuses.COMPLETED.value
                )
            )
        )

        mockMvc.get("/todos/?page=$page&maxResult=$maxResults") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }
    }


}