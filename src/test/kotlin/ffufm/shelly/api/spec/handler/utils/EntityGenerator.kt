package ffufm.shelly.api.spec.handler.utils

import ffufm.shelly.api.spec.dbo.todo.TodoToDo
import ffufm.shelly.api.spec.dbo.user.UserUser
import ffufm.shelly.api.spec.dbo.user.UserUserDTO

object EntityGenerator {
    fun createUser(): UserUser= UserUser(
        name = "Brandon",
        email = "brandon@gmail.com"
    )
    fun createTodo(): TodoToDo= TodoToDo(
        description = "Assignment 1",
        status = "Pending"
    )
}