package com.example.randommemes

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currentImageUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        progressbar.visibility= View.VISIBLE
        nextButton.isEnabled=false
        shareButton.isEnabled=false
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
               currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility= View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false
                    }




                }).into(memeImageView)

            },
            Response.ErrorListener {
                Log.d("error",it.localizedMessage)
            })

MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    fun shareMeme(view: View) {
    val intent =  Intent(Intent.ACTION_SEND)
        intent.type="test/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey,checkout this cool meme i got from relieved app")
        val chooser=Intent.createChooser(intent,"share this meme using..")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()

    }
}