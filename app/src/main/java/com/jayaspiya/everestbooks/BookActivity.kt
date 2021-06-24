package com.jayaspiya.everestbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jayaspiya.everestbooks.model.Book

class BookActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvBookPrice: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivBook: ImageView
    private lateinit var btnAddToCart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        tvTitle = findViewById(R.id.tvTitle)
        tvBookPrice = findViewById(R.id.tvBookPrice)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvDescription = findViewById(R.id.tvDescription)
        ivBook = findViewById(R.id.ivBook)
        btnAddToCart = findViewById(R.id.btnAddToCart)

        val book: Book? = intent.getParcelableExtra("book")

//        val bookMap = mapOf(
//            "title" to "A Man with One of Those Faces",
//            "price" to 450,
//            "author" to "Caimh Mcdonnell",
//            "description" to "The first time somebody tried to kill him was an accident. The second time was deliberate. Now Paul Mulchrone finds himself on the run with nobody to turn to except a nurse who has read one-too-many crime novels and a renegade copper with a penchant for violence. Together they must solve one of the most notorious crimes in Irish history or else they’ll be history.",
//            "imageUrl" to "https://silly-visvesvaraya-f21a81.netlify.app/assets/a-man-with-one-of-those-faces--front.png"
//        )

        if (book != null) {
            tvTitle.text = book.title.toString()
            tvBookPrice.text = "Rs." + book.price.toString()
            tvAuthor.text = book.author.toString()
            tvDescription.text = book.synopsis.toString()
            Glide.with(this)
                .load(book.imageUrl.toString())
                .into(ivBook)
        }

    }
}