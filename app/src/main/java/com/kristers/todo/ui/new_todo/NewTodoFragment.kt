package com.kristers.todo.ui.new_todo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kristers.todo.R
import com.kristers.todo.databinding.FragmentCreateNewBinding
import com.kristers.todo.objects.Todo
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NewTodoFragment : Fragment() {

    private val viewModel: NewTodoViewModel by viewModels()
    private var _binding: FragmentCreateNewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateNewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val importane = resources.getStringArray(R.array.importance)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, importane)
        binding.importanceDropdown.setAdapter(arrayAdapter)
        setHasOptionsMenu(true)

        binding.inDate.text = "Choose Date"

        binding.inDate.setOnClickListener {
            setDateDialog()
        }

        binding.btnSave.setOnClickListener {
            setTodo()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDateDialog() {
        val calendar = Calendar.getInstance()

        val dateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            sdf.format(calendar.time).also { binding.inDate.text = it }
        }
        DatePickerDialog(
            requireActivity(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTodo() {
        if (binding.title.text != null && binding.inDate.text.length> 1 && binding.inDate.text != "Choose Date" && binding.description.text != null) {
            val todo = Todo(
                UUID.randomUUID().toString(), "" + binding.title.text,
                SimpleDateFormat("yyyy-MM-dd").parse(binding.inDate.text.toString()).time,
                "" + binding.importanceDropdown.text,
                "" + binding.description.text,
                false,
                System.currentTimeMillis()
            )
            viewModel._todo.value = todo
            viewModel.saveTodo()
            goToMainFragment()
        } else {
            Snackbar.make(requireView(), "Not all fields are filled", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }
    }

    private fun goToMainFragment() {
        requireView().findNavController().navigate(R.id.action_nav_slideshow_to_nav_home)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_sortBy_date).isVisible = false
        menu.findItem(R.id.action_sortBy_importance).isVisible = false
        super.onCreateOptionsMenu(menu!!, inflater!!)
    }
}
