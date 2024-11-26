package com.example.cryptext.data

import com.example.cryptext.data.entity.Friend
import com.example.cryptext.data.entity.Message
import com.example.cryptext.data.entity.User
import java.sql.Timestamp
import java.time.Instant

fun fakeData(database: AppDatabase){

    val friends = listOf( Friend(
                id = "10",
                name = "Marcos Costa",
                email = "marcos@mail.com",
                username = "@marcos",
                sharedKey = "3"
            ),
            Friend(
                id = "20",
                name = "Thiago Costa",
                email = "thiago@mail.com",
                username = "@thiago",
                sharedKey = "3"
            ),
            Friend(
                id = "30",
                name = "Luana Costa",
                email = "luana@mail.com",
                username = "@luana",
                sharedKey = "3"
            ),
        )

    val users = listOf(
        User(
            id = "11",
            name = "Noah Hitotuzi",
            email = "noah@mail.com",
            username = "@noah",
            p = "1",
            q = "2",
            publicKey = "3"
        ),
        User(
            id = "21",
            name = "Miguel Angelo",
            email = "miguel@mail.com",
            username = "@miguel",
            p = "1",
            q = "2",
            publicKey = "3"
        ),
        User(
            id = "31",
            name = "Valéria Ribeiro",
            email = "valeria@mail.com",
            username = "@valeria",
            p = "1",
            q = "2",
            publicKey = null
        ),
    )

    var messages = listOf(
        Message(
            friend = "@marcos",
            timestamp = Instant.parse("2024-11-26T12:00:00Z").toEpochMilli(),
            received = false,
            read = true,
            content = "Oi bom dia"
        ),
        Message(
            friend = "@marcos",
            timestamp = Instant.parse("2024-11-26T12:01:00Z").toEpochMilli(),
            received = false,
            read = false,
            content = "Oi bom dia"
        ),
        Message(
            friend = "@marcos",
            timestamp = Instant.parse("2024-11-26T12:02:00Z").toEpochMilli(),
            received = true,
            read = false,
            content = "Oi bom dia"
        ),
        Message(
            friend = "@luana",
            timestamp = Instant.parse("2024-11-26T12:03:00Z").toEpochMilli(),
            received = true,
            read = true,
            content = "Oi bom dia"
        ),
        Message(
            friend = "@luana",
            timestamp = Instant.parse("2024-11-26T12:04:00Z").toEpochMilli(),
            received = false,
            read = true,
            content = "Oi bom dia"
        ),
        Message(
            friend = "@luana",
            timestamp = Instant.parse("2024-11-26T12:05:00Z").toEpochMilli(),
            received = false,
            read = false,
            content = "Oi bom dia"
        ),
        Message(
            friend = "@luana",
            timestamp = Instant.parse("2024-11-26T12:06:00Z").toEpochMilli(),
            received = false,
            read = false,
            content = "Oi bom dia"
        )
    )

    database.friendDao().insertAll(friends)
    database.userDao().insertAll(users)
    database.messageDao().insertAll(messages)
}