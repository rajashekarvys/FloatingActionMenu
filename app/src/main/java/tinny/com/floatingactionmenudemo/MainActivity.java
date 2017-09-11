package tinny.com.floatingactionmenudemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;

import tinny.com.fabmenulib.FloatingMenuLib;
import tinny.com.fabmenulib.ImagesPojo;
import tinny.com.fabmenulib.OnFabItemClick;
import tinny.com.fabmenulib.OnFabOpenCloseListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingMenuLib floatingMenuLib = (FloatingMenuLib) findViewById(R.id.fab);
        final ArrayList<ImagesPojo> imagesPojos = new ArrayList<>();
        imagesPojos.add(new ImagesPojo(R.mipmap.call,"call"));
        imagesPojos.add(new ImagesPojo(R.mipmap.message));


        imagesPojos.add(new ImagesPojo(R.mipmap.whatsapp,"whatsApp",getResources().getColor(R.color.fab_background_1)));
        imagesPojos.add(new ImagesPojo(R.mipmap.message,"Message",getResources().getColor(R.color.fab_background_2)));

       floatingMenuLib.setUpFabMenu(imagesPojos);
      // floatingMenuLib.setFabBackgroundToMenuItems(imagesPojos);

        floatingMenuLib.removeBackGroundScreen(true);

        floatingMenuLib.setOnFabItemClick(new OnFabItemClick() {
            @Override
            public void onClikFab(int pos) {
                Toast.makeText(MainActivity.this, "Click on position "+ pos, Toast.LENGTH_SHORT).show();
            }
        });

        floatingMenuLib.setOnFabOpenCloseListener(new OnFabOpenCloseListener() {
            @Override
            public void onOpen() {
               // Toast.makeText(MainActivity.this, "Opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClose() {
                //Toast.makeText(MainActivity.this, "Closed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
