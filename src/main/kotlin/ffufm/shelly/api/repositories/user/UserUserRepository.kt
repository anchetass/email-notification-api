package ffufm.shelly.api.repositories.user

import de.ffuf.pass.common.repositories.PassRepository
import ffufm.shelly.api.spec.dbo.user.UserUser
import ffufm.shelly.api.spec.dbo.user.UserUserDTO
import kotlin.Long
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserUserRepository : PassRepository<UserUser, Long> {
    @Query(
        """SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END
            FROM UserUser u WHERE u.email = :email
        """
    )
    fun doesEmailExist(email: String): Boolean
}
