package com.chinatel.robotclient.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.wangjianlog.baseframework.tools.StringUtils;

public class ListViewCallLog extends ListView
{
  private MyAdapter adapter;
  private CallLogWindow callWindow;
  private boolean deleteStatus = false;
  private LayoutInflater inflater;
//  private List<MyCallLog> listItems = new ArrayList();
//private List<MyCallLog> listItems;

  public ListViewCallLog(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.inflater = LayoutInflater.from(paramContext);
    this.adapter = new MyAdapter();
    setAdapter(this.adapter);
  }

  public void cancalDelete()
  {
    this.deleteStatus = false;
    notifyDataSetChanged();
  }

  public void delectSelected()
  {}

  public CallLogWindow getCallWindow()
  {
    return this.callWindow;
  }

  public void notifyDataSetChanged()
  {
    this.adapter.notifyDataSetChanged();
  }

//  public void removeListElement(List<MyCallLog> paramList)
//  {
//    Iterator localIterator = paramList.iterator();
//    while (true)
//    {
//      if (!localIterator.hasNext())
//        return;
//      if (((MyCallLog)localIterator.next()).getIsChecked().intValue() == 1)
//        localIterator.remove();
//    }
//  }

  public void setCallWindow(CallLogWindow paramCallLogWindow)
  {
    this.callWindow = paramCallLogWindow;
  }

//  public void setData(List<MyCallLog> paramList)
//  {
//    this.listItems.clear();
//    this.listItems.addAll(paramList);
//    notifyDataSetChanged();
//  }

  public void setDeleteing()
  {
    this.deleteStatus = true;
    if (this.callWindow != null)
      this.callWindow.showDeleteBtn();
  }

  private class Holder
  {
    public ImageView avatarView;
    public ImageView callInOrOutView;
    public CheckBox checkBoxView;
    public TextView dateView;
    public TextView durationView;
    public TextView nameView;
    public TextView timeView;

    private Holder()
    {
    }
  }

  private class MyAdapter extends BaseAdapter
  {
    private MyAdapter()
    {
    }

    public int getCount()
    {
		return 0;
//      return ListViewCallLog.this.listItems.size();
    }

    public Object getItem(int paramInt)
    {
      return null;
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
//      MyCallLog localMyCallLog = (MyCallLog)ListViewCallLog.this.listItems.get(paramInt);
      ListViewCallLog.Holder localHolder = null;
      if (paramView == null)
      {
        localHolder = new ListViewCallLog.Holder();
        paramView = ListViewCallLog.this.inflater.inflate(2130903049, null);
        localHolder.callInOrOutView = ((ImageView)paramView.findViewById(2131230800));
        localHolder.avatarView = ((ImageView)paramView.findViewById(2131230801));
        localHolder.nameView = ((TextView)paramView.findViewById(2131230802));
        localHolder.durationView = ((TextView)paramView.findViewById(2131230803));
        localHolder.dateView = ((TextView)paramView.findViewById(2131230804));
        localHolder.timeView = ((TextView)paramView.findViewById(2131230805));
        localHolder.checkBoxView = ((CheckBox)paramView.findViewById(2131230798));
        paramView.setTag(localHolder);
        if (!ListViewCallLog.this.deleteStatus)
        localHolder.checkBoxView.setVisibility(0);
//        label173: if (localMyCallLog.getIsChecked().intValue() != 0)
//        localHolder.checkBoxView.setChecked(false);
//        label193: if (!StringUtils.isEmpty(localMyCallLog.getName()))
//          localHolder.nameView.setText(localMyCallLog.getName());
//        if (localMyCallLog.getCallInOrOutView() != 0)
//        localHolder.callInOrOutView.setImageResource(2130837555);
      }
      while (true)
      {
        localHolder.avatarView.setImageResource(2130837691);
//        localHolder.durationView.setText(localMyCallLog.getDuration());
//        localHolder.dateView.setText(localMyCallLog.getDate());
//        localHolder.timeView.setText(localMyCallLog.getTime());
        paramView.setOnLongClickListener(new View.OnLongClickListener()
        {
          public boolean onLongClick(View paramAnonymousView)
          {
            ListViewCallLog.this.setDeleteing();
            ListViewCallLog.MyAdapter.this.notifyDataSetChanged();
            return false;
          }
        });
//        paramView.setOnClickListener(new ListViewCallLog.MyOnclickListener(localHolder, localMyCallLog));
        localHolder = (ListViewCallLog.Holder)paramView.getTag();
        label329: localHolder.checkBoxView.setVisibility(4);
        label341: localHolder.checkBoxView.setChecked(true);
        label353: localHolder.callInOrOutView.setImageResource(2130837556);
      }
    }
  }

  class MyOnclickListener
    implements View.OnClickListener
  {
//    private MyCallLog mCallLog;
    private ListViewCallLog.Holder mHolder;


    public void onClick(View paramView)
    {
      if (this.mHolder.checkBoxView.isChecked())
      {
//        this.mCallLog.setIsChecked(Integer.valueOf(0));
        System.out.println("----true的时候点击");
        this.mHolder.checkBoxView.setChecked(false);
        return;
      }
//      this.mCallLog.setIsChecked(Integer.valueOf(1));
      System.out.println("----false的时候点击");
      this.mHolder.checkBoxView.setChecked(true);
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.view.ListViewCallLog
 * JD-Core Version:    0.6.2
 */