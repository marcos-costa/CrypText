package com.example.cryptext

import java.math.BigInteger
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


fun encryptBlowfish(key: BigInteger, message: String): String {

    val keyBytes = key.toByteArray()
    val secretKey = SecretKeySpec(keyBytes, "Blowfish")

    val cipher = Cipher.getInstance("Blowfish")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)

    val encryptedBytes = cipher.doFinal(message.toByteArray(Charsets.UTF_8))
    return java.util.Base64.getEncoder().encodeToString(encryptedBytes)
}

fun decryptBlowfish(key: BigInteger, encryptedMessage: String): String {

    val keyBytes = key.toByteArray()
    val secretKey = SecretKeySpec(keyBytes, "Blowfish")

    val cipher = Cipher.getInstance("Blowfish")
    cipher.init(Cipher.DECRYPT_MODE, secretKey)

    val encryptedBytes = java.util.Base64.getDecoder().decode(encryptedMessage)

    return String(cipher.doFinal(encryptedBytes), Charsets.UTF_8)
}

fun main() {
    val key = BigInteger("9")
    val message = "Esta é uma mensagem secreta!"

    val encryptedMessage = encryptBlowfish(key, message)
    println("Mensagem criptografada: $encryptedMessage")

    val decryptedMessage = decryptBlowfish(key, encryptedMessage)
    println("Mensagem descriptografada: $decryptedMessage")
}
