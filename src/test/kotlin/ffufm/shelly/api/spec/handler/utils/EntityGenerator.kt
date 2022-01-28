package ffufm.shelly.api.spec.handler.utils

import ffufm.shelly.api.spec.dbo.user.UserUser

object EntityGenerator {
    fun createUser(): UserUser = UserUser(
        name = "Brandon",
        email = "brandon@gmail.com"
    )
}