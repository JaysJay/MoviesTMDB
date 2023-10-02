package com.sanstv.movies.ui.cast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialFadeThrough
import com.sanstv.movies.R
import com.sanstv.movies.data.model.MediaType
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.databinding.FragmentListBinding
import com.sanstv.movies.ui.cast.adapter.CastDataAdapter
import com.sanstv.movies.ui.image.BigImageViewModel
import com.sanstv.movies.ultility.ext.navigateSafe
import com.sanstv.movies.ultility.state.Resource

class CastsFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<CastsFragmentArgs>()

    private val bigImageViewModel by activityViewModels<BigImageViewModel>()
    private val castViewModel by activityViewModels<CastViewModel>()

    private var hasLoaded: Boolean = false

    private val castDataAdapter by lazy {
        CastDataAdapter(
            setOnClickListener = { navigateMedia(it) },
            expandBiography = {},
            setOnImageClick = { url, iv ->
                openImageFullSize(url, iv)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        setupCastViewModel(args.cast.id)
    }

    private fun openImageFullSize(posterPath: String?, imageView: ImageView) {
        imageView.transitionName = posterPath
        this.exitTransition = null
        bigImageViewModel.setImagePath(posterPath)

        val action = CastsFragmentDirections.actionCastsFragmentToBigImageFragment()
        val extras = FragmentNavigatorExtras(
            imageView to imageView.transitionName
        )
        findNavController().navigate(action, extras)
        Log.d("navigateToMedia", imageView.transitionName)
    }

    private fun navigateMedia(media: Media) {
        val action = CastsFragmentDirections.actionCastsFragmentToFragmentMedia(
            media.copy(media_type = media.media_type ?: MediaType.movie)
        )
        findNavController().navigateSafe(action)
    }

    private fun setupCastViewModel(personId: Int) {
        if (!hasLoaded) {
            castViewModel.getPerson(personId)
        }
        castViewModel.personResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> response.data?.let {
                        TransitionManager.beginDelayedTransition(
                            binding.root,
                            MaterialFadeThrough()
                        )
                        castDataAdapter.submitList(it)
                        isLoading(false)
                        hasLoaded = true
                    }
                    is Resource.Error -> {
                        showSnackBar(response.message)
                        binding.rvList.isInvisible = true
                    }
                    is Resource.Loading -> if (!hasLoaded) {
                        isLoading(true)
                    }
                }
            }
        }
    }

    private fun isLoading(hide: Boolean) {
        binding.apply {
            loading.isInvisible = !hide
            rvList.isInvisible = hide
        }
    }

    private fun setupRecyclerView() {
        binding.rvList.apply {
            adapter = castDataAdapter
            layoutManager = LinearLayoutManager(
                activity, LinearLayoutManager.VERTICAL, false
            )
        }
    }

    private fun showSnackBar(message: String?) {
        Snackbar.make(
            binding.root,
            message ?: resources.getString(R.string.something_went_wrong),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rvList.adapter = null
        _binding = null
    }

    companion object {
        const val TAG = "CastsFragment"
    }

}