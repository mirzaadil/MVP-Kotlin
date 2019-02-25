package  com.kotlin.mirzaadil.mvparchitecture.net.exception


/**
 * @author Mirza Adil
 * desc:
 */
object ErrorStatus {
    /**
     * Successful response
     */
    @JvmField
    val SUCCESS = 0

    /**
     * unknown mistake
     */
    @JvmField
    val UNKNOWN_ERROR = 1002

    /**
     * Server internal error
     */
    @JvmField
    val SERVER_ERROR = 1003

    /**
     * Network connection timeout
     */
    @JvmField
    val NETWORK_ERROR = 1004

    /**
     * Other exceptions such as API parsing exceptions (or third-party data structure changes)
     */
    @JvmField
    val API_ERROR = 1005

}