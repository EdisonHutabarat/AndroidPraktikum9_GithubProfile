package id.ac.polbeng.edisonrizal.githubprofile.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import id.ac.polbeng.edisonrizal.githubprofile.GlideApp
import id.ac.polbeng.edisonrizal.githubprofile.R
import id.ac.polbeng.edisonrizal.githubprofile.databinding.ActivityMainBinding
import id.ac.polbeng.edisonrizal.githubprofile.helpers.Config
import id.ac.polbeng.edisonrizal.githubprofile.models.GithubUser
import id.ac.polbeng.edisonrizal.githubprofile.services.GithubUserService
import id.ac.polbeng.edisonrizal.githubprofile.services.ServiceBuilder
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import id.ac.polbeng.edisonrizal.githubprofile.viewmodels.MainViewModel
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        mainViewModel.githubUser.observe(this) { user ->
            setUserData(user)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.btnSearchUserLogin.setOnClickListener {
            val userLogin = binding.etSearchUserLogin.text.toString()
            var query = Config.DEFAULT_USER_LOGIN
            if (userLogin.isNotEmpty()) {
                query = userLogin
            }
            mainViewModel.searchUser(query)
        }
    }
    private fun setUserData(githubUser: GithubUser) {
        binding.tvUser.text = githubUser.toString()
        GlideApp.with(applicationContext)
            .load(githubUser.avatarUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.baseline_broken_image_24))
            .into(binding.imgUser)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}