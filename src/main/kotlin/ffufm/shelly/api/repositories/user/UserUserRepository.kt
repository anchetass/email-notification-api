package ffufm.shelly.api.repositories.user

import de.ffuf.pass.common.repositories.PassRepository
import ffufm.shelly.api.spec.dbo.user.UserUser
import kotlin.Long
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserUserRepository : PassRepository<UserUser, Long> {
    @Query(
        "SELECT t from UserUser t LEFT JOIN FETCH t.toDos",
        countQuery = "SELECT count(id) FROM UserUser"
    )
    fun findAllAndFetchToDos(pageable: Pageable): Page<UserUser>
}
