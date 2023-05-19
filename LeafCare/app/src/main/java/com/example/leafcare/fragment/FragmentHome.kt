package com.example.leafcare.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.leafcare.MainActivity
import com.example.leafcare.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainActivity: MainActivity

    private val captureImageContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val bitmap = it.data?.extras?.get("data") as Bitmap
                binding.imgSearch.setImageBitmap(bitmap)
            }
        }

    private val getAlbumLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val img = activityResult.data?.data
            binding.imgSearch.setImageURI(img)
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
        binding.imgSearch.clipToOutline = true

        binding.btnCamera.setOnClickListener {
            callCamera()
        }

        binding.btnAlbum.setOnClickListener {
            getAlbum()
        }

        return binding.root
    }

    private fun callCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        captureImageContract.launch(intent)
    }

    private fun getAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        getAlbumLauncher.launch(intent)
    }


}