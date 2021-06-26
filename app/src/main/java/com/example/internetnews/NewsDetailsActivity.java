package com.example.internetnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    String title,desc,content, imageURl, url;
    private TextView titletv, contentTv, subDesctv;
    private ImageView newsIV;
    private Button readNewsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageURl = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        titletv = findViewById(R.id.idTVNews);
        contentTv = findViewById(R.id.idTVContent);
        subDesctv = findViewById(R.id.idTVSubDesc);
        newsIV = findViewById(R.id.idIVNewsDetails);
        readNewsBtn = findViewById(R.id.idBtnReadNews);

        titletv.setText(title);
        contentTv.setText(content);
        subDesctv.setText(desc);
        Picasso.get().load(imageURl).into(newsIV);


        readNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });



    }
}