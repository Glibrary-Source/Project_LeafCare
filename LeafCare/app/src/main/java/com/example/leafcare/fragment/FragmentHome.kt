package com.example.leafcare.fragment

import android.Manifest
import android.Manifest.permission_group.STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.leafcare.MainActivity
import com.example.leafcare.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainActivity: MainActivity

    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    private val CAMERA_CODE = 98
    private val STORAGE_CODE = 99

    private val captureImageContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the captured image result here
                val intentData: Intent? = result.data
                val imageUri: Uri? = intentData?.data
                Log.d("testTAG", "$imageUri")
                if(imageUri != null) {
                    binding.imgSearch.setImageURI(imageUri)
                    Log.d("testTAG", "$imageUri")
                }

            } else {
                Log.d("testTAG", "데이터 uri 없음")
                // Handle the case where image capture was canceled or failed
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)

        binding.btnCamera.setOnClickListener {
            callCamera()
        }

        binding.btnAlbum.setOnClickListener {
            getAlbum()
        }

        return binding.root
    }

    private fun callCamera() {
        try{
            val itt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            captureImageContract.launch(itt)
        }
        catch (e: Exception) {
            ActivityCompat.requestPermissions(mainActivity, CAMERA, CAMERA_CODE)
        }
    }

    private fun getAlbum() {
        val itt = Intent(Intent.ACTION_PICK)
        itt.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(itt, STORAGE_CODE)
    }

}