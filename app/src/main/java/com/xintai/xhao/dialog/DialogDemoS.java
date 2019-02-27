package com.xintai.xhao.dialog;
//package com.xhao.mobile.dialog;
//
//
//import java.sql.Date;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.zip.Inflater;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.Toast;
//
//public class DialogDemoS extends Activity {
//	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
//	private View layout;
//	private ProgressDialog dialog;
//	private LinearLayout linear;
//
//	// private Calendar cal = Calendar.getInstance();
//
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//		btn1 = (Button) findViewById(R.id.btn1);
//		btn2 = (Button) findViewById(R.id.btn2);
//		btn3 = (Button) findViewById(R.id.btn3);
//		btn4 = (Button) findViewById(R.id.btn4);
//		btn5 = (Button) findViewById(R.id.btn5);
//		btn6 = (Button) findViewById(R.id.btn6);
//		btn7 = (Button) findViewById(R.id.btn7);
//		linear = (LinearLayout) findViewById(R.id.linear);
//
//		/**
//		 * 自定义Dialog组件
//		 */
//		btn1.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				NewDialog newdialog = new NewDialog(DialogDemoS.this);
//				newdialog.setTitle("修改");
//				newdialog.setContent("修改內容？");
//				newdialog.setBackgroundColor(Color.BLACK);// 这里的color指的是Color这个类，而不是R.color.XX
//				// newdialog.setBackgroundResource(R.drawable.ic_launcher);
//				newdialog.setTitleBackgroundColor(Color.parseColor("#373737"));
//
//				// newdialog.setBackgroundResource(R.color.black);
//				// newdialog.setTitleBackgroundResource(R.color.wan);
//
//				// newdialog.setBackgroundDrawable(getResources().getDrawable(R.color.black));
//				// newdialog.setTitleBackgroundDrawable(getResources().getDrawable(R.color.wan));
//
//				// 以后也许有用，自定义的对应上，布局上面的组件点击事件
//				// newdialog.setOnYClick(new OnClickListener() {
//				// @Override
//				// public void onClick(View v) {
//				// // TODO Auto-generated method stub
//				// newdialog.dismiss();
//				//
//				// }
//				// });
//				// newdialog.setOnNClick(new OnClickListener() {
//				// @Override
//				// public void onClick(View v) {
//				// // TODO Auto-generated method stub
//				// newdialog.dismiss();
//				//
//				// }
//				// });
//
//			}
//		});
//		
//		/**
//		 * 选择型Dialog
//		 */
//		btn2.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				Dialog dialog = new AlertDialog.Builder(DialogDemoS.this)
//						.setTitle("喜欢调查")
//						.setMessage("你喜欢看成龙的电影吗")
//						.setPositiveButton("喜欢",
//								new DialogInterface.OnClickListener() {
//									public void onClick(DialogInterface dialog,
//											int which) {
//										btn2.setText("喜欢");
//									}
//								})
//						.setNegativeButton("不喜欢",
//								new DialogInterface.OnClickListener() {
//									public void onClick(DialogInterface dialog,
//											int which) {
//										btn2.setText("不喜欢");
//									}
//								}).show();
//			}
//		});
//
//		btn3.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				final EditText edit = new EditText(DialogDemoS.this);
//				Dialog dialog = new AlertDialog.Builder(DialogDemoS.this)
//						.setTitle("请输入：")
//						.setView(edit)
//						.setPositiveButton("确定",
//								new DialogInterface.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										// TODO Auto-generated method stub
//										btn3.setText(edit.getText());
//									}
//								}).show();
//			}
//		});
//		
//		btn4.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				Dialog dialog = new AlertDialog.Builder(DialogDemoS.this)
//						.setTitle("单选框")
//						.setSingleChoiceItems(
//								new String[] { "苏州", "上海", "南京" }, 1,
//								new DialogInterface.OnClickListener() {
//									public void onClick(DialogInterface dialog,
//											int which) {
//										if (which == 0) {
//											btn4.setText("苏州");
//										} else if (which == 1) {
//											btn4.setText("上海");
//										} else if (which == 2) {
//											btn4.setText("南京");
//										}
//										dialog.dismiss();
//									}
//								}).show();
//			}
//		});
//		
//		btn5.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				Dialog dialog = new AlertDialog.Builder(DialogDemoS.this)
//						.setTitle("多选框")
//						.setMultiChoiceItems(new String[] { "徐丹", "严灿", "李明" },
//								null, null)
//						.setPositiveButton("确定",
//								new DialogInterface.OnClickListener() {
//									public void onClick(DialogInterface dialog,
//											int which) {
//
//									}
//								})
//						.setNeutralButton("取消",
//								new DialogInterface.OnClickListener() {
//									public void onClick(DialogInterface dialog,
//											int which) {
//
//									}
//								}).show();
//			}
//		});
//		/**
//		 * 自定义Dialog布局
//		 */
//		btn6.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				LayoutInflater inflater = LayoutInflater.from(getApplication());
//				layout = inflater.inflate(R.layout.mycreating, null);// 这2句必须放到事件里
//				Dialog dialog = new AlertDialog.Builder(DialogDemoS.this)
//						.setTitle("注册表单").setView(layout).show();
//
//				EditText edit_name = (EditText) layout
//						.findViewById(R.id.edit_name);// 寻找过滤出来的组件一定要在View里面
//				EditText edit_age = (EditText) layout
//						.findViewById(R.id.edit_age);
//				RadioGroup group = (RadioGroup) layout.findViewById(R.id.group);
//				// 怎么获取单选框和复选框里面的值
//				/**
//				 * 在最后提交的时候获取单选按钮/复选按钮的值
//				 */
//				String radio = ((RadioButton) layout.findViewById(group
//						.getCheckedRadioButtonId())).getText().toString();
//				Toast.makeText(getApplication(), radio, 1).show();
//
//				LinearLayout linear = (LinearLayout) layout
//						.findViewById(R.id.linear);
//				for (int i = 0; i < linear.getChildCount(); i++) {
//					if (((CheckBox) linear.getChildAt(i)).isChecked()) {
//						String value = ((CheckBox) linear.getChildAt(i))
//								.getText().toString();
//						Toast.makeText(getApplication(), value, 1).show();
//					}
//				}
//
//				group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//					@Override
//					public void onCheckedChanged(RadioGroup group, int checkedId) {
//						// TODO Auto-generated method stub
//						for (int i = 0; i < group.getChildCount(); i++) {
//							if (checkedId == group.getChildAt(i).getId()) {
//								String value = ((RadioButton) group
//										.getChildAt(i)).getText().toString();
//								Toast.makeText(getApplication(), value, 1)
//										.show();
//							}
//						}
//					}
//				});
//
//				RadioButton man = (RadioButton) layout.findViewById(R.id.man);
//				RadioButton woman = (RadioButton) layout
//						.findViewById(R.id.woman);
//
//				Button regist = (Button) layout.findViewById(R.id.regist);// 可以获取布局里面的组件，像平常组件一样操作
//				Button cancel = (Button) layout.findViewById(R.id.cancel);
//			}
//		});
//		
//		/**
//		 * 进度Dialog
//		 */
//		btn7.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				dialog = new ProgressDialog(DialogDemoS.this);
//				dialog.setMessage("数据正在加载，请等待。。。");
//				dialog.show();
//				handler.postDelayed(run, 2000);
//			}
//		});
//	}
//
//	private Handler handler = new Handler();
//	private Runnable run = new Runnable() {
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			dialog.dismiss();
//		}
//	};
//}