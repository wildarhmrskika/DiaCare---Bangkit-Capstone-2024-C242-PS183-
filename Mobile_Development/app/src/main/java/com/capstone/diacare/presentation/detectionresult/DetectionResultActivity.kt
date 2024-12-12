package com.capstone.diacare.presentation.detectionresult

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.capstone.diacare.R
import com.capstone.diacare.data.model.PredictionResult
import com.capstone.diacare.databinding.FragmentDetectionResultBinding
import com.capstone.diacare.utils.ResultPrecautionAndSolution
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class DetectionResultActivity : AppCompatActivity() {

    private lateinit var binding: FragmentDetectionResultBinding
    private val vm: DetectionResultViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetectionResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val result = intent.getParcelableExtra<PredictionResult>("result")
        val uri = intent.getStringExtra("uri")
        val date = LocalDate.now().toString()

        vm.initializeData(result, uri?.toUri())

        binding.tvResult.text = String.format(
            resources.getString(R.string.detection_result),
            vm.getResult()?.prediction?.jsonMemberClass ?: "no Data"
        )
        binding.tvAccuracy.text = String.format(
            resources.getString(R.string.detection_accuracy),
            vm.getResult()?.prediction?.confidence.toString() + "%"
        )
        binding.tvDate.text = String.format(
            resources.getString(R.string.detection_date),
            date
        )
        binding.imageViewDetection.setImageURI(vm.getUri())

        binding.tvRisk.text = if (vm.getResult()?.prediction?.jsonMemberClass == "diabetes") {
            ResultPrecautionAndSolution.getDiabetesRisk(
                vm.getResult()!!.prediction?.confidence ?: 0.0
            )
        } else {
            ResultPrecautionAndSolution.getHealthyRisk(
                vm.getResult()!!.prediction?.confidence ?: 0.0
            )
        }
        binding.tvPrecaution.text = if (vm.getResult()?.prediction?.jsonMemberClass == "diabetes") {
            ResultPrecautionAndSolution.getDiabetesPrecaution(
                vm.getResult()!!.prediction?.confidence ?: 0.0
            )
        } else {
            ResultPrecautionAndSolution.getHealthyPrecaution(
                vm.getResult()!!.prediction?.confidence ?: 0.0
            )
        }
        binding.tvSuggestion.text = if (vm.getResult()?.prediction?.jsonMemberClass == "diabetes") {
            ResultPrecautionAndSolution.getDiabetesSuggestion(
                vm.getResult()!!.prediction?.confidence ?: 0.0
            )
        } else {
            ResultPrecautionAndSolution.getHealthySuggestion(
                vm.getResult()!!.prediction?.confidence ?: 0.0
            )
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.saveHistoryButton.setOnClickListener {
            vm.insertHistory(date, vm.getUri()!!, prediction = result?.prediction!!)
        }

        vm.onSuccessAddHistory.observe(this) {
            if (it) {
                Toast.makeText(this, "Success Add to History!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}