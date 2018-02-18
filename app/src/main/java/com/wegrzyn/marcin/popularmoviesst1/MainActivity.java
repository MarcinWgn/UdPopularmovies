package com.wegrzyn.marcin.popularmoviesst1;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.ImageView;

        import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView testView = findViewById(R.id.test_IW);

        Picasso.with(this)
                .load("http://dailydriver.pl/wp-content/uploads/2015/09/Ford-Focus-RS-III-2015_06.jpg")
                .into(testView);
    }

}
