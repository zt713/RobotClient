package com.chinatel.robot.View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomDialog extends Dialog {
	public CustomDialog(Context paramContext) {
		super(paramContext);
	}

	public CustomDialog(Context paramContext, int paramInt) {
		super(paramContext, paramInt);
	}

	public static class Builder {
		private View contentView;
		private Context context;
		private String message;
		private DialogInterface.OnClickListener negativeButtonClickListener;
		private String negativeButtonText;
		private DialogInterface.OnClickListener positiveButtonClickListener = null;
		private String positiveButtonText;
		private String title;

		public Builder(Context paramContext) {
			this.context = paramContext;
		}

		public CustomDialog create() {
			LayoutInflater localLayoutInflater = (LayoutInflater) this.context
					.getSystemService("layout_inflater");
			final CustomDialog localCustomDialog = new CustomDialog(
					this.context, 2131230728);
			View localView = localLayoutInflater.inflate(2130903054, null);
			localCustomDialog.addContentView(localView,
					new ViewGroup.LayoutParams(-1, -2));
			((TextView) localView.findViewById(2131361850)).setText(this.title);
			if (this.positiveButtonText != null) {/*
												 * ((Button)localView.findViewById
												 * (2131361852)).setText(this.
												 * positiveButtonText); if
												 * (this.
												 * positiveButtonClickListener
												 * != null)
												 * ((Button)localView.findViewById
												 * (
												 * 2131361852)).setOnClickListener
												 * (new View.OnClickListener() {
												 * public void onClick(View
												 * paramAnonymousView) {
												 * CustomDialog.Builder.this.
												 * positiveButtonClickListener
												 * .onClick(localCustomDialog,
												 * -1); } }); if
												 * (this.negativeButtonText ==
												 * null) // break label212;
												 * ((Button
												 * )localView.findViewById
												 * (2131361853)).setText(this.
												 * negativeButtonText); if
												 * (this.
												 * negativeButtonClickListener
												 * != null)
												 * ((Button)localView.findViewById
												 * (
												 * 2131361853)).setOnClickListener
												 * (new View.OnClickListener() {
												 * public void onClick(View
												 * paramAnonymousView) {
												 * CustomDialog.Builder.this.
												 * negativeButtonClickListener
												 * .onClick(localCustomDialog,
												 * -2); } }); label168: if
												 * (this.message == null) //
												 * break label226;
												 * ((TextView)localView
												 * .findViewById
												 * (2131361851)).setText
												 * (this.message);
												 */
			}
			while (true) {
				localCustomDialog.setContentView(localView);
				// return localCustomDialog;
				localView.findViewById(2131361852).setVisibility(8);
				// break;
				label212: localView.findViewById(2131361853).setVisibility(8);
				// break label168;
				label226: if (this.contentView != null) {
					((LinearLayout) localView.findViewById(2131361803))
							.removeAllViews();
					((LinearLayout) localView.findViewById(2131361803))
							.addView(this.contentView,
									new ViewGroup.LayoutParams(-1, -1));
				}
			}
		}

		public Builder setContentView(View paramView) {
			this.contentView = paramView;
			return this;
		}

		public Builder setMessage(int paramInt) {
			this.message = ((String) this.context.getText(paramInt));
			return this;
		}

		public Builder setMessage(String paramString) {
			this.message = paramString;
			return this;
		}

		public Builder setNegativeButton(int paramInt,
				DialogInterface.OnClickListener paramOnClickListener) {
			this.negativeButtonText = ((String) this.context.getText(paramInt));
			// this.negativeButtonClickListener = paramOnClickListener;
			return this;
		}

		public Builder setNegativeButton(String paramString,
				DialogInterface.OnClickListener paramOnClickListener) {
			this.negativeButtonText = paramString;
			// this.negativeButtonClickListener = paramOnClickListener;
			return this;
		}

		public Builder setPositiveButton(int paramInt,
				DialogInterface.OnClickListener paramOnClickListener) {
			this.positiveButtonText = ((String) this.context.getText(paramInt));
			// this.positiveButtonClickListener = paramOnClickListener;
			return this;
		}

		public Builder setPositiveButton(String paramString,
				DialogInterface.OnClickListener paramOnClickListener) {
			this.positiveButtonText = paramString;
			// this.positiveButtonClickListener = paramOnClickListener;
			return this;
		}

		public Builder setTitle(int paramInt) {
			this.title = ((String) this.context.getText(paramInt));
			return this;
		}

		public Builder setTitle(String paramString) {
			this.title = paramString;
			return this;
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.View.CustomDialog JD-Core Version: 0.6.2
 */