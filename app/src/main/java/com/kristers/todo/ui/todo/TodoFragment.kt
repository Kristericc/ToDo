package com.kristers.todo.ui.todo

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.kristers.todo.R
import com.kristers.todo.databinding.FragmentTodoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TodoFragment : Fragment() {
    private val viewModel: TodoViewModel by viewModels()
    private var _binding: FragmentTodoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)

        if (arguments != null) {
            val todoJson: String? = arguments?.getString("todo")
            viewModel._todo.value = todoJson?.let { Json.decodeFromString(it) }
            setView()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setView() {
        binding.title.text = viewModel.todo.value!!.title
        binding.importance.text = viewModel.todo.value!!.importance

        val date = Date(viewModel.todo.value!!.date)
        val df2 = SimpleDateFormat("yyyy-MM-dd")
        val dateText: String = df2.format(date)

        binding.date.text = dateText
        binding.description.text = viewModel.todo.value!!.description

        if (!viewModel.todo.value!!.done) {
            binding.todoBtn.text = "Completed"
            binding.todoBtn.setOnClickListener {
                viewModel.completeTodo()
                requireView().findNavController().navigate(R.id.nav_home)
            }
        } else {
            binding.todoBtn.text = "Delete"
            binding.todoBtn.setOnClickListener {
                viewModel.deleteTodo()
                requireView().findNavController().navigate(R.id.nav_completed)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_sortBy_date).isVisible = false
        menu.findItem(R.id.action_sortBy_importance).isVisible = false
        super.onCreateOptionsMenu(menu!!, inflater!!)
    }
}
