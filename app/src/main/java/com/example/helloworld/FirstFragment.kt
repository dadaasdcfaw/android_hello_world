package com.example.helloworld

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.helloworld.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    val args: FirstFragmentArgs by navArgs()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init text view
        val count = args.myArg
        view.findViewById<TextView>(R.id.textview_first).text = count.toString()

        view.findViewById<Button>(R.id.random_button).setOnClickListener {
            val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
            val currentCount = showCountTextView.text.toString().toInt()
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(currentCount)
            findNavController().navigate(action)
        }

        // find the toast_button by its ID and set a click listener
        view.findViewById<Button>(R.id.toast_button).setOnClickListener {
            // create a Toast with some text, to appear for a short time
            val myToast = Toast.makeText(context, "Hello Toast!", Toast.LENGTH_SHORT)
            // show the Toast
            myToast.show()
        }

        view.findViewById<Button>(R.id.count_button).setOnClickListener {
            countMe(view)
        }

        // find the toast_button by its ID and set a click listener
        view.findViewById<Button>(R.id.reset_button).setOnClickListener {
            resetMe(view)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun countMe(view: View) {
        // Get the text view
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
        // Get the value of the text view.
        val countString = showCountTextView.text.toString()
        // Convert value to a number and increment it
        var count = countString.toInt()
        count++
        // Display the new value in the text view.
        showCountTextView.text = count.toString()
    }

    private fun resetMe(view: View) {
        // Get the text view
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
        showCountTextView.text = "0"
    }
}