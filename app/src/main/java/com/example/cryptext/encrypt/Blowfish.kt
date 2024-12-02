package com.example.cryptext.encrypt

import java.math.BigInteger
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


fun encryptBlowfish(key: String, message: String): String {

    val keyBytes = key.toByteArray()
    val secretKey = SecretKeySpec(keyBytes, "Blowfish")

    val cipher = Cipher.getInstance("Blowfish")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)

    val encryptedBytes = cipher.doFinal(message.toByteArray(Charsets.UTF_8))
    return java.util.Base64.getEncoder().encodeToString(encryptedBytes)
}

fun decryptBlowfish(key: String, encryptedMessage: String): String {

    val keyBytes = key.toByteArray()
    val secretKey = SecretKeySpec(keyBytes, "Blowfish")

    val cipher = Cipher.getInstance("Blowfish")
    cipher.init(Cipher.DECRYPT_MODE, secretKey)

    val encryptedBytes = java.util.Base64.getDecoder().decode(encryptedMessage)

    return String(cipher.doFinal(encryptedBytes), Charsets.UTF_8)
}

fun main() {
    val key = "12345"
    val message = "Esta e uma mensagem secreta!"

    val encryptedMessage = encryptBlowfish(key, message)
    println("Mensagem criptografada: $encryptedMessage")

    val decryptedMessage = decryptBlowfish(key, encryptedMessage)
    println("Mensagem descriptografada: $decryptedMessage")
}
