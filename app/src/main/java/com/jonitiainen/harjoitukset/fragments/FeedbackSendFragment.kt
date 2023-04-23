package com.jonitiainen.harjoitukset.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonitiainen.harjoitukset.databinding.FragmentFeedbackSendBinding

class FeedbackSendFragment : Fragment() {
    private var _binding: FragmentFeedbackSendBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedbackSendBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}