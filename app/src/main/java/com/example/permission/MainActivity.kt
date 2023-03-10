package com.example.permission

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.permission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pickImage = 100
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPermission()
    }

    private fun setPermission() {
        binding.permissionButton.setOnClickListener {
            when {
                checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    resultLauncher.launch(intent)
                }
                shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) -> {}
                else -> {
                    requestPermissions(
                        arrayOf(READ_EXTERNAL_STORAGE),
                        pickImage
                    )
                }
            }
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                imageUri = data?.data!!
                binding.imageView.setImageURI(imageUri)
            }
        }
}