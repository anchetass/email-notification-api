package ffufm.shelly.api.spec.handler.todo.implementation

import ffufm.shelly.api.PassTestBase
import ffufm.shelly.api.repositories.todo.TodoToDoRepository
import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import ffufm.shelly.api.spec.handler.todo.TodoToDoDatabaseHandler
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class TodoToDoDatabaseHandlerTest : PassTestBase() {
    @Autowired
    lateinit var todoToDoRepository: TodoToDoRepository

    @Autowired
    lateinit var todoToDoDatabaseHandler: TodoToDoDatabaseHandler

    @Before
    @After
    fun cleanRepositories() {
        todoToDoRepository.deleteAll()
    }

    @Test
    fun `test create`() = runBlocking {
        val body: TodoToDo = TodoToDo()
        todoToDoDatabaseHandler.create(body)
        Unit
    }

    @Test
    fun `test getAll`() = runBlocking {
        val maxResults: Int = 100
        val page: Int = 0
        todoToDoDatabaseHandler.getAll(maxResults, page)
        Unit
    }

    @Test
    fun `test remove`() = runBlocking {
        val id: Long = 0
        todoToDoDatabaseHandler.remove(id)
        Unit
    }

    @Test
    fun `test update`() = runBlocking {
        val body: TodoToDo = TodoToDo()
        val id: Long = 0
        todoToDoDatabaseHandler.update(body, id)
        Unit
    }
}
