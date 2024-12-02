package com.example.cryptext.encrypt

import android.util.Log
import java.math.BigInteger
import java.security.SecureRandom

class DiffieHellman {

    private var TAG = "Diffie-Hellman"

    fun generatePG(): Pair<BigInteger, BigInteger>{
        val random = SecureRandom()

        val p: BigInteger = BigInteger.probablePrime(16, random)
        Log.d(TAG, "Random value generated of P: $p")
        val g: BigInteger = BigInteger(16, random)
        Log.d(TAG, "Random value generated of G: $g")


        return Pair(p, g)
    }

    fun calculatePublicKey(p: BigInteger, g: BigInteger, privateKey:BigInteger): BigInteger {
        val publicKey = g.modPow(privateKey, p)
        Log.d(TAG, "Value geterated of USER publicKey: $publicKey")
        return publicKey
    }

    fun calculateSharedSecret(p: BigInteger, privateKey: BigInteger, publicKey: BigInteger): BigInteger {
        val sharedKey = publicKey.modPow(privateKey, p)
        Log.d(TAG, "Value geterated of sharedKey: $sharedKey")
        return sharedKey
    }
}