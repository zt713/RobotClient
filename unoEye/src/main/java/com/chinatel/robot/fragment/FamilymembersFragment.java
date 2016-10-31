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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinatel.robot.R;
import com.chinatel.robot.R1;
import com.chinatel.robot.Activity.SpeakingBeforeActivity;
import com.chinatel.robot.Activity.SpeakingBeforeActivity.CallClientReceiver;
import com.chinatel.robot.Util.NetWorkUtil;
import com.chinatel.robot.Util.SoundPlayer;
import com.chinatel.robot.Util.WifiApAdmin;
import com.chinatel.robot.View.RoundImageView;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.bean.HomeMemberInfo;
import com.chinatel.robot.db.dao.HomeMemberDao;
import java.io.File;
import java.util.List;
import rtc.sdk.iface.Connection;
import rtc.sdk.iface.ConnectionListener;

public class FamilymembersFragment extends Fragment implements
		View.OnClickListener {
	private Context context;
	private HomeMemberDao dao;
	private AlertDialog dialog;
	private GridView gridv_members;
	private boolean hasPlay;
	private List<HomeMemberInfo> list_allmember;
	private MembersAdapter membersAdapter;
	private RelativeLayout no_data;

	private boolean checkNet() {
		boolean bool = NetWorkUtil.isNetworkConnected(getActivity());
		return (new WifiApAdmin(getActivity()).isWifiApEnabled()) || (bool);
	}

	private boolean checkNet2() {
		return NetWorkUtil.isNetworkConnected(getActivity());
	}

	private void setData() {
		this.list_allmember = this.dao.findNoIntercept();
		if ((this.list_allmember == null) || (this.list_allmember.size() == 0)) {
			this.no_data.setVisibility(0);
			this.gridv_members.setVisibility(4);
			if (!this.hasPlay) {
				this.hasPlay = true;
				final SoundPlayer localSoundPlayer1 = SoundPlayer.getInstance(
						getActivity(), "qly018.mp3");
				localSoundPlayer1
						.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
							public void onCompletion(
									MediaPlayer paramAnonymousMediaPlayer) {
								localSoundPlayer1.release();
							}
						});
				localSoundPlayer1.play();
			}
		}
		if (this.membersAdapter == null) {
			this.membersAdapter = new MembersAdapter();
			this.gridv_members.setAdapter(this.membersAdapter);
			this.no_data.setVisibility(4);
			this.gridv_members.setVisibility(0);
			if (!this.hasPlay) {
				this.hasPlay = true;
				final SoundPlayer localSoundPlayer2 = SoundPlayer.getInstance(
						getActivity(), "qly002.mp3");
				localSoundPlayer2
						.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
							public void onCompletion(
									MediaPlayer paramAnonymousMediaPlayer) {
								localSoundPlayer2.release();
							}
						});
				localSoundPlayer2.play();
			}
		}
		this.membersAdapter.notifyDataSetChanged();
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

	public void onActivityCreated(Bundle paramBundle) {
		super.onActivityCreated(paramBundle);
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case 2131361864:
		case 2131361865:
		default:
			return;
		case 2131361866:
			if (!checkNet2()) {
				Toast.makeText(getActivity(), "网络不可用，不能查看userId", 0).show();
				return;
			}
			Intent localIntent = new Intent();
			localIntent.setClassName("com.unisrobot.rsk",
					"com.unisrobot.myrobot3.ModifyPasswordActivity1");
			startActivity(localIntent);
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
		View localView = paramLayoutInflater.inflate(
				R.layout.fragmen_mumber_manager, null);
		this.gridv_members = ((GridView) localView
				.findViewById(R.id.gridv_members));
		this.no_data = ((RelativeLayout) localView.findViewById(R.id.no_data));
		localView.findViewById(R.id.bt_lookQR).setOnClickListener(this);
		this.dao = new HomeMemberDao(this.context);
		return localView;
	}

	public void onResume() {
		super.onResume();
		setData();
	}

	class MembersAdapter extends BaseAdapter {
		MembersAdapter() {
		}

		public int getCount() {
			return FamilymembersFragment.this.list_allmember.size();
		}

		public Object getItem(int paramInt) {
			return null;
		}

		public long getItemId(int paramInt) {
			return 0L;
		}

		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			final HomeMemberInfo localHomeMemberInfo = (HomeMemberInfo) FamilymembersFragment.this.list_allmember
					.get(paramInt);
			FamilymembersFragment.ViewHolder localViewHolder;
			if (paramView == null) {
				localViewHolder = new FamilymembersFragment.ViewHolder(
						FamilymembersFragment.this);
				paramView = View.inflate(FamilymembersFragment.this.context,
						R.layout.list_item_homemembers, null);
				localViewHolder.tv_mumber_name = ((TextView) paramView
						.findViewById(R.id.tv_number_name));
				localViewHolder.iv_item = ((RoundImageView) paramView
						.findViewById(R.id.iv_item));
				paramView.setTag(localViewHolder);
				localViewHolder.tv_mumber_name.setTextSize(22.0F);
				localViewHolder.tv_mumber_name
						.setText(((HomeMemberInfo) FamilymembersFragment.this.list_allmember
								.get(paramInt)).getName());
				localViewHolder.iv_item.setType(1);
				if (localHomeMemberInfo.getImguri().equals("")) {
					localViewHolder.iv_item
							.setImageResource(R.drawable.gridview_head);
				} else {
					localViewHolder.iv_item.setImageURI(Uri.fromFile(new File(
							localHomeMemberInfo.getImguri())));
				}
			}
			paramView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View paramAnonymousView) {
					Toast.makeText(FamilymembersFragment.this.context,
							"呼叫：" + localHomeMemberInfo.getKeycode(), 1).show();
					Log.i("robot",
							"呼叫的keyCode：" + localHomeMemberInfo.getKeycode());
					if (!FamilymembersFragment.this.checkNet()) {
						FamilymembersFragment.this.showNetDialog();
						return;
					}
					Intent localIntent = new Intent(
							FamilymembersFragment.this.context,
							SpeakingBeforeActivity.class);
					localIntent.putExtra("keyCode",
							localHomeMemberInfo.getKeycode());
					FamilymembersFragment.this.startActivity(localIntent);
										
					/*
					 * if ((MyApplication.getInstance().cons[0] == null) ||
					 * (!MyApplication
					 * .getInstance().cons[0].info().contains(localHomeMemberInfo
					 * .getKeycode()))) break;
					 * MyApplication.getInstance().cons[0].disconnect();
					 * MyApplication
					 * .getInstance().mMListener.onDisconnected(200);
					 */
					/*Intent intent = new Intent(CallClientReceiver.BROADCAST_ACTION);
					context.sendBroadcast(intent);	*/
				
				}
			});
			localViewHolder = (FamilymembersFragment.ViewHolder) paramView
					.getTag();
			/*
			 * break; label193:
			 * localViewHolder.iv_item.setImageResource(2130837599);
			 */return paramView;
		}
	}

	class ViewHolder {
		RoundImageView iv_item;
		TextView tv_mumber_name;

		ViewHolder(FamilymembersFragment fragment) {
		}

		ViewHolder() {

		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.fragment.FamilymembersFragment JD-Core
 * Version: 0.6.2
 */