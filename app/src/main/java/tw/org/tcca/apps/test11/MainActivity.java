package tw.org.tcca.apps.test11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {
    private File sdroot, imgFile;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }else{
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }

    private void init(){
        sdroot = Environment.getExternalStorageDirectory();
        imgFile = new File(sdroot, "android.png");
        if (imgFile.exists()){
            Log.v("bradlog", "file ok");
        }

        webView = findViewById(R.id.webview);
        initWebView();
    }

    private void initWebView(){
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setDisplayZoomControls(false);

//        String html = "<h1>Brad Big Company</h1>";
//        webView.loadData(html, "text/html", "UTF-8");

        byte[] imgBytes = new byte[(int)imgFile.length()];
        try(FileInputStream fin = new FileInputStream(imgFile)){
            fin.read(imgBytes);
            String imgBase64 = Base64.encodeToString(imgBytes, Base64.DEFAULT);
            String html = "<img src='data:image/png; base64, " + imgBase64 + "' />";
            webView.loadData(html, "text/html", "UTF-8");
        }catch (Exception e){

        }



    }

}