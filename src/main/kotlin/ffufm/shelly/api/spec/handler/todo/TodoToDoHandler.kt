package ffufm.shelly.api.spec.handler.todo

import com.fasterxml.jackson.module.kotlin.readValue
import de.ffuf.pass.common.handlers.PassMvcHandler
import ffufm.shelly.api.spec.dbo.todo.TodoToDoDTO
import kotlin.Int
import kotlin.Long
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.server.ResponseStatusException

interface TodoToDoDatabaseHandler {
    /**
     * Create ToDo: Creates a new ToDo object
     * HTTP Code 201: The created ToDo
     */
    suspend fun create(body: TodoToDoDTO, id: Long): TodoToDoDTO

    /**
     * Get all ToDos by page: Returns all ToDos from the system that the user has access to. The
     * Headers will include TotalElements, TotalPages, CurrentPage and PerPage to help with Pagination.
     * HTTP Code 200: List of ToDos
     */
    suspend fun getAll(maxResults: Int = 100, page: Int = 0): Page<TodoToDoDTO>

    /**
     * Delete ToDo by id.: Deletes one specific ToDo.
     */
    suspend fun remove(id: Long)

    /**
     * Update the ToDo: Updates an existing ToDo
     * HTTP Code 200: The updated model
     * HTTP Code 404: The requested object could not be found by the submitted id.
     * HTTP Code 422: On or many fields contains a invalid value.
     */
    suspend fun update(body: TodoToDoDTO, id: Long): TodoToDoDTO
}

@Controller("todo.ToDo")
class TodoToDoHandler : PassMvcHandler() {
    @Autowired
    lateinit var databaseHandler: TodoToDoDatabaseHandler

    /**
     * Create ToDo: Creates a new ToDo object
     * HTTP Code 201: The created ToDo
     */
    @RequestMapping(value = ["/todos/{id:\\d+}/"], method = [RequestMethod.POST])
    suspend fun create(@RequestBody body: TodoToDoDTO, @PathVariable id: Long): ResponseEntity<*> {
        body.validateOrThrow()
        return success { databaseHandler.create(body, id) }
    }

    /**
     * Get all ToDos by page: Returns all ToDos from the system that the user has access to. The
     * Headers will include TotalElements, TotalPages, CurrentPage and PerPage to help with Pagination.
     * HTTP Code 200: List of ToDos
     */
    @RequestMapping(value = ["/todos/"], method = [RequestMethod.GET])
    suspend fun getAll(@RequestParam("maxResults") maxResults: Int? = 100, @RequestParam("page")
            page: Int? = 0): ResponseEntity<*> {

        return paging { databaseHandler.getAll(maxResults ?: 100, page ?: 0) }
    }

    /**
     * Delete ToDo by id.: Deletes one specific ToDo.
     */
    @RequestMapping(value = ["/todos/{id:\\d+}/"], method = [RequestMethod.DELETE])
    suspend fun remove(@PathVariable("id") id: Long): ResponseEntity<*> {

        return success { databaseHandler.remove(id) }
    }

    /**
     * Update the ToDo: Updates an existing ToDo
     * HTTP Code 200: The updated model
     * HTTP Code 404: The requested object could not be found by the submitted id.
     * HTTP Code 422: On or many fields contains a invalid value.
     */
    @RequestMapping(value = ["/todos/{id:\\d+}/"], method = [RequestMethod.PUT])
    suspend fun update(@RequestBody body: TodoToDoDTO, @PathVariable("id") id: Long):
            ResponseEntity<*> {
        body.validateOrThrow()
        return success { databaseHandler.update(body, id) }
    }
}
