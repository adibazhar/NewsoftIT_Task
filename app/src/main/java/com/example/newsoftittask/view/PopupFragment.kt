package com.example.newsoftittask.view

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsoftittask.R
import com.example.newsoftittask.data.PostViewModel
import com.example.newsoftittask.model.Post
import kotlinx.android.synthetic.main.fragment_popup.*


private const val ARG_PARAM1 = "param1"

class PopupFragment : DialogFragment() {

    private var param1: Post? = null
    private lateinit var viewmodel : PostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val post = param1!!
        viewmodel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

        textInput_title.editText!!.setText(post.title)
        textInput_message.editText!!.setText(post.message)

        btn_update.setOnClickListener {
            updatePost(post)
        }


    }

    fun updatePost(post: Post){
        val newTitle = textInput_title.editText!!.text.toString()
        val newText = textInput_message.editText!!.text.toString()
        post.title = newTitle
        post.message = newText
        viewmodel.updatePosts(post.id,post)

        viewmodel.updateResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(activity,"Successfully update information",Toast.LENGTH_SHORT).show()
                updateResult_layout.visibility = View.VISIBLE
                textview_newTitle.text = it.title
                textview_newMessage.text = it.message

            }
        })
    }

    override fun onResume() {
        super.onResume()

        val window = dialog!!.window
        val size = Point()

        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)

        val width = size.x
        val height = size.y

        window.setLayout(width,(height*0.75).toInt())
        window.setGravity(Gravity.CENTER)
    }

    companion object {

        @JvmStatic
        fun newInstance(args1: Post) =
            PopupFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1,args1)
                }
            }
    }
}