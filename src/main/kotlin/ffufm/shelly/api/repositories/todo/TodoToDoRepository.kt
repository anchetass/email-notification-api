package ffufm.shelly.api.repositories.todo

import de.ffuf.pass.common.repositories.PassRepository
import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import kotlin.Long
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TodoToDoRepository : PassRepository<TodoToDo, Long> {
    @Query(
        "SELECT t from TodoToDo t LEFT JOIN FETCH t.user",
        countQuery = "SELECT count(id) FROM TodoToDo"
    )
    fun findAllAndFetchUser(pageable: Pageable): Page<TodoToDo>
}
