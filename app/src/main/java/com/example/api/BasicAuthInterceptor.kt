import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(private val username: String, private val password: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val credentials = "$username:$password"
        val basic = "Basic " + android.util.Base64.encodeToString(credentials.toByteArray(), android.util.Base64.NO_WRAP)
        val request = chain.request().newBuilder()
            .header("Authorization", basic)
            .build()
        return chain.proceed(request)
    }
}
