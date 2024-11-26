package com.example.cryptext

import java.security.KeyFactory
import java.security.KeyPair

import android.util.Base64
import android.util.Log
import io.socket.client.Ack
import org.json.JSONObject
import java.math.BigInteger
import java.security.KeyPairGenerator
import javax.crypto.KeyAgreement
import javax.crypto.interfaces.DHPublicKey
import javax.crypto.spec.DHParameterSpec
import javax.crypto.spec.DHPublicKeySpec

class DiffieHellmanClient {

    private val socket = SocketHandler.getSocket()
    private lateinit var clientKeyPair: java.security.KeyPair
    private lateinit var sharedSecret: ByteArray

    fun startDiffieHellman() {
        try {
            // Parâmetros p (número primo grande) e g (gerador)
            val p = BigInteger(
                "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1" +
                        "29024E088A67CC74020BBEA63B139B22514A08798E3404DD" +
                        "EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245" +
                        "E485B576625E7EC6F44C42E9A63A36210000000000090563", 16
            )
            val g = BigInteger("2") // Gerador padrão para DH

            // Gera a chave usando os parâmetros p e g
            val dhSpec = DHParameterSpec(p, g)
            val keyPairGenerator = KeyPairGenerator.getInstance("DH")
            keyPairGenerator.initialize(dhSpec)
            clientKeyPair = keyPairGenerator.generateKeyPair()

            // Obtém a chave pública do cliente em Base64
            val publicKeyBase64 = Base64.encodeToString(clientKeyPair.public.encoded, Base64.NO_WRAP)

            // Converte p e g para Base64
            val pBase64 = Base64.encodeToString(p.toByteArray(), Base64.NO_WRAP)
            val gBase64 = Base64.encodeToString(g.toByteArray(), Base64.NO_WRAP)

            // Envia os valores p, g e a chave pública para o servidor
            val payload = JSONObject()
            payload.put("p", pBase64)
            payload.put("g", gBase64)
            payload.put("userPublicKey", publicKeyBase64)

            // Log para depuração
            Log.d("diffie-hellman data", payload.toString())

            // Envia os dados para o servidor via WebSocket
            socket.emit("diffie-hellman", arrayOf(pBase64, gBase64, publicKeyBase64), object : Ack {
                override fun call(vararg args: Any?) {
                    if (args.isNotEmpty() && args[0] is JSONObject) {
                        val response = args[0] as JSONObject
                        handleServerResponse(response)
                    }
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleServerResponse(data: JSONObject) {
        try {
            // Recebe a chave pública do servidor em Base64
            val serverPublicKeyBase64 = data.getString("PublicKeyServer")
            Log.d("diffie json received", "" + data.getString("PublicKeyServer"))
            val serverPublicKeyBytes = Base64.decode(serverPublicKeyBase64, Base64.NO_WRAP)

            // Converte os bytes da chave pública do servidor diretamente para uma chave pública DH
            val keyFactory = KeyFactory.getInstance("DH")
            val serverPublicKeySpec = DHPublicKeySpec(
                BigInteger(1, serverPublicKeyBytes),
                (clientKeyPair.public as DHPublicKey).params.p,
                (clientKeyPair.public as DHPublicKey).params.g
            )
            val serverPublicKey = keyFactory.generatePublic(serverPublicKeySpec) as DHPublicKey

            // Gera a chave secreta compartilhada
            val keyAgreement = KeyAgreement.getInstance("DH")
            keyAgreement.init(clientKeyPair.private)
            keyAgreement.doPhase(serverPublicKey, true)
            sharedSecret = keyAgreement.generateSecret()

            // Exibe a chave secreta compartilhada em Base64
            println("Chave secreta compartilhada (Base64): ${Base64.encodeToString(sharedSecret, Base64.NO_WRAP)}")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSharedKeyServer(): String {
        // Retorna a chave secreta compartilhada em Base64
        return Base64.encodeToString(sharedSecret, Base64.NO_WRAP)
    }

    // Converte uma string Base64 para um array de bytes
    private fun base64ToByteArray(base64: String): ByteArray {
        return Base64.decode(base64, Base64.NO_WRAP)
    }

    // Converte um array de bytes para Base64
    private fun bytesToBase64(bytes: ByteArray): String {
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }
}
