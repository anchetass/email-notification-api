package ffufm.shelly.api.spec.dbo.todo

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
import ffufm.shelly.api.spec.dbo.user.UserUser
import ffufm.shelly.api.spec.dbo.user.UserUserDTO
import ffufm.shelly.api.spec.dbo.user.UserUserSerializer
import java.util.TreeSet
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.Long
import kotlin.String
import kotlin.reflect.KClass
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.FetchMode
import org.springframework.beans.factory.getBeansOfType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Model for to do list
 */
@Entity(name = "TodoToDo")
@Table(name = "todo_todo")
data class TodoToDo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    /**
     * Description of the to do list
     */
    @Column(
        nullable = false,
        updatable = true,
        name = "description"
    )
    @Lob
    val description: String = "",
    /**
     * Status of the to do list
     */
    @Column(
        nullable = false,
        updatable = true,
        name = "status"
    )
    @Lob
    val status: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    val user: UserUser? = null
) : PassDTOModel<TodoToDo, TodoToDoDTO, Long>() {
    override fun toDto(): TodoToDoDTO = super.toDtoInternal(TodoToDoSerializer::class as
            KClass<PassDtoSerializer<PassDTOModel<TodoToDo, TodoToDoDTO, Long>, TodoToDoDTO, Long>>)

    override fun readId(): Long? = this.id

    override fun toString(): String = super.toString()
}

/**
 * Model for to do list
 */
data class TodoToDoDTO(
    val id: Long? = null,
    /**
     * Description of the to do list
     */
    val description: String? = "",
    /**
     * Status of the to do list
     */
    val status: String? = "",
    val user: UserUserDTO? = null
) : PassDTO<TodoToDo, Long>() {
    override fun toEntity(): TodoToDo = super.toEntityInternal(TodoToDoSerializer::class as
            KClass<PassDtoSerializer<PassDTOModel<TodoToDo, PassDTO<TodoToDo, Long>, Long>,
            PassDTO<TodoToDo, Long>, Long>>)

    override fun readId(): Long? = this.id
}

@Component
class TodoToDoSerializer : PassDtoSerializer<TodoToDo, TodoToDoDTO, Long>() {
    override fun toDto(entity: TodoToDo): TodoToDoDTO = cycle(entity) {
        TodoToDoDTO(
                id = entity.id,
        description = entity.description,
        status = entity.status,
        user = entity.user?.idDto() ?: entity.user?.toDto()
                )}

    override fun toEntity(dto: TodoToDoDTO): TodoToDo = TodoToDo(
            id = dto.id,
    description = dto.description ?: "",
    status = dto.status ?: "",
    user = dto.user?.toEntity()
            )
    override fun idDto(id: Long): TodoToDoDTO = TodoToDoDTO(
            id = id,
    description = null,
    status = null,

            )}

@Service("todo.TodoToDoValidator")
class TodoToDoValidator : PassModelValidation<TodoToDo> {
    override fun buildValidator(validatorBuilder: ValidatorBuilder<TodoToDo>):
            ValidatorBuilder<TodoToDo> = validatorBuilder.apply {
        konstraintOnObject(TodoToDo::user) {
            notNull()
        }
    }
}

@Service("todo.TodoToDoDTOValidator")
class TodoToDoDTOValidator : PassModelValidation<TodoToDoDTO> {
    override fun buildValidator(validatorBuilder: ValidatorBuilder<TodoToDoDTO>):
            ValidatorBuilder<TodoToDoDTO> = validatorBuilder.apply {
        konstraintOnObject(TodoToDoDTO::user) {
            notNull()
        }
    }
}
