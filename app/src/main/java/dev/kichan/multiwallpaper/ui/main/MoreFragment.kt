package dev.kichan.multiwallpaper.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.kichan.multiwallpaper.BaseFragment
import dev.kichan.multiwallpaper.R
import dev.kichan.multiwallpaper.databinding.FragmentMoreBinding

class MoreFragment : BaseFragment<FragmentMoreBinding>(R.layout.fragment_more) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.run {
            btnMoreAppInfo.setOnClickListener {
                val url = "http://www.toss.me/바키찬희찬"
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(intent)
            }
        }

        return binding.root
    }
}