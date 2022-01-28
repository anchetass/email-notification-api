package ffufm.shelly.api.spec.handler.user

import com.fasterxml.jackson.module.kotlin.readValue
import de.ffuf.pass.common.handlers.PassMvcHandler
import ffufm.shelly.api.spec.dbo.user.UserUserDTO
import kotlin.Long
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.server.ResponseStatusException

interface UserUserDatabaseHandler {
    /**
     * Create User: Creates a new User object
     * HTTP Code 201: The created User
     */
    suspend fun create(body: UserUserDTO): UserUserDTO

    /**
     * Delete User by id.: Deletes one specific User.
     */
    suspend fun remove(id: Long)

    /**
     * Update the User: Updates an existing User
     * HTTP Code 200: The updated model
     * HTTP Code 404: The requested object could not be found by the submitted id.
     * HTTP Code 422: On or many fields contains a invalid value.
     */
    suspend fun update(body: UserUserDTO, id: Long): UserUserDTO
}

@Controller("user.User")
class UserUserHandler : PassMvcHandler() {
    @Autowired
    lateinit var databaseHandler: UserUserDatabaseHandler

    /**
     * Create User: Creates a new User object
     * HTTP Code 201: The created User
     */
    @RequestMapping(value = ["/users/"], method = [RequestMethod.POST])
    suspend fun create(@RequestBody body: UserUserDTO): ResponseEntity<*> {
        body.validateOrThrow()
        return success { databaseHandler.create(body) }
    }

    /**
     * Delete User by id.: Deletes one specific User.
     */
    @RequestMapping(value = ["/users/{id:\\d+}/"], method = [RequestMethod.DELETE])
    suspend fun remove(@PathVariable("id") id: Long): ResponseEntity<*> {

        return success { databaseHandler.remove(id) }
    }

    /**
     * Update the User: Updates an existing User
     * HTTP Code 200: The updated model
     * HTTP Code 404: The requested object could not be found by the submitted id.
     * HTTP Code 422: On or many fields contains a invalid value.
     */
    @RequestMapping(value = ["/users/{id:\\d+}/"], method = [RequestMethod.PUT])
    suspend fun update(@RequestBody body: UserUserDTO, @PathVariable("id") id: Long):
            ResponseEntity<*> {
        body.validateOrThrow()
        return success { databaseHandler.update(body, id) }
    }
}
