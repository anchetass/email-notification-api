package ffufm.shelly.api.handlerimpl.todo

import de.ffuf.pass.common.handlers.PassDatabaseHandler
import de.ffuf.pass.common.utilities.extensions.orElseThrow404
import de.ffuf.pass.common.utilities.extensions.toDtos
import ffufm.shelly.api.repositories.todo.TodoToDoRepository
import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import ffufm.shelly.api.spec.dbo.todo.TodoToDoDTO
import ffufm.shelly.api.spec.handler.todo.TodoToDoDatabaseHandler
import ffufm.shelly.api.utils.Statuses
import kotlin.Int
import kotlin.Long
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component("todo.TodoToDoHandler")
class TodoToDoHandlerImpl : PassDatabaseHandler<TodoToDo, TodoToDoRepository>(),
        TodoToDoDatabaseHandler {
    /**
     * Create ToDo: Creates a new ToDo object
     * HTTP Code 201: The created ToDo
     */
    override suspend fun create(body: TodoToDoDTO): TodoToDoDTO {
        if (body.status !in Statuses.values().map { it.value }) {
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Invalid status: ${body.status}"
            )
        }
        return repository.save(body.toEntity()).toDto()
    }

    /**
     * Get all ToDos by page: Returns all ToDos from the system that the user has access to. The
     * Headers will include TotalElements, TotalPages, CurrentPage and PerPage to help with Pagination.
     * HTTP Code 200: List of ToDos
     */
    override suspend fun getAll(maxResults: Int, page: Int): Page<TodoToDoDTO> {
        val pagination = PageRequest.of(page, maxResults)
        return repository.findAll(pagination).toDtos()
    }

    /**
     * Delete ToDo by id.: Deletes one specific ToDo.
     */
    override suspend fun remove(id: Long) {
        val original = repository.findById(id).orElseThrow404(id)
        return repository.delete(original)
    }

    /**
     * Update the ToDo: Updates an existing ToDo
     * HTTP Code 200: The updated model
     * HTTP Code 404: The requested object could not be found by the submitted id.
     * HTTP Code 422: On or many fields contains a invalid value.
     */
    override suspend fun update(body: TodoToDoDTO, id: Long): TodoToDoDTO {
        val original = repository.findById(id).orElseThrow404(id)

        if (body.status != null && body.status !in Statuses.values().map { it.value }) {
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Invalid status: ${body.status}"
            )
        }

        return repository.save(original.copy(
            description = body.description ?: original.description,
            status = body.status ?: original.description
        )).toDto()
    }
}
