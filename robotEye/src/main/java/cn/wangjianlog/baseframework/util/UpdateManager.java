package cn.wangjianlog.baseframework.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.File;

public class UpdateManager
{
  private static UpdateManager instance;
  private AsyncTask<Object, Object, Object> downloadTask;
  private String downloadUrl;

  public static UpdateManager getInstance()
  {
    if (instance == null)
      instance = new UpdateManager();
    return instance;
  }

  public void cancelDownloadAndroidApk()
  {
    if (this.downloadTask != null)
    {
      this.downloadTask.cancel(true);
      this.downloadTask = null;
    }
  }

  public AsyncTask<Object, Object, Object> downloadAndroidApk(File paramFile, String paramString, final UpdateCallBack paramUpdateCallBack, final UpdateProgress paramUpdateProgress)
  {
    if (this.downloadTask != null)
      return this.downloadTask;
    return new AsyncTask()
    {
      // ERROR //
      protected Object doInBackground(Object[] paramAnonymousArrayOfObject)
      {
		return paramAnonymousArrayOfObject;}

      protected void onPostExecute(Object paramAnonymousObject)
      {
        if ((paramAnonymousObject instanceof Exception))
        {
          paramUpdateCallBack.fail(paramAnonymousObject);
          return;
        }
        paramUpdateCallBack.success(paramAnonymousObject);
      }

      protected void onProgressUpdate(Object[] paramAnonymousArrayOfObject)
      {
        super.onProgressUpdate(paramAnonymousArrayOfObject);
        paramUpdateProgress.onProgressUpdate(paramAnonymousArrayOfObject);
      }
    }
    .execute(new Object[] { paramString, paramFile });
  }

  public void downloadAndroidApk(File paramFile, UpdateCallBack paramUpdateCallBack, UpdateProgress paramUpdateProgress)
  {
    this.downloadTask = downloadAndroidApk(paramFile, this.downloadUrl, paramUpdateCallBack, paramUpdateProgress);
  }

  public String getDownloadUrl()
  {
    return this.downloadUrl;
  }

  public void setDownloadUrl(String paramString)
  {
    this.downloadUrl = paramString;
  }

  public void showToast(Activity paramActivity, String paramString)
  {
    Toast.makeText(paramActivity, paramString, 1).show();
  }

  public static abstract interface UpdateCallBack
  {
    public abstract void fail(Object paramObject);

    public abstract void success(Object paramObject);
  }

  public static abstract interface UpdateProgress
  {
    public abstract void onProgressUpdate(Object[] paramArrayOfObject);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.util.UpdateManager
 * JD-Core Version:    0.6.2
 */