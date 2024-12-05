package com.example.cryptext.encrypt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

class tokenJWT {

    fun generateToken(): String {
        // A chave privada do app deve ser a mesma que a chave privada do servidor
        val secret = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0aW5obyIsImlhdCI6MTczMzM1MTQwNiwiZXhwIjoxNzMzMzU1MDA2fQ.YlOkE3-k6WXdL8ZQ_jr6KBW4UgfWleINWTXp7FXSn74"
        val algorithm = Algorithm.HMAC256(secret)

        val token = JWT.create()
            .withSubject("envio_token")
            .withIssuedAt(Date()) // Data de emissão
            .withExpiresAt(Date(System.currentTimeMillis() + 3600 * 1000)) // Expira em 1 hora
            .sign(algorithm)

        return token
    }

}