package  com.kotlin.mirzaadil.mvparchitecture.net.exception

import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * @author Mirza Adil
 * desc: Exception handling class

 */

class ExceptionHandle {


    companion object {
        var errorCode = ErrorStatus.UNKNOWN_ERROR
        var errorMsg = "Request failed, please try again later"

        fun handleException(e: Throwable): String {
            e.printStackTrace()
            if (e is SocketTimeoutException) {//network timeout
                Logger.e("TAG", "Abnormal network connection: " + e.message)
                errorMsg = "Abnormal network connection"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is ConnectException) { //Are considered network errors
                Logger.e("TAG", "Abnormal network connection: " + e.message)
                errorMsg = "Abnormal network connection"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException
            ) {   //Are considered parsing errors
                Logger.e("TAG", "Abnormal network connection: " + e.message)
                errorMsg = "Abnormal network connection"
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is ApiException) {//Error message returned by the server
                errorMsg = e.message.toString()
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is UnknownHostException) {
                Logger.e("TAG", "Abnormal network connection: " + e.message)
                errorMsg = "Abnormal network connection"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is IllegalArgumentException) {
                errorMsg = "Parameter error"
                errorCode = ErrorStatus.SERVER_ERROR
            } else {//unknown mistake
                try {
                    Logger.e("TAG", "error: " + e.message)
                } catch (e1: Exception) {
                    Logger.e("TAG", "Unknown error Debug debugging")
                }

                errorMsg = "Unknown error, may be anchored~"
                errorCode = ErrorStatus.UNKNOWN_ERROR
            }
            return errorMsg
        }

    }


}
