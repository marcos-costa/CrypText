package com.example.cryptext

import android.util.Log
import io.socket.client.Ack
import org.json.JSONObject
import java.math.BigInteger

class DiffieHellmanClient {

    private val socket = SocketHandler.getSocket()
    lateinit var sharedSecretServer: BigInteger

    fun startDiffieHellman() {
        try {
            val p: BigInteger = BigInteger("13")
            val g: BigInteger = BigInteger("6")

            val privateKey: BigInteger = BigInteger(256, java.util.Random())

            fun calculatePublicKey(): BigInteger {
                return g.modPow(privateKey, p)
            }

            fun calculateSharedSecret(serverPublicKey: BigInteger): BigInteger {
                return serverPublicKey.modPow(privateKey, p)
            }

            val publicKeyUser = calculatePublicKey()

            val payload = JSONObject()
            payload.put("p", p)
            payload.put("g", g)
            payload.put("userPublicKey", publicKeyUser)

            Log.d("diffie-hellman data", payload.toString())

            socket.emit("diffie-hellman", arrayOf(p, g, publicKeyUser), object : Ack {
                override fun call(vararg args: Any?) {
                    if (args.isNotEmpty() && args[0] is JSONObject) {
                        val response = args[0] as JSONObject
                        val publicKeyServer = BigInteger(response.getString("PublicKeyServer"))
                        Log.d("user public key",""+publicKeyUser)
                        Log.d("server key",""+publicKeyServer)
                        sharedSecretServer = calculateSharedSecret(publicKeyServer)

                        Log.d("shared key w server",""+sharedSecretServer)
                    }
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getServerSharedKey():BigInteger{
        return sharedSecretServer
    }

}
