package ffufm.shelly.api.spec.handler.user.implementation

import ffufm.shelly.api.PassTestBase
import ffufm.shelly.api.repositories.user.UserUserRepository
import ffufm.shelly.api.spec.dbo.user.UserUser
import ffufm.shelly.api.spec.handler.user.UserUserDatabaseHandler
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class UserUserDatabaseHandlerTest : PassTestBase() {
    @Autowired
    lateinit var userUserRepository: UserUserRepository

    @Autowired
    lateinit var userUserDatabaseHandler: UserUserDatabaseHandler

    @Before
    @After
    fun cleanRepositories() {
        userUserRepository.deleteAll()
    }

    @Test
    fun `test create`() = runBlocking {
        val body: UserUser = UserUser()
        userUserDatabaseHandler.create(body)
        Unit
    }

    @Test
    fun `test remove`() = runBlocking {
        val id: Long = 0
        userUserDatabaseHandler.remove(id)
        Unit
    }

    @Test
    fun `test update`() = runBlocking {
        val body: UserUser = UserUser()
        val id: Long = 0
        userUserDatabaseHandler.update(body, id)
        Unit
    }
}
