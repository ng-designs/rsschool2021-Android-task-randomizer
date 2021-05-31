package com.rsschool.android2021

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var iListener: FragmentInterface? = null
    private var errorText = "Ошибка:"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val minInputValue = view.findViewById<EditText>(R.id.min_value)
        val maxInputValue = view.findViewById<EditText>(R.id.max_value)

        // TODO: val min = ...
        // TODO: val max = ...

        generateButton?.setOnClickListener {
            val min = checkInputValues(minInputValue, "Min")
            val max = checkInputValues(maxInputValue, "Max")

            if (min != -1 && max != -1) {
                if (max < min) {
                    errorText = "${errorText}\nЧисло Min больше чем Max"
                }else if(min == max){
                    errorText = "${errorText}\nВведённые значения равны. Введите различные значения."
                } else iListener?.generateRandom(min, max)
            }
            if (errorText.isNotBlank()) {
                Toast.makeText(activity, errorText.trim(), Toast.LENGTH_SHORT).show()
            }
            errorText = ""

            // TODO: send min and max to the SecondFragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        iListener = context as FragmentInterface
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    private fun checkInputValues(inputText:EditText, str:String):Int{
        var result:Int?
        if (inputText.text.isNotBlank()){
            result = inputText.text.toString().toIntOrNull()
            if (result == null || result < 0){
                errorText = "${errorText}\nНедопустимое значение $str"
                result = -1
            }
        }else{
            errorText = "${errorText}\nПоле $str пустое"
            result = -1
        }
        return result
    }
}