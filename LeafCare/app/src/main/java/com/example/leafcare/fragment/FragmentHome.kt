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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.leafcare.MainActivity
import com.example.leafcare.databinding.FragmentHomeBinding
import com.example.leafcare.viewmodel.FragmentViewModel

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainActivity: MainActivity
    val TAG = "testHome"

    private lateinit var viewModel: FragmentViewModel

    private val captureImageContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val bitmap = it.data?.extras?.get("data") as Bitmap
                binding.imgSearch.setImageBitmap(bitmap)
                viewModel.currentPictureEditBitmap(bitmap)
            }
        }

    private val getAlbumLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val img = activityResult.data?.data
            binding.imgSearch.setImageURI(img)
            try{
                viewModel.currentPictureEditUri(img!!)
            }
            catch (e: Exception){  }

        }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)
        binding.imgSearch.clipToOutline = true
        currentPictureSetImage()


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
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            captureImageContract.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "카메라 권한을 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        getAlbumLauncher.launch(intent)
    }

    private fun currentPictureSetImage() {
        when(viewModel.currentPictureSelect.value) {
            "bit" -> {
                binding.imgSearch.setImageBitmap(
                    viewModel.currentPictureBitmap.value
                )
            }
            "uri" -> {
                binding.imgSearch.setImageURI(
                    viewModel.currentPictureUri.value
                )
            }
            else -> {

            }
        }
    }


}