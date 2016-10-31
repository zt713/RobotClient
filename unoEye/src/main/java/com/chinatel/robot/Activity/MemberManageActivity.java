package com.chinatel.robot.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.chinatel.robot.Util.BMapUtil;
import com.chinatel.robot.Util.NetWorkUtil;
import com.chinatel.robot.View.RoundImageView;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.bean.HomeMemberInfo;
import com.chinatel.robot.camera.CustomCamera;
import com.chinatel.robot.db.dao.CallRecordingDao;
import com.chinatel.robot.db.dao.HomeMemberDao;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class MemberManageActivity extends Activity implements
		View.OnClickListener {
	private static final int FLAG_CHOOSE_IMG = 1001;
	private static final int FLAG_CHOOSE_PHONE = 1002;
	private static final int FLAG_MODIFY_FINISH = 1003;
	private static String localTempImageFileName = "";
	private BMapUtil bmaputil;
	private byte[] bs;
	private CallRecordingDao callRecordingDao;
	private HomeMemberDao dao;
	private AlertDialog dialog;
	private LinearLayout have_members;
	private List<HomeMemberInfo> list_allmember;
	private ListView lv_membermanager;
	private long mExitTime;
	private ViewHolder mHolder;
	private MemberAdapter memberAdapter;
	private RelativeLayout no_member;
	private File take_photo;

	private boolean checkNet() {
		return NetWorkUtil.isNetworkConnected(this);
	}

	private void exit() {
		if (System.currentTimeMillis() - this.mExitTime > 2000L) {
			Toast.makeText(this, "再按一次退出成员管理", 0).show();
			this.mExitTime = System.currentTimeMillis();
			return;
		}
		finish();
	}

	private void fillData() {
		this.list_allmember = this.dao.findAll();
		if ((this.list_allmember == null) || (this.list_allmember.size() == 0)) {
			this.no_member.setVisibility(0);
			this.have_members.setVisibility(4);
			return;
		}
		this.no_member.setVisibility(4);
		this.have_members.setVisibility(0);
		new Thread() {
			public void run() {
				MemberManageActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						if (MemberManageActivity.this.memberAdapter == null) {
							// MemberManageActivity.this.memberAdapter = new
							// MemberManageActivity.MemberAdapter(MemberManageActivity.this);
							// MemberManageActivity.this.lv_membermanager.setAdapter(MemberManageActivity.this.memberAdapter);
							// return;
						}
						MemberManageActivity.this.memberAdapter
								.notifyDataSetChanged();
					}
				});
			}
		}.start();
	}

	private void setPicToView(Intent paramIntent) {
		if (paramIntent.getExtras() != null) {
			this.bs = paramIntent.getByteArrayExtra("bitmap");
			Bitmap localBitmap = BitmapFactory.decodeByteArray(this.bs, 0,
					this.bs.length);
			if (localBitmap != null)
				this.mHolder.headerImg.setImageBitmap(localBitmap);
			new Thread(new Runnable() {
				private String fileName;

				public void run() {
					MemberManageActivity.this.bmaputil = new BMapUtil();
					new File(MemberManageActivity.this.bmaputil.getImageDir())
							.mkdirs();
					this.fileName = (MemberManageActivity.this.bmaputil
							.getImageDir() + "/" + System.currentTimeMillis() + ".jpg");
					try {
						FileOutputStream localFileOutputStream = new FileOutputStream(
								this.fileName);
						localFileOutputStream
								.write(MemberManageActivity.this.bs);
						localFileOutputStream.flush();
						localFileOutputStream.close();
						if ((MemberManageActivity.this.take_photo != null)
								&& (MemberManageActivity.this.take_photo
										.exists()))
							MemberManageActivity.this.take_photo.delete();
						MemberManageActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								// int i =
								// MemberManageActivity.this.mHolder.position;
								// MemberManageActivity.this.dao.update(MemberManageActivity.this.fileName,
								// ((HomeMemberInfo)MemberManageActivity.this.list_allmember.get(i)).getName(),
								// ((HomeMemberInfo)MemberManageActivity.this.list_allmember.get(i)).getDevice(),
								// ((HomeMemberInfo)MemberManageActivity.this.list_allmember.get(i)).getMode(),
								// ((HomeMemberInfo)MemberManageActivity.this.list_allmember.get(i)).getKeycode());
								// ((HomeMemberInfo)MemberManageActivity.this.list_allmember.get(i)).setImguri(MemberManageActivity.this.fileName);
								// MemberManageActivity.this.memberAdapter.notifyDataSetChanged();
							}
						});
						return;
					} catch (Exception localException) {
						while (true)
							localException.printStackTrace();
					}
				}
			}).start();
		}
	}

	protected void onActivityResult(int paramInt1, int paramInt2,
			Intent paramIntent) {
		super.onActivityResult(paramInt1, paramInt2, paramIntent);
		if ((paramInt1 == 1001) && (paramInt2 == -1))
			if (paramIntent != null)
				startPhotoClip(paramIntent.getData());
		do {
			String str = null;
			do {
				if ((paramInt1 != 1002) || (paramInt2 != -1))
					break;
				str = paramIntent.getStringExtra("img");
			} while (str == null);
			this.take_photo = new File(str);
			startPhotoClip(Uri.fromFile(this.take_photo));
			return;
		} while ((paramInt1 != 1003) || (paramInt2 != -1)
				|| (paramIntent == null));
		// setPicToView(paramIntent);
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case 2131361882:
		default:
			return;
		case 2131361858:
			Intent localIntent3 = new Intent();
			localIntent3.setAction("android.intent.action.PICK");
			localIntent3.setType("image/*");
			startActivityForResult(localIntent3, 1001);
			this.dialog.dismiss();
			return;
		case 2131361860:
			if (MyApplication.getInstance().mConnect != null) {
				Toast.makeText(this, "摄像头已被监控占用，无法拍照！", 0).show();
				return;
			}
			Intent localIntent2 = new Intent();
			localIntent2.setClass(this, CustomCamera.class);
			startActivityForResult(localIntent2, 1002);
			this.dialog.dismiss();
			return;
		case 2131361861:
			this.dialog.dismiss();
			return;
		case 2131361822:
		}
		if (!checkNet()) {
			Toast.makeText(this, "网络不可用，不能查看userId", 0).show();
			return;
		}
		Intent localIntent1 = new Intent();
		localIntent1.setClassName("com.unisrobot.rsk",
				"com.unisrobot.myrobot3.ModifyPasswordActivity1");
		startActivity(localIntent1);
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903046);
		this.dao = new HomeMemberDao(this);
		this.callRecordingDao = new CallRecordingDao(this);
		this.no_member = ((RelativeLayout) findViewById(2131361816));
		this.have_members = ((LinearLayout) findViewById(2131361826));
		findViewById(2131361822).setOnClickListener(this);
		findViewById(2131361805).setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				MemberManageActivity.this.finish();
			}
		});
		this.lv_membermanager = ((ListView) findViewById(2131361827));
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if (paramInt == 4) {
			exit();
			return true;
		}
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	protected void onResume() {
		super.onResume();
		fillData();
	}

	public void startPhotoClip(Uri paramUri) {
		Log.v("-----------", paramUri.getPath());
		Intent localIntent = new Intent(this, HeaderClipActivity.class);
		localIntent.putExtra("uri", paramUri);
		startActivityForResult(localIntent, 1003);
	}

	class MemberAdapter extends BaseAdapter {
		private String name2;

		MemberAdapter() {
		}

		public int getCount() {
			return MemberManageActivity.this.list_allmember.size();
		}

		public Object getItem(int paramInt) {
			return null;
		}

		public long getItemId(int paramInt) {
			return 0L;
		}

		public View getView(final int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			View localView;
			MemberManageActivity.ViewHolder localViewHolder = null;
			if (paramView == null) {
				localView = View.inflate(MemberManageActivity.this, 2130903062,
						null);
				localViewHolder = new MemberManageActivity.ViewHolder();
				localViewHolder.position = paramInt;
				localViewHolder.headerImg = ((RoundImageView) localView
						.findViewById(2131361882));
				localViewHolder.headerImg.setType(1);
				localViewHolder.name = ((TextView) localView
						.findViewById(2131361883));
				localViewHolder.device = ((TextView) localView
						.findViewById(2131361884));
				localViewHolder.mode = ((ToggleButton) localView
						.findViewById(2131361885));
				localViewHolder.delete = ((Button) localView
						.findViewById(2131361886));
				localView.setTag(localViewHolder);
				HomeMemberInfo localHomeMemberInfo = (HomeMemberInfo) MemberManageActivity.this.list_allmember
						.get(paramInt);
				if (!"".equals(localHomeMemberInfo.getImguri()))
					localViewHolder.headerImg
							.setImageURI(Uri.fromFile(new File(
									localHomeMemberInfo.getImguri())));
				localViewHolder.name.setText(localHomeMemberInfo.getName());
				localViewHolder.name
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View paramAnonymousView) {
								Intent localIntent = new Intent(
										MemberManageActivity.this,
										RoleSetActivity2.class);
								localIntent
										.putExtra(
												"name2",
												((HomeMemberInfo) MemberManageActivity.this.list_allmember
														.get(paramInt))
														.getName());
								localIntent
										.putExtra(
												"imguri",
												((HomeMemberInfo) MemberManageActivity.this.list_allmember
														.get(paramInt))
														.getImguri());
								localIntent
										.putExtra(
												"device",
												((HomeMemberInfo) MemberManageActivity.this.list_allmember
														.get(paramInt))
														.getDevice());
								localIntent
										.putExtra(
												"mode",
												((HomeMemberInfo) MemberManageActivity.this.list_allmember
														.get(paramInt))
														.getMode());
								localIntent
										.putExtra(
												"keycode",
												((HomeMemberInfo) MemberManageActivity.this.list_allmember
														.get(paramInt))
														.getKeycode());
								MemberManageActivity.this
										.startActivity(localIntent);
							}
						});
				localViewHolder.device.setText(localHomeMemberInfo.getDevice());
				if (!localHomeMemberInfo.getMode().equals("1"))
					localViewHolder.mode.setChecked(true);
				localViewHolder.delete.setVisibility(4);
			}
			localViewHolder.headerImg
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							// MemberManageActivity.this.mHolder =
							// localViewHolder;
							AlertDialog.Builder localBuilder = new AlertDialog.Builder(
									MemberManageActivity.this);
							View localView = View
									.inflate(MemberManageActivity.this,
											2130903056, null);
							Button localButton1 = (Button) localView
									.findViewById(2131361858);
							Button localButton2 = (Button) localView
									.findViewById(2131361860);
							Button localButton3 = (Button) localView
									.findViewById(2131361861);
							localButton1
									.setOnClickListener(MemberManageActivity.this);
							localButton2
									.setOnClickListener(MemberManageActivity.this);
							localButton3
									.setOnClickListener(MemberManageActivity.this);
							MemberManageActivity.this.dialog = localBuilder
									.create();
							MemberManageActivity.this.dialog.setView(localView,
									0, 0, 0, 0);
							MemberManageActivity.this.dialog.show();
						}
					});
			localViewHolder.mode
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						public void onCheckedChanged(
								CompoundButton paramAnonymousCompoundButton,
								boolean paramAnonymousBoolean) {
							HomeMemberInfo localHomeMemberInfo = (HomeMemberInfo) MemberManageActivity.this.list_allmember
									.get(paramInt);
							if (paramAnonymousBoolean)
								if (localHomeMemberInfo.getName().equals("陌生人")) {
									Toast.makeText(MemberManageActivity.this,
											"请先设置角色名称", 1000).show();
									// localViewHolder.mode.setChecked(false);
								}
							while (localHomeMemberInfo.getMode().equals("0")) {
								Toast.makeText(MemberManageActivity.this,
										"允许该设备连接机器人", 1000).show();
								MemberManageActivity.this.dao.update(
										localHomeMemberInfo.getImguri(),
										localHomeMemberInfo.getName(),
										localHomeMemberInfo.getDevice(), "1",
										localHomeMemberInfo.getKeycode());
								((HomeMemberInfo) MemberManageActivity.this.list_allmember
										.get(paramInt)).setMode("1");
								MemberManageActivity.this.memberAdapter
										.notifyDataSetChanged();
								return;
							}
							Toast.makeText(MemberManageActivity.this,
									"拒绝该设备连接机器人", 1000).show();
							MemberManageActivity.this.dao.update(
									localHomeMemberInfo.getImguri(),
									localHomeMemberInfo.getName(),
									localHomeMemberInfo.getDevice(), "0",
									localHomeMemberInfo.getKeycode());
							((HomeMemberInfo) MemberManageActivity.this.list_allmember
									.get(paramInt)).setMode("0");
							MemberManageActivity.this.memberAdapter
									.notifyDataSetChanged();
						}
					});
			localViewHolder.delete
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							String str = ((HomeMemberInfo) MemberManageActivity.this.list_allmember
									.get(paramInt)).getKeycode();
							if ((str != null) && (!"".equals(str))) {
								MemberManageActivity.this.dao
										.delete(((HomeMemberInfo) MemberManageActivity.this.list_allmember
												.get(paramInt)).getKeycode());
								MemberManageActivity.this.callRecordingDao
										.delete(str);
								MemberManageActivity.this.list_allmember
										.remove(paramInt);
								MemberManageActivity.this.memberAdapter
										.notifyDataSetChanged();
							}
						}
					});
			localView = paramView;
			localViewHolder = (MemberManageActivity.ViewHolder) localView
					.getTag();
			localViewHolder.mode.setChecked(false);
			localViewHolder.delete.setVisibility(0);
			return localView;
		}

	}

	static class ViewHolder {
		Button delete;
		TextView device;
		RoundImageView headerImg;
		ToggleButton mode;
		TextView name;
		public int position;
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.MemberManageActivity JD-Core
 * Version: 0.6.2
 */