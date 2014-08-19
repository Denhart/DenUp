package dk.denhart.denup;
/**
 *   Created by Denhart on 07-08-2014.
 */
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import org.apache.commons.codec.binary.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String action = intent.getAction();

        // if this is from the share menu
        if (Intent.ACTION_SEND.equals(action)) {
            if (extras.containsKey(Intent.EXTRA_STREAM)) {
                try {
                    // Get resource path from intent callee
                    Uri uri = extras.getParcelable(Intent.EXTRA_STREAM);
                    ContentResolver cr = getContentResolver();
                    InputStream is = cr.openInputStream(uri);
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String type = mime.getExtensionFromMimeType(cr.getType(uri));
                    String data_string = new String(Base64.encodeBase64(getBytesFromFile(is)));
                    String urlPath = getString(R.string.path, getString(R.string.key))+type;

                    new uploadTask(this).execute(urlPath, data_string);
                    Toast.makeText(getApplicationContext(), getString(R.string.uploading),Toast.LENGTH_LONG).show();
                    super.finish();
                    return;
                } catch (Exception e) {
                    Log.e(this.getClass().getName(), e.toString());
                }
            } else if (extras.containsKey(Intent.EXTRA_TEXT)) {
                return;
            }
        }
        setContentView(R.layout.activity_my);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public static byte[] getBytesFromFile(InputStream is) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            Log.e("Info", e.toString());
            return null;
        }
    }
}