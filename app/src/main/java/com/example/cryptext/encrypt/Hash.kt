package com.example.cryptext.encrypt

import android.util.Log
import java.security.MessageDigest


fun hashSenhaSHA256(senha: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(senha.toByteArray())
    val hashString = hash.joinToString("") { "%02x".format(it) }
    Log.d("HASH", "Senha original: $senha, Hash da senha: $hash")
    return hashString

}
