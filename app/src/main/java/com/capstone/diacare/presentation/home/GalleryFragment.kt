package com.capstone.diacare.presentation.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.capstone.diacare.databinding.FragmentGaleryBinding
import com.capstone.diacare.utils.uriToMultipartBodyPart


class GalleryFragment : Fragment() {
    private var _binding: FragmentGaleryBinding? = null
    private val vm: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private lateinit var launcher: ActivityResultLauncher<PickVisualMediaRequest>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGaleryBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launcher = registerForActivityResult<PickVisualMediaRequest, Uri>(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            vm.saveUri(uri)

        }
        vm.savedImageUri.observe(requireActivity()) { uri ->
            context?.let { ctx ->
                val glide = Glide.with(ctx)
                glide.clear(binding.imageArea)
                glide.load(uri).into(binding.imageArea)
            }

        }
        binding.analyzeBtn.setOnClickListener {
            val uri = vm.savedImageUri.value
            if (uri != null) {
                try {
                    Log.d("Analyze Button", "onViewCreated: uri is $uri")
                    val mp = uriToMultipartBodyPart(requireContext(), uri, "file")
                    if (mp==null){
                        Log.d("Analyze Button", "multipart is null")
                    }else {
                        vm.analyze(mp)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "error : ${e.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Log.d("Analyze Button", "onViewCreated: NULL on uri")
            }

        }
        binding.galeryPickBtn.setOnClickListener {
            launcher.launch(
                PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly).build()
            )
        }
    }
}
