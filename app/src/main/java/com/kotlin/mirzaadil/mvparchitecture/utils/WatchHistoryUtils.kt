package com.kotlin.mirzaadil.mvparchitecture.utils

import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import android.util.Base64
import java.io.*


/**
 * @author Mirza Adil
 * desc:Kotlin delegate attribute +SharedPreference instance (recording video watch record)
 */
class WatchHistoryUtils {

    companion object {
        /**
         * File name saved in the phone
         */
        private val FILE_NAME = "kotlin_mvp_file"

        /**
         * To save the data, we need to get the specific type of the saved data, and then call different save methods according to the type.*
         * @param context
         * @param key
         * @param object
         */
        fun put(context: Context, key: String, `object`: Any) {

            val sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE)
            val editor = sp.edit()

            when (`object`) {
                is String -> editor.putString(key, `object`)
                is Int -> editor.putInt(key, `object`)
                is Boolean -> editor.putBoolean(key, `object`)
                is Float -> editor.putFloat(key, `object`)
                is Long -> editor.putLong(key, `object`)
                else -> editor.putString(key, `object`.toString())
            }

            SharedPreferencesCompat.apply(editor)
        }

        /**
         * To get the method of saving the data, we get the specific type of the saved data according to the default value, and then call the method relative to the method to get the value.
         *
         * @param context
         * @param key
         * @param defaultObject
         * @return
         */
        operator fun get(context: Context, key: String, defaultObject: Any): Any? {
            val sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE)

            return when (defaultObject) {
                is String -> sp.getString(key, defaultObject)
                is Int -> sp.getInt(key, defaultObject)
                is Boolean -> sp.getBoolean(key, defaultObject)
                is Float -> sp.getFloat(key, defaultObject)
                is Long -> sp.getLong(key, defaultObject)
                else -> null
            }

        }

        /**
         * Remove the value that a key value already corresponds to
         *
         * @param context
         * @param key
         */
        fun remove(context: Context, key: String) {
            val sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.remove(key)
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * Clear all data
         *
         * @param context
         */
        fun clear(context: Context) {
            val sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.clear()
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * Query whether a key has been saved
         *
         * @param context
         * @param key
         * @return
         */
        fun contains(context: Context, key: String): Boolean {
            val sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE)
            return sp.contains(key)
        }

        /**
         * Return all key-value pairs
         *
         * @param context
         * @return
         */
        fun getAll(context: Context): Map<String, *> {
            val sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE)
            return sp.all
        }


        /**
         * Create a compatibility class that resolves the SharedPreferencesCompat.apply method
         *
         * @author Mirza ADil
         */
        private object SharedPreferencesCompat {
            private val sApplyMethod = findApplyMethod()

            /**
             * Reflection method for finding apply
             *
             * @return
             */
            private fun findApplyMethod(): Method? {
                try {
                    val clz = SharedPreferences.Editor::class.java
                    return clz.getMethod("apply")
                } catch (e: NoSuchMethodException) {
                }

                return null
            }

            /**
             * If you find it, use apply, otherwise use commit
             *
             * @param editor
             */
            fun apply(editor: SharedPreferences.Editor) {
                try {
                    if (sApplyMethod != null) {
                        sApplyMethod.invoke(editor)
                        return
                    }
                } catch (e: IllegalArgumentException) {
                } catch (e: IllegalAccessException) {
                } catch (e: InvocationTargetException) {
                }

                editor.commit()
            }
        }

        /*****************Variable file name for storing various history********/


        /**
         * To save the data, we need to get the specific type of the saved data, and then call different save methods according to the type.
         *
         * @param context
         * @param key
         * @param object
         */
        fun put(fileName: String, context: Context, key: String, `object`: Any) {

            val sp = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE)
            val editor = sp.edit()

            when (`object`) {
                is String -> editor.putString(key, `object`)
                is Int -> editor.putInt(key, `object`)
                is Boolean -> editor.putBoolean(key, `object`)
                is Float -> editor.putFloat(key, `object`)
                is Long -> editor.putLong(key, `object`)
                else -> editor.putString(key, `object`.toString())
            }

            SharedPreferencesCompat.apply(editor)
        }

        /**
         * To get the method of saving the data, we get the specific type of the saved data according to the default value, and then call the method relative to the method to get the value.
         *
         * @param context
         * @param key
         * @param defaultObject
         * @return
         */
        operator fun get(fileName: String, context: Context, key: String, defaultObject: Any): Any? {
            val sp = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE)

            return when (defaultObject) {
                is String -> sp.getString(key, defaultObject)
                is Int -> sp.getInt(key, defaultObject)
                is Boolean -> sp.getBoolean(key, defaultObject)
                is Float -> sp.getFloat(key, defaultObject)
                is Long -> sp.getLong(key, defaultObject)
                else -> null
            }

        }

        /**
         * Remove the value that a key value already corresponds to
         *
         * @param context
         * @param key
         */
        fun remove(fileName: String, context: Context, key: String) {
            val sp = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.remove(key)
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * Clear all data
         *
         * @param context
         */
        fun clear(fileName: String, context: Context) {
            val sp = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.clear()
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * Query if a key already exists
         *
         * @param context
         * @param key
         * @return
         */
        fun contains(fileName: String, context: Context, key: String): Boolean {
            val sp = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE)
            return sp.contains(key)
        }

        /**
         * Return all key-value pairs
         *
         * @param context
         * @return
         */
        fun getAll(fileName: String, context: Context): Map<String, *> {
            val sp = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE)
            return sp.all
        }


        fun putObject(fileName: String,context: Context, `object`: Any?,
                      key: String): Boolean {
            val sp = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE)
            if (`object` == null) {
                val editor = sp.edit().remove(key)
                return editor.commit()
            }
            val baos = ByteArrayOutputStream()
            var oos: ObjectOutputStream?
            try {
                oos = ObjectOutputStream(baos)
                oos.writeObject(`object`)
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }

            // Put the object in the OutputStream
            // Convert the object to a byte array and base64 encode it
            val objectStr = String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT))
            try {
                baos.close()
                oos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val editor = sp.edit()
            editor.putString(key, objectStr)
            return editor.commit()
        }

        /**
         * To get the method of saving the data, we get the specific type of the saved data according to the default value, and then call the method relative to the method to get the value.
         *
         * @param context
         * @param key
         * @return
         */
        fun getObject(fileName: String,context: Context, key: String): Any? {
            val sharePre = context.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE)
            try {
                val wordBase64 = sharePre.getString(key, "")
                // Restore base64 format string to byte array
                if (wordBase64 == null || wordBase64 == "") { // Indispensable, otherwise java.io.StreamCorruptedException will be reported below.

                    return null
                }
                val objBytes = Base64.decode(wordBase64.toByteArray(),
                        Base64.DEFAULT)
                val bais = ByteArrayInputStream(objBytes)
                val ois = ObjectInputStream(bais)
                // Convert byte array to product object
                val obj = ois.readObject()
                bais.close()
                ois.close()
                return obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * Serialized object

         * @param person
         * *
         * @return
         * *
         * @throws IOException
         */
        @Throws(IOException::class)
        private fun <A> serialize(obj: A): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(
                    byteArrayOutputStream)
            objectOutputStream.writeObject(obj)
            var serStr = byteArrayOutputStream.toString("ISO-8859-1")
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
            objectOutputStream.close()
            byteArrayOutputStream.close()
            return serStr
        }

        /**
         * Deserialized object


         * @param str
         * *
         * @return
         * *
         * @throws IOException
         * *
         * @throws ClassNotFoundException
         */
        @Suppress("UNCHECKED_CAST")
        @Throws(IOException::class, ClassNotFoundException::class)
        private fun <A> deSerialization(str: String): A {
            val redStr = java.net.URLDecoder.decode(str, "UTF-8")
            val byteArrayInputStream = ByteArrayInputStream(
                    redStr.toByteArray(charset("ISO-8859-1")))
            val objectInputStream = ObjectInputStream(
                    byteArrayInputStream)
            val obj = objectInputStream.readObject() as A
            objectInputStream.close()
            byteArrayInputStream.close()
            return obj
        }


    }


}
