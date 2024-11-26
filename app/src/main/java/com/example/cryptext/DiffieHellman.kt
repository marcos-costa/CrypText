package com.example.cryptext

import android.util.Log
import io.socket.client.Ack
import io.socket.client.Socket
import org.json.JSONObject
import java.math.BigInteger
import java.security.SecureRandom

class DiffieHellmanClient (
    private val socket: Socket
) {

    lateinit var sharedSecretServer: BigInteger

    fun getServerSharedKey():BigInteger{
        return sharedSecretServer
    }

    fun generatePG(): Pair<BigInteger, BigInteger>{

        val random = SecureRandom()

        val p: BigInteger = BigInteger.probablePrime(16, random)
        val g: BigInteger = BigInteger(16, random)

        return Pair(p, g)
    }

    fun calculatePublicKey(p: BigInteger, g: BigInteger, privateKey:BigInteger): BigInteger {
        return g.modPow(privateKey, p)
    }

    fun calculateSharedSecret(p: BigInteger, privateKey: BigInteger, publicKey: BigInteger): BigInteger {
        return publicKey.modPow(privateKey, p)
    }

    fun startDiffieHellman(privateKey: BigInteger) {
        try {
            val (p, g) = generatePG()

            val publicKey = calculatePublicKey(p, g, privateKey)

            val payload = JSONObject()
            payload.put("p", p)
            payload.put("g", g)
            payload.put("userPublicKey", publicKey)

            Log.d("diffie-hellman data", payload.toString())

            socket.emit("diffie-hellman", arrayOf(p, g, publicKey), object : Ack {
                override fun call(vararg args: Any?) {
                    if (args.isNotEmpty() && args[0] is JSONObject) {
                        val response = args[0] as JSONObject
                        val publicKeyServer = BigInteger(response.getString("PublicKeyServer"))
                        Log.d("user public key","" + publicKey)
                        Log.d("server key","" + publicKeyServer)

                        sharedSecretServer = calculateSharedSecret(p, privateKey, publicKeyServer)

                        Log.d("shared key w server",""+sharedSecretServer)
                    }
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
