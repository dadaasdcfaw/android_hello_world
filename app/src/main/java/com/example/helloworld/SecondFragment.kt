package com.example.helloworld

import ApiClient.apiService
import BasicAuthInterceptor
import RetrofitClient.retrofit
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.helloworld.databinding.FragmentSecondBinding
import android.util.Log
import android.widget.Toast
import com.example.api.random_app_payload
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    val args: SecondFragmentArgs by navArgs()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val count = args.myArg

        val countText = getString(R.string.random_heading, count)
        view.findViewById<TextView>(R.id.textview_header).text = countText


        val random = java.util.Random()
        var randomNumber = 0
        if (count > 0) {
            randomNumber = random.nextInt(count)+1
        }
        view.findViewById<TextView>(R.id.textview_random).text = randomNumber.toString()

        binding.buttonSecond.setOnClickListener {
            val payload = random_app_payload(
                max_value = count.toString(),
                random_value = randomNumber.toString(),
            )
            //Send number to the api
            sendPostRequest(payload = payload)

            //Go back to first fragment
            val action = SecondFragmentDirections.actionSecondFragmentToFirstFragment(count)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendPostRequest(payload:random_app_payload) {
        val call = apiService.postMethod(payload)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val stringData = response.body()
                    // Handle successful response
                    showToast("Response: $stringData")
                } else {
                    // Handle error
                    showToast("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // Handle failure
                showToast( "Failed to fetch data: ${t.message}")
            }
        })
    }
    private fun showToast(message: String) {
        val activity = requireActivity()
        activity.runOnUiThread() {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }
}