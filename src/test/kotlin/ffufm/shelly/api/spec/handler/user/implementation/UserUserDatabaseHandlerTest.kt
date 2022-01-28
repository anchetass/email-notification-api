package ffufm.shelly.api.spec.handler.user.implementation

import ffufm.shelly.api.PassTestBase
import ffufm.shelly.api.repositories.user.UserUserRepository
import ffufm.shelly.api.spec.dbo.user.UserUser
import ffufm.shelly.api.spec.handler.user.UserUserDatabaseHandler
import ffufm.shelly.api.spec.handler.utils.EntityGenerator
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserUserDatabaseHandlerTest : PassTestBase() {
    @Autowired
    lateinit var userUserDatabaseHandler: UserUserDatabaseHandler

    @Test
    fun `create should return user`() = runBlocking {
        assertEquals(0, userUserRepository.findAll().count())
        val user = EntityGenerator.createUser().toDto()
        val createdUser = userUserDatabaseHandler.create(user)

        assertEquals(1, userUserRepository.findAll().count())
        assertEquals(user.name, createdUser.name)
        assertEquals(user.email, createdUser.email)
    }

    @Test
    fun `create should return fail given duplicate email`() = runBlocking {
        val user = userUserRepository.save(EntityGenerator.createUser())
        val duplicateUser = user.copy(
            name = "Brenda",
            email = "brenda@gmail.com"
        ).toDto()
        val exception = assertFailsWith<ResponseStatusException> {
            userUserDatabaseHandler.create(duplicateUser)
        }
        val expectedException = "409 CONFLICT \"Email ${duplicateUser.email} already exists!\""
        assertEquals(expectedException, exception.message)
    }

    @Test
    fun `remove user should work`() = runBlocking {
        val user = EntityGenerator.createUser()
        val createdUser = userUserRepository.save(user)

        assertEquals(1, userUserRepository.findAll().count())
        userUserDatabaseHandler.remove(createdUser.id!!)
        assertEquals(0, userUserRepository.findAll().count())
    }

    @Test
    fun `remove user should fail given invalid userId`() = runBlocking {
        val id : Long = 123
        val exception = assertFailsWith<ResponseStatusException> {
            userUserDatabaseHandler.remove(id)
        }
        val expectedException = "404 NOT_FOUND \"UserUser with ID $id not found\""
        assertEquals(expectedException, exception.message)
    }

    @Test
    fun `update user should return updated user given valid inputs`() = runBlocking {
        val user = EntityGenerator.createUser()
        val original = userUserRepository.save(user)

        val body= original.copy(
            name = "Brandon",
            email = "brandon@gmail.com",
        )

        val updatedUser = userUserDatabaseHandler.update(body.toDto(), original.id!!)
        assertEquals(body.name, updatedUser.name)
        assertEquals(body.email, updatedUser.email)
    }

    @Test
    fun `update should fail given invalid userId`() = runBlocking {
        val original = userUserRepository.save(EntityGenerator.createUser())

        val body = original.copy(
            name = "Brandon",
            email = "brandon@gmail.com",
        )

        val id : Long = 123
        val exception = assertFailsWith<ResponseStatusException> {
            userUserDatabaseHandler.update(body.toDto(), id)
        }
        val expectedException = "404 NOT_FOUND \"UserUser with ID $id not found\""
        assertEquals(expectedException, exception.message)
    }
}
