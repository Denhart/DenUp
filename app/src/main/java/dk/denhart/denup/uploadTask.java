package dk.denhart.denup;
/**
 *   Created by Denhart on 07-08-2014.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class uploadTask extends AsyncTask<String, String, String > {
    private Context mContext;
    private NotificationManager mNotificationManager;

    public uploadTask(Context context){
        this.mContext = context;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }
    @Override
    protected String doInBackground(String... uri) {
        URL url;
        HttpURLConnection conn;
        try{
            url=new URL(uri[0]);
            String param=uri[1];

            conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"\r\n");

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();
            String response= "";
            Scanner inStream = new Scanner(conn.getInputStream());

            while(inStream.hasNextLine())
                response+=(inStream.nextLine());
            return response;
        }

        catch(MalformedURLException ex){
            Log.i("Info", "Http error: " + ex.toString());
        }
        catch(IOException ex){
            Log.i("Info","Http error: " + ex.toString());
        }
        return "Error";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(result.contentEquals("Error")){
            createNotification(mContext.getString(R.string.upfailed),mContext.getString(R.string.swipe));
            Toast.makeText(mContext, mContext.getString(R.string.upfailed), Toast.LENGTH_LONG).show();
        }else {
            createNotification(mContext.getString(R.string.upcomp),mContext.getString(R.string.swipe));
        }
    }
    private void createNotification(String contentTitle, String contentText) {
        Notification.Builder builder = new Notification.Builder(mContext)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setVibrate(new long[]{100, 200, 100, 500});

        Notification mNotification = builder.build();
        int NOTIFICATION_ID = 1;
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }
}