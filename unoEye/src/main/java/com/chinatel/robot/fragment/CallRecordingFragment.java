package com.chinatel.robot.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.chinatel.robot.Activity.SpeakingBeforeActivity;
import com.chinatel.robot.Util.DateUtil;
import com.chinatel.robot.Util.NetWorkUtil;
import com.chinatel.robot.Util.SoundPlayer;
import com.chinatel.robot.Util.ViewUtil;
import com.chinatel.robot.Util.WifiApAdmin;
import com.chinatel.robot.View.RoundImageView;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.bean.CallRecordInfo;
import com.chinatel.robot.db.dao.CallRecordingDao;
import com.chinatel.robot.db.dao.HomeMemberDao;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import rtc.sdk.iface.Connection;
import rtc.sdk.iface.ConnectionListener;

public class CallRecordingFragment extends Fragment implements
		View.OnClickListener {
	private Button bt_cancel;
	private Button bt_delete;
	private List<CallRecordInfo> callRecordList;
	private CallRecordingDao callRecordingDao;
	private CallrecLvAdapter callrecLvAdapter;
	private Context context;
	private AlertDialog dialog;
	private HomeMemberDao homeMemberDao;
	private ImageView img_no_content;
	private List<CallRecordInfo> list;
	private ListView lv_call_recording;
	private boolean showCb;

	private boolean checkNet() {
		boolean bool = NetWorkUtil.isNetworkConnected(getActivity());
		return (new WifiApAdmin(getActivity()).isWifiApEnabled()) || (bool);
	}

	private void setData() {
		this.list = this.callRecordingDao.findAll();
		if ((this.list == null) || (this.list.size() == 0)) {
			this.img_no_content.setVisibility(0);
			this.lv_call_recording.setVisibility(4);
		}
		this.callrecLvAdapter = new CallrecLvAdapter();
		this.lv_call_recording.setAdapter(this.callrecLvAdapter);
	}

	private void showNetDialog() {
		final SoundPlayer localSoundPlayer = SoundPlayer.getInstance(
				getActivity(), "qly001");
		localSoundPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					public void onCompletion(
							MediaPlayer paramAnonymousMediaPlayer) {
						localSoundPlayer.release();
					}
				});
		localSoundPlayer.play();
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(
				getActivity());
		View localView = View.inflate(getActivity(), 2130903057, null);
		Button localButton1 = (Button) localView.findViewById(2131361862);
		Button localButton2 = (Button) localView.findViewById(2131361863);
		localButton1.setOnClickListener(this);
		localButton2.setOnClickListener(this);
		this.dialog = localBuilder.create();
		this.dialog.setView(localView, 0, 0, 0, 0);
		this.dialog.show();
	}

	public void Hide() {
		this.bt_cancel.setVisibility(8);
		this.bt_delete.setVisibility(8);
		RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(
				new RelativeLayout.LayoutParams(-2, -2));
		localLayoutParams.leftMargin = ViewUtil.dipToPixel(this.context, 150);
		localLayoutParams.rightMargin = ViewUtil.dipToPixel(this.context, 150);
		localLayoutParams.topMargin = ViewUtil.dipToPixel(this.context, 0);
		this.lv_call_recording.setLayoutParams(localLayoutParams);
		this.showCb = false;
		this.callrecLvAdapter.notifyDataSetChanged();
	}

	public String getDurnTime(Date paramDate1, Date paramDate2) {
		try {
			long l = paramDate2.getTime() - paramDate1.getTime();
			Time localTime = new Time("GMT+8");
			localTime.set(l);
			StringBuffer localStringBuffer1 = new StringBuffer();
			StringBuffer localStringBuffer2 = new StringBuffer();
			StringBuffer localStringBuffer3 = new StringBuffer();
			localStringBuffer1.append(localTime.hour);
			localStringBuffer2.append(localTime.minute);
			localStringBuffer3.append(localTime.second);
			if (localStringBuffer1.length() == 1)
				localStringBuffer1.insert(0, 0);
			if (localStringBuffer2.length() == 1)
				localStringBuffer2.insert(0, 0);
			if (localStringBuffer3.length() == 1)
				localStringBuffer3.insert(0, 0);
			String str = localStringBuffer1 + ":" + localStringBuffer2 + ":"
					+ localStringBuffer3;
			return str;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public void onActivityCreated(Bundle paramBundle) {
		super.onActivityCreated(paramBundle);
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		default:
			return;
		case 2131361862:
			startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
			this.dialog.dismiss();
			return;
		case 2131361863:
		}
		this.dialog.cancel();
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		this.context = getActivity();
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(2130903059, null);
		this.img_no_content = ((ImageView) localView.findViewById(2131361867));
		this.bt_delete = ((Button) localView.findViewById(2131361869));
		this.bt_cancel = ((Button) localView.findViewById(2131361868));
		this.bt_delete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				ArrayList localArrayList = new ArrayList();
				Iterator localIterator = CallRecordingFragment.this.list
						.iterator();
				while (true) {
					if (!localIterator.hasNext()) {
						CallRecordingFragment.this.list
								.removeAll(localArrayList);
						CallRecordingFragment.this.callrecLvAdapter
								.notifyDataSetChanged();
						return;
					}
					CallRecordInfo localCallRecordInfo = (CallRecordInfo) localIterator
							.next();
					if (localCallRecordInfo.isChecked()) {
						localArrayList.add(localCallRecordInfo);
						CallRecordingFragment.this.callRecordingDao
								.deleteById(localCallRecordInfo.get_id()
										.intValue());
					}
				}
			}
		});
		this.bt_cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				CallRecordingFragment.this.Hide();
			}
		});
		this.lv_call_recording = ((ListView) localView.findViewById(2131361870));
		this.callRecordList = new ArrayList();
		this.callRecordingDao = new CallRecordingDao(this.context);
		this.homeMemberDao = new HomeMemberDao(this.context);
		this.lv_call_recording
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong) {
						CallRecordingFragment.this.show();
						return true;
					}
				});
		this.lv_call_recording
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong) {
						CallRecordInfo localCallRecordInfo = (CallRecordInfo) CallRecordingFragment.this.list
								.get(paramAnonymousInt);
						CallRecordingFragment.ViewHolder localViewHolder = (CallRecordingFragment.ViewHolder) paramAnonymousView
								.getTag();
						if (localViewHolder.cb_status.isChecked()) {
							localCallRecordInfo.setChecked(false);
							localViewHolder.cb_status.setChecked(false);
							return;
						}
						localCallRecordInfo.setChecked(true);
						localViewHolder.cb_status.setChecked(true);
					}
				});
		return localView;
	}

	public void onResume() {
		super.onResume();
		setData();
	}

	public void show() {
		this.bt_cancel.setVisibility(0);
		this.bt_delete.setVisibility(0);
		RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(
				new RelativeLayout.LayoutParams(-2, -2));
		localLayoutParams.leftMargin = ViewUtil.dipToPixel(this.context, 150);
		localLayoutParams.rightMargin = ViewUtil.dipToPixel(this.context, 150);
		localLayoutParams.topMargin = ViewUtil.dipToPixel(this.context, 50);
		this.lv_call_recording.setLayoutParams(localLayoutParams);
		this.showCb = true;
		this.callrecLvAdapter.notifyDataSetChanged();
	}

	public void sort(List<CallRecordInfo> paramList) {
		Collections.sort(paramList, new Comparator<CallRecordInfo>() {
			public int compare(CallRecordInfo paramAnonymousCallRecordInfo1,
					CallRecordInfo paramAnonymousCallRecordInfo2) {
				return 0;
			}
		});
	}

	class CallrecLvAdapter extends BaseAdapter {
		CallrecLvAdapter() {
		}

		public int getCount() {
			return CallRecordingFragment.this.list.size();
		}

		public Object getItem(int paramInt) {
			return CallRecordingFragment.this.list.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return 0L;
		}

		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			View localView;
			CallRecordingFragment.ViewHolder localViewHolder = null;
			CallRecordInfo localCallRecordInfo = null;
			if (paramView != null) {
				localView = paramView;
				localViewHolder = (CallRecordingFragment.ViewHolder) localView
						.getTag();
				if (!CallRecordingFragment.this.showCb)
					localViewHolder.cb_status.setVisibility(0);
				localCallRecordInfo = (CallRecordInfo) CallRecordingFragment.this.list
						.get(paramInt);
				localViewHolder.cb_status.setChecked(localCallRecordInfo
						.isChecked());
				RoundImageView localRoundImageView = localViewHolder.iv_icon;
				View.OnClickListener local1 = new View.OnClickListener() {
					public void onClick(View paramAnonymousView) {/*
																 * Toast.makeText
																 * (
																 * CallRecordingFragment
																 * .
																 * this.context,
																 * "呼叫的keyCode："
																 * +
																 * localCallRecordInfo
																 * .
																 * getKeycode(),
																 * 1).show();
																 * Log
																 * .i("robot",
																 * "呼叫的keyCode："
																 * +
																 * localCallRecordInfo
																 * .
																 * getKeycode())
																 * ; if (!
																 * CallRecordingFragment
																 * .
																 * this.checkNet
																 * ()) {
																 * CallRecordingFragment
																 * .this.
																 * showNetDialog
																 * (); return; }
																 * for (int i =
																 * 0; ; i++) {
																 * if (i >= 4);
																 * while (true)
																 * { Intent
																 * localIntent =
																 * new Intent(
																 * CallRecordingFragment
																 * .
																 * this.context,
																 * SpeakingBeforeActivity
																 * .class);
																 * localIntent
																 * .putExtra
																 * ("keyCode",
																 * localCallRecordInfo
																 * .
																 * getKeycode())
																 * ;
																 * CallRecordingFragment
																 * .this.
																 * startActivity
																 * (
																 * localIntent);
																 * if
																 * ((MyApplication
																 * .
																 * getInstance()
																 * .cons[i] ==
																 * null) ||
																 * (!MyApplication
																 * .
																 * getInstance()
																 * .
																 * cons[i].info(
																 * ).contains(
																 * localCallRecordInfo
																 * .
																 * getKeycode())
																 * )) break;
																 * MyApplication
																 * .
																 * getInstance()
																 * .cons[i].
																 * disconnect();
																 * MyApplication
																 * .
																 * getInstance()
																 * .mMListener.
																 * onDisconnected
																 * (200); } }
																 */
					}
				};
				localRoundImageView.setOnClickListener(local1);
				String str1 = localCallRecordInfo.getKeycode();
				/*
				 * if ((str1 == null) || ("".equals(str1))) String str8 =
				 * CallRecordingFragment.this.homeMemberDao.findImguri(str1); if
				 * (("".equals(str8)) || (str8 == null)) File localFile = new
				 * File(str8); if ((localFile == null) || (!localFile.exists()))
				 * localViewHolder.iv_icon.setImageURI(Uri.fromFile(localFile));
				 * label181: if
				 * (!"come".equals(localCallRecordInfo.getComego()))
				 * localViewHolder.iv_comego.setImageResource(2130837573);
				 */
				/*
				 * if ((str1 == null) || ("".equals(str1))) String str7 =
				 * CallRecordingFragment.this.homeMemberDao.findName2(str1);
				 * localViewHolder.tv_name.setText(str7);
				 */
			}
			String str2 = localCallRecordInfo.getStarttime();
			String str3 = "";
			String str4 = "";
			String str5 = localCallRecordInfo.getEndtime();
			if (str2.length() > 11) {
				str4 = str2.substring(0, 10);
				str3 = str2.substring(10);
			}
			localViewHolder.tv_date.setText(str4);
			localViewHolder.tv_time.setText(str3);
			Date localDate1 = DateUtil.getDateByString(str2,
					"yyyy-MM-ddHH:mm:ss");
			Date localDate2 = DateUtil.getDateByString(str5,
					"yyyy-MM-ddHH:mm:ss");
			String str6 = CallRecordingFragment.this.getDurnTime(localDate1,
					localDate2);
			localViewHolder.tv_duration.setText(str6);
			// return localView;
			localView = View.inflate(CallRecordingFragment.this.context,
					2130903060, null);
			localViewHolder = new CallRecordingFragment.ViewHolder();
			localViewHolder.iv_comego = ((ImageView) localView
					.findViewById(2131361871));
			localViewHolder.iv_icon = ((RoundImageView) localView
					.findViewById(2131361872));
			localViewHolder.iv_time = ((ImageView) localView
					.findViewById(2131361874));
			localViewHolder.tv_duration = ((TextView) localView
					.findViewById(2131361875));
			localViewHolder.tv_name = ((TextView) localView
					.findViewById(2131361873));
			localViewHolder.tv_date = ((TextView) localView
					.findViewById(2131361876));
			localViewHolder.tv_time = ((TextView) localView
					.findViewById(2131361877));
			localViewHolder.cb_status = ((CheckBox) localView
					.findViewById(2131361878));
			localView.setTag(localViewHolder);
			// break;
			label512: localViewHolder.cb_status.setVisibility(8);
			// break label36;
			label525: localViewHolder.iv_icon.setImageResource(2130837575);
			// break label181;
			label538: localViewHolder.iv_icon.setImageResource(2130837575);
			// break label181;
			label551: localViewHolder.iv_icon.setImageResource(2130837575);
			// break label181;
			label564: localViewHolder.iv_comego.setImageResource(2130837574);
			// break label204;
			label577: localViewHolder.tv_name.setText("");
			return null;

		}
	}

	static class ViewHolder {
		CheckBox cb_status;
		ImageView iv_comego;
		RoundImageView iv_icon;
		ImageView iv_time;
		TextView tv_date;
		TextView tv_duration;
		TextView tv_name;
		TextView tv_time;
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.fragment.CallRecordingFragment JD-Core
 * Version: 0.6.2
 */