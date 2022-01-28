package ffufm.shelly.api

import de.ffuf.pass.common.security.SpringContext
import de.ffuf.pass.common.security.SpringSecurityAuditorAware
import ffufm.shelly.api.repositories.todo.TodoToDoRepository
import ffufm.shelly.api.repositories.user.UserUserRepository
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest(classes = [SBShelly::class, SpringSecurityAuditorAware::class])
@AutoConfigureMockMvc
abstract class PassTestBase {
    @Autowired
    lateinit var context: ApplicationContext

    @Autowired
    lateinit var todoToDoRepository: TodoToDoRepository

    @Autowired
    lateinit var userUserRepository: UserUserRepository

    @Before
    fun initializeContext() {
        SpringContext.context = context
    }

    @After
    fun cleanRepositories(){
        todoToDoRepository.deleteAll()
        userUserRepository.deleteAll()
    }
}
