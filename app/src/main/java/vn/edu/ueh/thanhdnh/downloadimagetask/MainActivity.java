package vn.edu.ueh.thanhdnh.downloadimagetask;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  ImageView ivCover;
  ProgressBar pbLoading;
  Button btLoad;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    ivCover = findViewById(R.id.ivCover);
    pbLoading = findViewById(R.id.pbLoading);
    btLoad = findViewById(R.id.btLoad);
    btLoad.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String image1 = "https://live.staticflickr.com/4859/44173254600_3867a81ba3_k.jpg";
        String image2 = "https://images6.alphacoders.com/134/thumb-1920-1341420.png";
        String image3 = "https://images6.alphacoders.com/137/1377110.png";
        /*executor.execute(()->{
          File file = Downloader.downloadFile(imageUrl, getCacheDir());
          if(file!=null)
            runOnUiThread(()->{ivCover.setImageURI(Uri.fromFile(file));});
        });*/
        Handler mainHandler = new Handler(Looper.getMainLooper());
        pbLoading.setVisibility(ProgressBar.VISIBLE);
        Downloader.downloadWithProgress(image3, mainHandler, getBaseContext(), getExternalFilesDir(null), pbLoading, ivCover);
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    executor.shutdown();
  }

  @Override
  protected void onStart() {
    super.onStart();
    ivCover.setImageURI(Uri.parse(Downloader.cached_file_path));
  }
}
