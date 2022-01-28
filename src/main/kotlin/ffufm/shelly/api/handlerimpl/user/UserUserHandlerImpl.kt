package ffufm.shelly.api.handlerimpl.user

import de.ffuf.pass.common.handlers.PassDatabaseHandler
import de.ffuf.pass.common.utilities.extensions.orElseThrow404
import ffufm.shelly.api.repositories.user.UserUserRepository
import ffufm.shelly.api.spec.dbo.user.UserUser
import ffufm.shelly.api.spec.handler.user.UserUserDatabaseHandler
import kotlin.Long
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("user.UserUserHandler")
class UserUserHandlerImpl : PassDatabaseHandler<UserUser, UserUserRepository>(),
        UserUserDatabaseHandler {
    /**
     * Create User: Creates a new User object
     * HTTP Code 201: The created User
     */
    override suspend fun create(body: UserUser): UserUser {
        TODO("not checked yet")
        return repository.save(body)
    }

    /**
     * Delete User by id.: Deletes one specific User.
     */
    override suspend fun remove(id: Long): UserUser {
        val original = repository.findById(id).orElseThrow404(id)
        TODO("not checked yet - update the values you really want updated")
        return repository.delete(original)
    }

    /**
     * Update the User: Updates an existing User
     * HTTP Code 200: The updated model
     * HTTP Code 404: The requested object could not be found by the submitted id.
     * HTTP Code 422: On or many fields contains a invalid value.
     */
    override suspend fun update(body: UserUser, id: Long): UserUser {
        val original = repository.findById(id).orElseThrow404(id)
        TODO("not checked yet - update the values you really want updated")
        return repository.save(original)
    }
}
