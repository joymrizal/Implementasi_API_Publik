package com.joymr.implementapipublik

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var ivPoster: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var tvRating: TextView
    private lateinit var etMovieId: EditText
    private lateinit var btnFetch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize all views
        ivPoster = findViewById(R.id.ivPoster)
        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        tvRating = findViewById(R.id.tvRating)
        etMovieId = findViewById(R.id.etMovieId)
        btnFetch = findViewById(R.id.btnFetch)

        // Set up button listeners
        btnFetch.setOnClickListener {
            val movieId = etMovieId.text.toString()
            if (movieId.isNotEmpty()) {
                fetchMovieData(movieId.toInt())
            }
        }
    }

    private fun fetchMovieData(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiKey = "2239085e752b0e0d176248ae920b95d1"
                val movie = RetrofitInstance.api.getMovieInfo(movieId, apiKey)

                if (movie != null) {
                    runOnUiThread {
                        tvTitle.text = movie.title
                        tvOverview.text = movie.overview
                        tvRating.text = "Rating: ${movie.vote_average}"

                        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
                        Glide.with(this@MainActivity).load(posterUrl).into(ivPoster)
                    }
                } else {
                    // Handle case where movie is not found
                    runOnUiThread {
                        tvTitle.text = "No Data Available"
                        tvOverview.text = ""
                        tvRating.text = ""

                        ivPoster.setImageResource(R.drawable.img_not_found) // Set your drawable resource
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    tvTitle.text = "No Data Available"
                    tvOverview.text = ""
                    tvRating.text = ""

                    ivPoster.setImageResource(R.drawable.img_not_found) // Set your drawable resource
                }
            }
        }
    }
}
