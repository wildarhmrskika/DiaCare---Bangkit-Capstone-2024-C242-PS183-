package com.capstone.diacare.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diacare.adapter.AdapterHistory
import com.capstone.diacare.data.model.PredictionEntity
import com.capstone.diacare.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val vm: HomeViewModel by activityViewModels()
    private var historyData: ArrayList<PredictionEntity> = arrayListOf()
    private lateinit var adapter: AdapterHistory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        adapter = AdapterHistory(historyData) { item ->
            vm.deleteHistory(item)
        }
        binding.historyRecyclerView.adapter = adapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getAllHistory()
        vm.historyData.observe(requireActivity()) {
            historyData.clear()
            historyData.addAll(it)
            adapter.notifyDataSetChanged()
        }
        vm.onDeleteSuccess.observe(requireActivity()){
//            Toast.makeText(requireActivity(), "Success Delete", Toast.LENGTH_SHORT).show()
            vm.getAllHistory()
        }
    }
}