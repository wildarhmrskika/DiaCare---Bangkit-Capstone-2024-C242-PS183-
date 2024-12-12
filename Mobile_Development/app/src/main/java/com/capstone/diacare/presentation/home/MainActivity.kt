package com.capstone.diacare.presentation.home

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.capstone.diacare.R
import com.capstone.diacare.databinding.ActivityMainBinding
import com.capstone.diacare.presentation.detectionresult.DetectionResultActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.time.Instant


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: HomeViewModel by viewModels()
    private lateinit var startCamera: ActivityResultLauncher<Intent>
    private lateinit var dialog: ProgressDialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        checkPermissions()
        setContentView(binding.root)

        dialog = ProgressDialog.show(this, "", "Loading")


        vm.predictionResult.observe(this) {
            val intent = Intent(this, DetectionResultActivity::class.java)
            intent.putExtra("result", it)
            intent.putExtra("uri", vm.savedImageUri.value.toString())
            startActivity(intent)
        }
        startCamera =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult: ActivityResult ->
                if (activityResult.resultCode == DetectionActivity.SECOND_ACTIVITY_RESULT_CODE) {
                    activityResult.data?.getStringExtra(DetectionActivity.SECOND_ACTIVITY_BUNDLE_ID)
                        ?.let {
                            val destinationUri =
                                Uri.fromFile(File(filesDir, "images${Instant.now()}.jpg"))
                            UCrop.of(it.toUri(), destinationUri)
                                .start(this)
                        }
                }
            }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        // Set default fragment
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Handle navigation item selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.navigation_galery -> {
                    replaceFragment(GalleryFragment())
                    true
                }

                R.id.navigation_history -> {
                    replaceFragment(HistoryFragment())
                    true
                }

                R.id.navigation_chat -> {
                    replaceFragment(ChatFragment())
                    true
                }

                else -> false
            }
        }
        binding.fab.setOnClickListener {
            val intent = Intent(
                this, DetectionActivity::class.java
            )
            startCamera.launch(intent)
        }

        vm.loadingState.observe(this) {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }

        }
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.navhost, fragment).commit()
    }

    private fun checkPermissions() {
        // For Android 13+ handle the new permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = arrayOf(
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_VIDEO,
                android.Manifest.permission.READ_MEDIA_AUDIO,
                android.Manifest.permission.CAMERA
            )
            requestPermissionsIfNeeded(permissions)
        } else {
            // For Android 12 and below
            val permissions = arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            )
            requestPermissionsIfNeeded(permissions)
        }
    }

    private fun requestPermissionsIfNeeded(permissions: Array<String>) {
        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            // Request permissions
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            // All permissions are granted
            onPermissionsGranted()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val deniedPermissions = permissions.filter { !it.value }.keys

        if (deniedPermissions.isEmpty()) {
            // All permissions are granted
            onPermissionsGranted()
        } else {
            // Some permissions are denied
            showPermissionRationale(deniedPermissions.toTypedArray())
        }
    }

    private fun showPermissionRationale(permissions: Array<String>) {
        AlertDialog.Builder(this).setTitle("Permission Required")
            .setMessage("The app needs these permissions to work properly. Please grant them.")
            .setPositiveButton("Allow") { _, _ ->
                requestPermissionLauncher.launch(permissions)
            }.setNegativeButton("Deny") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun onPermissionsGranted() {
        // Code to execute when all permissions are granted
        Toast.makeText(this, "All permissions granted!", Toast.LENGTH_SHORT).show()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            vm.saveUri(resultUri)
            supportFragmentManager.beginTransaction().replace(R.id.navhost, GalleryFragment())
                .commit()
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
        }
    }

}
