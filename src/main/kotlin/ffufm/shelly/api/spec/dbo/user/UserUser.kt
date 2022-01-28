package ffufm.shelly.api.spec.dbo.user

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint
import am.ik.yavi.builder.konstraintOnObject
import de.ffuf.pass.common.models.PassDTO
import de.ffuf.pass.common.models.PassDTOModel
import de.ffuf.pass.common.models.PassDtoSerializer
import de.ffuf.pass.common.models.PassModelValidation
import de.ffuf.pass.common.models.idDto
import de.ffuf.pass.common.security.SpringContext
import de.ffuf.pass.common.utilities.extensions.konstraint
import de.ffuf.pass.common.utilities.extensions.toEntities
import de.ffuf.pass.common.utilities.extensions.toSafeDtos
import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import ffufm.shelly.api.spec.dbo.todo.TodoToDoDTO
import ffufm.shelly.api.spec.dbo.todo.TodoToDoSerializer
import java.util.TreeSet
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Lob
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlin.reflect.KClass
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.FetchMode
import org.springframework.beans.factory.getBeansOfType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Model for users
 */
@Entity(name = "UserUser")
@Table(name = "user_user")
data class UserUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    /**
     * Name of the user
     */
    @Column(
        nullable = false,
        updatable = true,
        name = "name"
    )
    @Lob
    val name: String = "",
    /**
     * Email of the user
     */
    @Column(
        nullable = false,
        updatable = true,
        name = "email"
    )
    @Lob
    val email: String = "",
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val toDos: List<TodoToDo>? = mutableListOf()
) : PassDTOModel<UserUser, UserUserDTO, Long>() {
    override fun toDto(): UserUserDTO = super.toDtoInternal(UserUserSerializer::class as
            KClass<PassDtoSerializer<PassDTOModel<UserUser, UserUserDTO, Long>, UserUserDTO, Long>>)

    override fun readId(): Long? = this.id

    override fun toString(): String = super.toString()
}

/**
 * Model for users
 */
data class UserUserDTO(
    val id: Long? = null,
    /**
     * Name of the user
     */
    val name: String? = "",
    /**
     * Email of the user
     */
    val email: String? = "",
    val toDos: List<TodoToDoDTO>? = null
) : PassDTO<UserUser, Long>() {
    override fun toEntity(): UserUser = super.toEntityInternal(UserUserSerializer::class as
            KClass<PassDtoSerializer<PassDTOModel<UserUser, PassDTO<UserUser, Long>, Long>,
            PassDTO<UserUser, Long>, Long>>)

    override fun readId(): Long? = this.id
}

@Component
class UserUserSerializer : PassDtoSerializer<UserUser, UserUserDTO, Long>() {
    override fun toDto(entity: UserUser): UserUserDTO = cycle(entity) {
        UserUserDTO(
                id = entity.id,
        name = entity.name,
        email = entity.email,
        toDos = entity.toDos?.toSafeDtos()
                )}

    override fun toEntity(dto: UserUserDTO): UserUser = UserUser(
            id = dto.id,
    name = dto.name ?: "",
    email = dto.email ?: "",
    toDos = dto.toDos?.toEntities() ?: emptyList()
            )
    override fun idDto(id: Long): UserUserDTO = UserUserDTO(
            id = id,
    name = null,
    email = null,

            )}

@Service("user.UserUserValidator")
class UserUserValidator : PassModelValidation<UserUser> {
    override fun buildValidator(validatorBuilder: ValidatorBuilder<UserUser>):
            ValidatorBuilder<UserUser> = validatorBuilder.apply {
    }
}

@Service("user.UserUserDTOValidator")
class UserUserDTOValidator : PassModelValidation<UserUserDTO> {
    override fun buildValidator(validatorBuilder: ValidatorBuilder<UserUserDTO>):
            ValidatorBuilder<UserUserDTO> = validatorBuilder.apply {
    }
}
