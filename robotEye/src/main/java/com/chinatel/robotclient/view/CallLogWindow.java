package com.chinatel.robotclient.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class CallLogWindow extends PopupWindow
  implements View.OnClickListener
{
  private ListViewCallLog all_call_listview;
  private Button allcall_btn;
  private ImageButton close_calllog_btn;
  private View conentView;
  private Button delete_cancle;
  private Button delete_ok;
  private Activity mActivity;
  private Button misscall_btn;

  public CallLogWindow(Activity paramActivity)
  {
    this.mActivity = paramActivity;
    this.conentView = ((LayoutInflater)paramActivity.getSystemService("layout_inflater")).inflate(2130903050, null);
    setContentView(this.conentView);
    initView();
    setup();
    selectPage(0);
  }

  private void closeWindow()
  {
    if (isShowing())
      dismiss();
  }

/*  private void getData()
  {
    List localList = select();
    this.all_call_listview.setData(localList);
  }

  private void getDataMissCall()
  {
    List localList = selectMissCall();
    this.all_call_listview.setData(localList);
  }*/

  private void hideDeleteBtn()
  {
    this.delete_cancle.setVisibility(4);
    this.delete_ok.setVisibility(4);
  }

  private void initView()
  {
    this.allcall_btn = ((Button)this.conentView.findViewById(2131230807));
    this.misscall_btn = ((Button)this.conentView.findViewById(2131230808));
    this.all_call_listview = ((ListViewCallLog)this.conentView.findViewById(2131230813));
    this.delete_cancle = ((Button)this.conentView.findViewById(2131230809));
    this.delete_ok = ((Button)this.conentView.findViewById(2131230810));
    this.close_calllog_btn = ((ImageButton)this.conentView.findViewById(2131230814));
    this.close_calllog_btn.setOnClickListener(this);
    this.delete_cancle.setOnClickListener(this);
    this.delete_ok.setOnClickListener(this);
    this.all_call_listview.setCallWindow(this);
    this.allcall_btn.setOnClickListener(this);
    this.misscall_btn.setOnClickListener(this);
  }

 /* private List<MyCallLog> select()
  {
    List localList = null;
    try
    {
      localList = new CallLogDao(this.mActivity, MyCallLog.class).list();
      sort(localList);
      return localList;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    return localList;
  }
*/
 /* private List<MyCallLog> selectMissCall()
  {
    List localList = null;
    try
    {
      localList = new CallLogDao(this.mActivity, MyCallLog.class).list("status", Integer.valueOf(0));
      sort(localList);
      return localList;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    return localList;
  }*/

  private void selectPage(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      this.allcall_btn.setBackgroundResource(2130837562);
      this.misscall_btn.setBackgroundResource(2130837564);
//      getData();
      return;
    case 1:
    }
    this.allcall_btn.setBackgroundResource(2130837561);
    this.misscall_btn.setBackgroundResource(2130837565);
//    getDataMissCall();
  }

  private void setup()
  {
    setWidth(-1);
    setHeight(-1);
    setFocusable(true);
    setOutsideTouchable(true);
    update();
    setBackgroundDrawable(new ColorDrawable(0));
    setAnimationStyle(2131361803);
  }

  public void cancalDelete()
  {
    this.all_call_listview.cancalDelete();
  }

  public void cancleDelete()
  {
    this.all_call_listview.cancalDelete();
    hideDeleteBtn();
  }

  public Activity getmActivity()
  {
    return this.mActivity;
  }

  public void insert()
  {
    try
    {
//      CallLogDao localCallLogDao = new CallLogDao(this.mActivity, MyCallLog.class);
//      localCallLogDao.add(new MyCallLog(Integer.valueOf(0), "头像", "小优", "00:10:16", "15-4-29", "09:23", Integer.valueOf(0), Integer.valueOf(0)));
//      localCallLogDao.add(new MyCallLog(Integer.valueOf(0), "头像", "小优2", "00:10:17", "15-5-01", "09:24", Integer.valueOf(0), Integer.valueOf(0)));
//      localCallLogDao.add(new MyCallLog(Integer.valueOf(1), "头像", "小优3", "00:10:18", "15-5-03", "09:25", Integer.valueOf(0), Integer.valueOf(1)));
//      localCallLogDao.add(new MyCallLog(Integer.valueOf(0), "头像", "小优4", "00:10:19", "15-5-02", "09:26", Integer.valueOf(0), Integer.valueOf(1)));
//      localCallLogDao.add(new MyCallLog(Integer.valueOf(1), "头像", "小优5", "00:10:120", "15-5-03", "09:27", Integer.valueOf(0), Integer.valueOf(0)));
//      localCallLogDao.add(new MyCallLog(Integer.valueOf(0), "头像", "小优6", "00:10:21", "15-5-05", "09:28", Integer.valueOf(0), Integer.valueOf(1)));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131230811:
    case 2131230812:
    case 2131230813:
    default:
      return;
    case 2131230807:
      selectPage(0);
      return;
    case 2131230808:
      selectPage(1);
      return;
    case 2131230809:
      cancleDelete();
      return;
    case 2131230810:
      this.all_call_listview.delectSelected();
      hideDeleteBtn();
      return;
    case 2131230814:
    }
    closeWindow();
  }

  public void setmActivity(Activity paramActivity)
  {
    this.mActivity = paramActivity;
  }

  public void show(View paramView, int paramInt1, int paramInt2)
  {
    if (!isShowing())
    {
      showAsDropDown(paramView, paramInt1, paramInt2);
      return;
    }
    dismiss();
  }

  public void showDeleteBtn()
  {
    this.delete_cancle.setVisibility(0);
    this.delete_ok.setVisibility(0);
  }

  /*public void sort(List<MyCallLog> paramList)
  {
    Collections.sort(paramList, new Comparator<MyCallLog>()
    {
      public int compare(MyCallLog paramAnonymousMyCallLog1, MyCallLog paramAnonymousMyCallLog2)
      {
        Date localDate1 = DateUtil.getDateByString(paramAnonymousMyCallLog1.getDate() + " " + paramAnonymousMyCallLog1.getTime(), "yyyy-MM-dd HH:mm");
        Date localDate2 = DateUtil.getDateByString(paramAnonymousMyCallLog2.getDate() + " " + paramAnonymousMyCallLog2.getTime(), "yyyy-MM-dd HH:mm");
        int i = 0;
        if (localDate1 != null)
        {
          i = 0;
          if (localDate2 != null)
            i = -localDate1.compareTo(localDate2);
        }
        return i;
      }
    });
  }*/
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.view.CallLogWindow
 * JD-Core Version:    0.6.2
 */