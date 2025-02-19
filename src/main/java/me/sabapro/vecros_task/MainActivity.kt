package me.sabapro.vecros_task

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import me.sabapro.vecros_task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.exploreBT.setOnClickListener {
            val input = binding.urlET.text.toString().trim()
            if(input.isEmpty())
            {
                closeKeyBoard()
                binding.urlET.background = ContextCompat.getDrawable(this,R.drawable.alert_bg)
                Toast.makeText(this,"Enter the link",Toast.LENGTH_SHORT).show()
            }
            else
            {
                closeKeyBoard()
                if(!isValidRtspUrl(input))
                {
                    Toast.makeText(this, "Invalid RTSP URL", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    val intent = Intent(this,MainActivity2::class.java)
                    intent.putExtra("URL",input)
                    startActivity(intent)
                }
            }
        }
    }

    private fun isValidRtspUrl(url: String) : Boolean {
        return url.startsWith("rtsp://", ignoreCase = true)
    }

    private fun closeKeyBoard()
    {
        val close_KB = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        close_KB.hideSoftInputFromWindow(binding.urlET.windowToken,0)
    }

}