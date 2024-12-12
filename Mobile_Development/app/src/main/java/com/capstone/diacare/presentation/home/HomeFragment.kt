package com.capstone.diacare.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.capstone.diacare.R
import com.capstone.diacare.databinding.FragmentHomeBinding
import com.capstone.diacare.presentation.news.NewsActivity
import com.yalantis.ucrop.UCrop
import java.io.File
import java.time.Instant


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm: HomeViewModel by activityViewModels()
    private lateinit var launcher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var startCamera: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initializeImagePicker()

        initializeCamera()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeCamera() {
        startCamera =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult: ActivityResult ->
                if (activityResult.resultCode == DetectionActivity.SECOND_ACTIVITY_RESULT_CODE) {
                    activityResult.data?.getStringExtra(DetectionActivity.SECOND_ACTIVITY_BUNDLE_ID)
                        ?.let {
                            val destinationUri =
                                Uri.fromFile(File(activity?.filesDir, "images${Instant.now()}.jpg"))
                            UCrop.of(it.toUri(), destinationUri)
                                .start(requireActivity())
                        }
                }
            }
    }

    private fun initializeImagePicker() {
        launcher = registerForActivityResult<PickVisualMediaRequest, Uri>(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                vm.saveUri(uri)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.navhost, GalleryFragment())?.commit()
                Log.d("PICK IMAGE", "onCreateView: ${uri.toString()}")
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCamera.setOnClickListener {
            val intent = Intent(
                requireContext(),
                DetectionActivity::class.java
            )
            startCamera.launch(intent)
        }
        binding.photoPickerArea.setOnClickListener {
            launcher.launch(
                PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly).build()
            )
        }
        binding.newsButton.setOnClickListener {
            val intent = Intent(requireContext(), NewsActivity::class.java)
            startActivity(intent)
        }
        binding.chatBotBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.navhost, ChatFragment())?.commit()
        }


    }

}


