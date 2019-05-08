package com.software7.chatroom.utils

import android.util.Base64
import java.security.*
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec


object EndToEndEncrypt {


    val KEY_ALGORITHM = "RSA"
    val SIGNATURE_ALGORITHM = "MD5withRSA"

    private val PUBLIC_KEY = "RSAPublicKey"
    private val PRIVATE_KEY = "RSAPrivateKey"

    @Throws(Exception::class)
    fun decryptBASE64(key: String): ByteArray {
        return Base64.decode(key, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun encryptBASE64(key: ByteArray): String {
        return Base64.encodeToString(key, Base64.DEFAULT)
    }


    @Throws(Exception::class)
    fun sign(data: ByteArray, privateKey: String): String {

        val keyBytes = decryptBASE64(privateKey)

        val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)

        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)

        val priKey = keyFactory.generatePrivate(pkcs8KeySpec)

        val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
        signature.initSign(priKey)
        signature.update(data)

        return encryptBASE64(signature.sign())
    }


    @Throws(Exception::class)
    fun verify(data: ByteArray, publicKey: String, sign: String): Boolean {

        val keyBytes = decryptBASE64(publicKey)

        val keySpec = X509EncodedKeySpec(keyBytes)

        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)

        val pubKey = keyFactory.generatePublic(keySpec)

        val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
        signature.initVerify(pubKey)
        signature.update(data)

        return signature.verify(decryptBASE64(sign))
    }


    @Throws(Exception::class)
    fun decryptByPrivateKey(data: ByteArray, key: String): ByteArray {
        val keyBytes = decryptBASE64(key)

        val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val privateKey = keyFactory.generatePrivate(pkcs8KeySpec)

        val cipher = Cipher.getInstance(keyFactory.algorithm)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        return cipher.doFinal(data)
    }


    @Throws(Exception::class)
    fun decryptByPublicKey(data: ByteArray, key: String): ByteArray {
        val keyBytes = decryptBASE64(key)

        val x509KeySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val publicKey = keyFactory.generatePublic(x509KeySpec)

        val cipher = Cipher.getInstance(keyFactory.algorithm)
        cipher.init(Cipher.DECRYPT_MODE, publicKey)

        return cipher.doFinal(data)
    }


    @Throws(Exception::class)
    fun encryptByPublicKey(data: ByteArray, key: String): ByteArray {
        val keyBytes = decryptBASE64(key)

        val x509KeySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val publicKey = keyFactory.generatePublic(x509KeySpec)

        val cipher = Cipher.getInstance(keyFactory.algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        return cipher.doFinal(data)
    }


    @Throws(Exception::class)
    fun encryptByPrivateKey(data: ByteArray, key: String): ByteArray {
        val keyBytes = decryptBASE64(key)

        val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val privateKey = keyFactory.generatePrivate(pkcs8KeySpec)

        val cipher = Cipher.getInstance(keyFactory.algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)

        return cipher.doFinal(data)
    }

    @Throws(Exception::class)
    fun getPrivateKey(keyMap: Map<String, Any>): String {
        val key = keyMap[PRIVATE_KEY] as Key

        return encryptBASE64(key.encoded)
    }


    @Throws(Exception::class)
    fun getPublicKey(keyMap: Map<String, Any>): String {
        val key = keyMap[PUBLIC_KEY] as Key

        return encryptBASE64(key.encoded)
    }

    @Throws(Exception::class)
    fun initKey(): Map<String, Any> {
        val keyPairGen = KeyPairGenerator
            .getInstance(KEY_ALGORITHM)
        keyPairGen.initialize(1024)

        val keyPair = keyPairGen.generateKeyPair()

        val publicKey = keyPair.public as RSAPublicKey

        val privateKey = keyPair.private as RSAPrivateKey

        val keyMap = HashMap<String, Any>(2)

        keyMap[PUBLIC_KEY] = publicKey
        keyMap[PRIVATE_KEY] = privateKey
        return keyMap
    }



}