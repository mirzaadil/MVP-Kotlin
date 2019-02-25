package  com.kotlin.mirzaadil.mvparchitecture.net.exception

/**
 * @author Mirza Adil
 * desc:
 */
class ApiException : RuntimeException {

    private var code: Int? = null


    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}