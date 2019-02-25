 package  com.kotlin.mirzaadil.mvparchitecture.net


/**
 * @author Mirza Adil
 * Encapsulate returned data
 */
class BaseResponse<T>(val code :Int,
                      val msg:String,
                      val data:T)