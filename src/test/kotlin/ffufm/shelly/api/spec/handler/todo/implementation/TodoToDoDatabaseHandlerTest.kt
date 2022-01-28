package ffufm.shelly.api.spec.handler.todo.implementation

import ffufm.shelly.api.PassTestBase
import ffufm.shelly.api.repositories.todo.TodoToDoRepository
import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import ffufm.shelly.api.spec.handler.todo.TodoToDoDatabaseHandler
import ffufm.shelly.api.spec.handler.utils.EntityGenerator
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TodoToDoDatabaseHandlerTest : PassTestBase() {
    @Autowired
    lateinit var todoToDoDatabaseHandler: TodoToDoDatabaseHandler

    @Test
    fun `create should return todo`() = runBlocking {
        assertEquals(0, todoToDoRepository.findAll().count())
        val todo = EntityGenerator.createTodo().toDto()
        val createdTodo = todoToDoDatabaseHandler.create(todo)

        assertEquals(1, todoToDoRepository.findAll().count())
        assertEquals(todo.description, createdTodo.description)
        assertEquals(todo.status, createdTodo.status)
    }


    @Test
    fun `remove todo should work`() = runBlocking {
        val todo = EntityGenerator.createTodo()
        val createdTodo = todoToDoRepository.save(todo)

        assertEquals(1,  todoToDoRepository.findAll().count())
       todoToDoDatabaseHandler.remove(createdTodo.id!!)
        assertEquals(0,  todoToDoRepository.findAll().count())
    }


    @Test
    fun `remove todo should fail given invalid todoId`() = runBlocking {
        val id : Long = 12345
        val exception = assertFailsWith<ResponseStatusException> {
            todoToDoDatabaseHandler.remove(id)
        }
        val expectedException = "404 NOT_FOUND \"TodoTodo with ID $id not found\""
        assertEquals(expectedException, exception.message)
    }




    @Test
    fun `update todo should return updated todo given valid inputs`() = runBlocking {
        val todo = EntityGenerator.createTodo()
        val original = todoToDoRepository.save(todo)

        val body= original.copy(
            description = "Hindi Maganda",
            status = "On going",
        )

        val updatedToDo = todoToDoDatabaseHandler.update(body.toDto(), original.id!!)
        assertEquals(body.description, updatedToDo.description)
        assertEquals(body.status, updatedToDo.status)
    }


    @Test
    fun `update should fail given invalid todoId`() = runBlocking {
        val original = todoToDoRepository.save(EntityGenerator.createTodo())

        val body = original.copy(
            description = "Hindi Maganda",
            status = "On going",
        )

        val id : Long = 12345
        val exception = assertFailsWith<ResponseStatusException> {
           todoToDoDatabaseHandler.update(body.toDto(), id)
        }
        val expectedException = "404 NOT_FOUND \"TodoTodo with ID $id not found\""
        assertEquals(expectedException, exception.message)
    }

}
