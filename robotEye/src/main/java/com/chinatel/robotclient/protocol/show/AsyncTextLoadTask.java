package com.chinatel.robotclient.protocol.show;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import java.io.BufferedReader;
import java.io.IOException;

public class AsyncTextLoadTask extends AsyncTask<Object, String, String>
{
  private BufferedReader br;
  private Context context;
  private Handler mhandler;

  public AsyncTextLoadTask(Context paramContext, BufferedReader paramBufferedReader, Handler paramHandler)
  {
    this.context = paramContext;
    this.br = paramBufferedReader;
    this.mhandler = paramHandler;
  }

  protected String doInBackground(Object[] paramArrayOfObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    while (true)
    {
      if (i < 50);
      try
      {
        String str = this.br.readLine();
        if (str == null)
          return localStringBuilder.toString();
        localStringBuilder.append(str + "\n");
        i++;
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
    }
  }

  protected void onPostExecute(String paramString)
  {
    super.onPostExecute(paramString);
    Message localMessage = new Message();
    localMessage.obj = paramString;
    localMessage.what = 1;
    this.mhandler.sendMessage(localMessage);
  }

  protected void onPreExecute()
  {
    super.onPreExecute();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.protocol.show.AsyncTextLoadTask
 * JD-Core Version:    0.6.2
 */