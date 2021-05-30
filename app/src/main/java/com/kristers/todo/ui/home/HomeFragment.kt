package com.kristers.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kristers.todo.R
import com.kristers.todo.databinding.FragmentHomeBinding
import com.kristers.todo.ui.recyclerView.CardRecyclerViewAdapter
import com.kristers.todo.ui.recyclerView.TodoGridItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    lateinit var adapter: CardRecyclerViewAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)

        viewModel.getAllPendingTodo()

        observeViewModel()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {

        viewModel.todoList.observe(
            requireActivity(),
            {
                binding.recyclerView.setHasFixedSize(true)
                binding.recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
                adapter =
                    viewModel.todoList.value?.let { it1 -> CardRecyclerViewAdapter(it1, requireView()) }!!
                binding.recyclerView.adapter = adapter
                val largePadding = 16
                val smallPadding = 8
                binding.recyclerView.addItemDecoration(TodoGridItemDecoration(largePadding, smallPadding))
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sortBy_date) {
            adapter.sortByDate()
            return true
        } else if (item.itemId == R.id.action_sortBy_importance) {
            adapter.sortByImportance()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
}
