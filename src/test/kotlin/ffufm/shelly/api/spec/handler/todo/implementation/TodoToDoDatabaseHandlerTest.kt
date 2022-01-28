package ffufm.shelly.api.spec.handler.todo.implementation

import ffufm.shelly.api.PassTestBase
import ffufm.shelly.api.repositories.todo.TodoToDoRepository
import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import ffufm.shelly.api.spec.handler.todo.TodoToDoDatabaseHandler
import ffufm.shelly.api.spec.handler.utils.EntityGenerator
import ffufm.shelly.api.utils.Statuses
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
        val user = userUserRepository.save(EntityGenerator.createUser())
        val todo = EntityGenerator.createTodo().copy(
            user = user
        ).toDto()
        val createdTodo = todoToDoDatabaseHandler.create(todo, user.id!!)

        assertEquals(todo.description, createdTodo.description)
        assertEquals(todo.status, createdTodo.status)
        assertEquals(1, todoToDoRepository.findAll().count())

    }


    @Test
    fun `remove todo should work`() = runBlocking {
        val user = userUserRepository.save(EntityGenerator.createUser())

        val todo = todoToDoRepository.save(
            EntityGenerator.createTodo().copy(
                user = user
            )
        )
        todoToDoDatabaseHandler.remove(todo.id!!)
        assertEquals(0,  todoToDoRepository.findAll().count())
    }


    @Test
    fun `remove todo should fail given invalid todoId`() = runBlocking {
        val id : Long = 12345
        val exception = assertFailsWith<ResponseStatusException> {
            todoToDoDatabaseHandler.remove(id)
        }
        val expectedException = "404 NOT_FOUND \"TodoToDo with ID 12345 not found\""
        assertEquals(expectedException, exception.message)
    }


    @Test
    fun `update todo should return updated todo given valid inputs`() = runBlocking {
        val user = userUserRepository.save(EntityGenerator.createUser())

        val todo = todoToDoRepository.save(
            EntityGenerator.createTodo().copy(
                user = user
            )
        )

        val body= todo.copy(
            description = "Assignment 2",
            status = Statuses.COMPLETED.value
        )

        val updatedToDo = todoToDoDatabaseHandler.update(body.toDto(), todo.id!!)
        assertEquals(body.description, updatedToDo.description)
        assertEquals(body.status, updatedToDo.status)
    }


    @Test
    fun `update should fail given invalid todoId`() = runBlocking {
        val user = userUserRepository.save(EntityGenerator.createUser())

        val todo = todoToDoRepository.save(
            EntityGenerator.createTodo().copy(
                user = user
            )
        )

        val body = todo.copy(
            description = "Assignment 2",
            status = "On going",
        )

        val id : Long = 12345
        val exception = assertFailsWith<ResponseStatusException> {
            todoToDoDatabaseHandler.update(body.toDto(), id)
        }
        val expectedException = "404 NOT_FOUND \"TodoToDo with ID 12345 not found\""
        assertEquals(expectedException, exception.message)
    }

}