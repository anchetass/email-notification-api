package ffufm.shelly.api.handlerimpl.todo

import de.ffuf.pass.common.handlers.PassDatabaseHandler
import de.ffuf.pass.common.utilities.extensions.orElseThrow404
import ffufm.shelly.api.repositories.todo.TodoToDoRepository
import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import ffufm.shelly.api.spec.handler.todo.TodoToDoDatabaseHandler
import kotlin.Int
import kotlin.Long
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("todo.TodoToDoHandler")
class TodoToDoHandlerImpl : PassDatabaseHandler<TodoToDo, TodoToDoRepository>(),
        TodoToDoDatabaseHandler {
    /**
     * Create ToDo: Creates a new ToDo object
     * HTTP Code 201: The created ToDo
     */
    override suspend fun create(body: TodoToDo): TodoToDo {
        TODO("not checked yet")
        return repository.save(body)
    }

    /**
     * Get all ToDos by page: Returns all ToDos from the system that the user has access to. The
     * Headers will include TotalElements, TotalPages, CurrentPage and PerPage to help with Pagination.
     * HTTP Code 200: List of ToDos
     */
    override suspend fun getAll(maxResults: Int = 100, page: Int = 0): Page<TodoToDo> {
        TODO("not checked yet")
        return repository.findAll(Pageable.unpaged())
    }

    /**
     * Delete ToDo by id.: Deletes one specific ToDo.
     */
    override suspend fun remove(id: Long): TodoToDo {
        val original = repository.findById(id).orElseThrow404(id)
        TODO("not checked yet - update the values you really want updated")
        return repository.delete(original)
    }

    /**
     * Update the ToDo: Updates an existing ToDo
     * HTTP Code 200: The updated model
     * HTTP Code 404: The requested object could not be found by the submitted id.
     * HTTP Code 422: On or many fields contains a invalid value.
     */
    override suspend fun update(body: TodoToDo, id: Long): TodoToDo {
        val original = repository.findById(id).orElseThrow404(id)
        TODO("not checked yet - update the values you really want updated")
        return repository.save(original)
    }
}
