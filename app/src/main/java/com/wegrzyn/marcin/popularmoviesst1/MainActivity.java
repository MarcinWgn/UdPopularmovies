package com.wegrzyn.marcin.popularmoviesst1;

        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    MoviesAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new TestTask(this).execute();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(this,getResources().getInteger(R.integer.column_size));
        recyclerView.setLayoutManager(mLayoutManager);

    }


     private static class TestTask extends AsyncTask<Void,Void,Void> {

        private WeakReference<MainActivity> reference;

         TestTask(MainActivity context) {
             reference = new WeakReference<>(context);
         }
        @Override
        protected Void doInBackground(Void... voids) {

            NetworkUtils.getListFromHttp(NetworkUtils.POPULAR_QUERY);

            Log.d(NetworkUtils.TAG,NetworkUtils.moviesList.get(0).getTitle());
            Log.d(NetworkUtils.TAG,NetworkUtils.moviesList.get(0).getPosterLocalization());
            Log.d(NetworkUtils.TAG,NetworkUtils.moviesList.get(0).getVoteAverage());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            reference.get().adapter = new MoviesAdapter(reference.get(),NetworkUtils.moviesList);
            reference.get().recyclerView.setAdapter(reference.get().adapter);
        }
    }
}
