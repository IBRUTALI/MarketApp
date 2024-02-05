package com.ighorosipov.marketapp.data.mapper

import com.ighorosipov.marketapp.data.model.db.UserEntity
import com.ighorosipov.marketapp.domain.model.db.User

class UserMapper {

    fun mapUserToUserEntity(user: User): UserEntity = with(user) {
        UserEntity(
            id = id,
            firstname = firstname,
            lastname = lastname,
            number = number
        )
    }

    fun mapUserEntityToUser(userEntity: UserEntity): User = with(userEntity) {
        User(
            id = id,
            firstname = firstname,
            lastname = lastname,
            number = number
        )
    }

}