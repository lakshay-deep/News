package com.lakshay.news.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaeger.library.StatusBarUtil
import com.lakshay.news.R
import com.lakshay.news.data.model.Status
import com.lakshay.news.databinding.ActivityMainBinding
import com.lakshay.news.ui.adapters.NewsAdapter
import com.lakshay.news.ui.viewModel.MainViewModel
import com.lakshay.news.util.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NewsAdapter.OnClickListener {

    private val newsAdapter by lazy { NewsAdapter(picasso) }

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var picasso: Picasso

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarColor()
        initializeWeather()
        initializeRecyclerView()
        getData()
        observeLiveData()
    }

    private fun setStatusBarColor() {
        StatusBarUtil.setColorNoTranslucent(
            this,
            ContextCompat.getColor(this, android.R.color.black)
        )
    }

    private fun initializeWeather() {
        picasso.load(Constants.WEATHER_LOGO)
            .error(R.drawable.ic_weather_placeholder)
            .placeholder(R.drawable.ic_weather_placeholder)
            .into(ivWeather)
    }

    private fun initializeRecyclerView() {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = newsAdapter
        newsAdapter.setOnClickListener(this)
    }

    private fun getData() {
        mainViewModel.getNews()
    }

    private fun observeLiveData() {
        mainViewModel.newsLiveData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.rvNews.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        binding.progressBar.visibility = View.GONE
                        binding.rvNews.visibility = View.VISIBLE
                        newsAdapter.submitList(it.articles.toList())
                    }
                }
                Status.ERROR -> {
                    binding.rvNews.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toasty.error(this, "${it.message}", Toasty.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onClick(position: Int, url: String?) {
        url?.let {
            startActivity(NewsDetailActivity.newIntent(this, url))
        }
    }
}