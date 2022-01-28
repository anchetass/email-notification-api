package ffufm.shelly.api.spec.handler.user.integration

import com.fasterxml.jackson.databind.ObjectMapper
import ffufm.shelly.api.PassTestBase
import ffufm.shelly.api.repositories.user.UserUserRepository
import ffufm.shelly.api.spec.dbo.user.UserUser
import ffufm.shelly.api.spec.handler.utils.EntityGenerator
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

class UserUserHandlerTest : PassTestBase() {
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @WithMockUser
    fun `create user should return isOk`() {
        val user = EntityGenerator.createUser()
        mockMvc.post("/users/") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(user)
        }.asyncDispatch().andExpect {
            status { isOk() }
        }
    }

    @Test
    @WithMockUser
    fun `create user should return isConflict given duplicate email`() {
        userUserRepository.save(EntityGenerator.createUser())
        val body = EntityGenerator.createUser()

        mockMvc.post("/users/") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.asyncDispatch().andExpect {
            status { isConflict() }
        }
    }

    @Test
    @WithMockUser
    fun `remove user should return isOk`() {
        val savedUser = userUserRepository.save(EntityGenerator.createUser())
                mockMvc.delete("/users/{id}/", savedUser.id) {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON

                }.asyncDispatch().andExpect {
                    status { isOk() }
                }
    }

    @Test
    @WithMockUser
    fun `remove user should return return isNotFound given invalid userId`() {
        userUserRepository.save(EntityGenerator.createUser())
        val invalidId: Long = 123
        mockMvc.delete("/users/{id}/", invalidId) {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
        }.asyncDispatch().andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @WithMockUser
    fun `update user should return isOk`() {
        val savedUser = userUserRepository.save(EntityGenerator.createUser())
        val body = savedUser.copy(
            name = "Brenda",
            email = "brenda@gmail.com"
        )
                mockMvc.put("/users/{id}/", savedUser.id) {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(body)
                }.asyncDispatch().andExpect {
                    status { isOk() }
                }
    }

    @Test
    @WithMockUser
    fun `update user should return isNotFound given invalid userId`() {
        val invalidId: Long = 123
        val original = userUserRepository.save(
            EntityGenerator.createUser()
        )
        val body = original.copy(
            name = "Brenda",
            email = "brenda@gmail.com"
        )
        mockMvc.put("/cities/{id}/", invalidId) {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.asyncDispatch().andExpect {
            status { isNotFound() }
        }
    }
}
