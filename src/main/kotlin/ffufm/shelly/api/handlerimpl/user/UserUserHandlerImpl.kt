package ffufm.shelly.api.handlerimpl.user

import de.ffuf.pass.common.handlers.PassDatabaseHandler
import de.ffuf.pass.common.utilities.extensions.orElseThrow404
import ffufm.shelly.api.repositories.user.UserUserRepository
import ffufm.shelly.api.spec.dbo.user.UserUser
import ffufm.shelly.api.spec.dbo.user.UserUserDTO
import ffufm.shelly.api.spec.handler.user.UserUserDatabaseHandler
import kotlin.Long
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component("user.UserUserHandler")
class UserUserHandlerImpl : PassDatabaseHandler<UserUser, UserUserRepository>(),
        UserUserDatabaseHandler {
    /**
     * Create User: Creates a new User object
     * HTTP Code 201: The created User
     */
    override suspend fun create(body: UserUserDTO): UserUserDTO {

        val bodyEntity = body.toEntity()

         //Check if the email already exist in the db
        if (repository.doesEmailExist(bodyEntity.email))
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email ${body.email} already exist.")

        //save the user using the  UserRepository
        return repository.save(bodyEntity).toDto()
    }

    /**
     * Delete User by id.: Deletes one specific User.
     */
    override suspend fun remove(id: Long) {
        // Check if the email already exist in the db
        val original = repository.findById(id).orElseThrow404(id)

        return repository.delete(original)
    }

    /**
     * Update the User: Updates an existing User
     * HTTP Code 200: The updated model
     * HTTP Code 404: The requested object could not be found by the submitted id.
     * HTTP Code 422: On or many fields contains a invalid value.
     */
    override suspend fun update(body: UserUserDTO, id: Long): UserUserDTO {
        // To check if the id is existing in the databases
        val original = repository.findById(id).orElseThrow404(id)

        val bodyEntity = body.toEntity()

        //save the updated data using the UserRepository
        return repository.save(original.copy(
            name = bodyEntity.name,
            email = bodyEntity.email
        )).toDto()
    }
}
