package com.saturdaycoder.easydoubanfm;
import java.util.Timer;
import java.util.TimerTask;

import com.saturdaycoder.easydoubanfm.player.HttpFetcher;

import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.ComponentName;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.*;

public class EasyDoubanFm extends Activity {
	private static final int MENU_LOGIN_ID = Menu.FIRST;
	private static final int MENU_ABOUT_ID = Menu.FIRST + 1;
	private static final int MENU_TIMER_ID = Menu.FIRST + 2;
	private static final int MENU_FEEDBACK_ID = Menu.FIRST + 3;
	private static final int MENU_CLOSE_ID = Menu.FIRST + 4;  
	//private WidgetContent widgetContent;

	//private static EasyDoubanFm _this = null;
	Button buttonChannel;
	ImageView imageCover;
	TextView textArtist;
	TextView textTitle;
	ImageButton buttonSkip;
	ImageButton buttonPlayPause;
	//TextView textButtonPlayPause;
	ImageButton buttonDownload;
	ImageButton buttonBan;
	ImageButton buttonRateUnrate;
	//TextView textButtonRateUnrate;
	ImageButton buttonMenu;
	ProgressBar progressBar;
	ProgressBar progressPower;
	TextView textPosition;
	
	int curPos;
	int duration;
	
	PlayerEventListener playerEventListener;
	
	/*public static void setPrepareProgress(int progress) {
		Debugger.debug("EasyDoubanFm.setPrepareProgress");
		if (_this == null) {
			Debugger.debug("no activity active, skip updating");
			return;
		}
		
		_this.imageCover.setVisibility(ImageView.GONE);
		_this.progressBar.setVisibility(ProgressBar.VISIBLE);
	}
	
	public static void updatePosition(int pos, int dur) {
		Debugger.debug("EasyDoubanFm.updatePosition");
		if (_this == null) {
			Debugger.debug("no activity active, skip updating");
			return;
		}
		synchronized(EasyDoubanFm.class) {
			_this.curPos = pos;
			_this.duration = dur;
		}

		_this.textPosition.setText(DateFormat.format("mm:ss", pos).toString() 
				+ " / " + DateFormat.format("mm:ss", dur).toString());	
	}
	
	public static void updateContents(WidgetContent content) {
		Debugger.debug("EasyDoubanFm.updateContents");
		if (_this == null) {
			Debugger.debug("no activity active, skip updating");
			return;
		}
		
		// channel text
		_this.buttonChannel.setText(content.channel);
		// picture
		if (content.picture == null) {
			_this.progressBar.setVisibility(ProgressBar.VISIBLE);
			_this.imageCover.setVisibility(ImageView.GONE);
			//_this.imageCover.setImageResource(R.drawable.default_album);
		}
		else {
			_this.imageCover.setVisibility(ImageView.VISIBLE);
			_this.progressBar.setVisibility(ProgressBar.GONE);
			_this.imageCover.setImageBitmap(content.picture);
		}
		// music artist
		_this.textArtist.setText(content.artist);
		// music title
		_this.textTitle.setText(content.title);
		// pause
		_this.buttonPlayPause.setImageResource(content.paused? R.drawable.btn_play: R.drawable.btn_pause);
		_this.textButtonPlayPause.setText(content.paused? _this.getResources().getString(R.string.button_name_play): _this.getResources().getString(R.string.button_name_pause));
		_this.mHandler.removeCallbacks(_this.mPositionTask);
		if (!content.paused) {
			_this.mHandler.postDelayed(_this.mPositionTask, 1000);
		}
		// rate/unrate
		_this.buttonRateUnrate.setImageResource(content.rated? R.drawable.btn_rated: R.drawable.btn_unrated);
		_this.textButtonRateUnrate.setText(content.rated? _this.getResources().getString(R.string.button_name_unrate): _this.getResources().getString(R.string.button_name_rate));
		// on/off
		switch(content.onState) {
		case EasyDoubanFmWidget.STATE_OFF: {
			_this.buttonPlayPause.setImageResource(R.drawable.btn_play);
			_this.textButtonPlayPause.setText(_this.getResources().getString(R.string.button_name_play));
			_this.mHandler.removeCallbacks(_this.mPositionTask);
			_this.progressBar.setVisibility(ProgressBar.GONE);
			_this.imageCover.setVisibility(ImageView.VISIBLE);
			_this.imageCover.setImageResource(R.drawable.default_album);
			break;
		}
		case EasyDoubanFmWidget.STATE_ON: {
			break;
		}
		case EasyDoubanFmWidget.STATE_PREPARE: {
			break;
		}
		default:
			break;
		}
	}*/
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        setContentView(R.layout.main_activity);
        
        
		buttonChannel = (Button)findViewById(R.id.buttonChannel);
		imageCover = (ImageView)findViewById(R.id.imageCover);
		textArtist = (TextView)findViewById(R.id.textArtist);
		textTitle = (TextView)findViewById(R.id.textTitle);
		buttonSkip = (ImageButton)findViewById(R.id.buttonNext);
		buttonPlayPause = (ImageButton)findViewById(R.id.buttonPlayPause);
		//textButtonPlayPause = (TextView)findViewById(R.id.textButtonPlayPause);
		buttonDownload = (ImageButton)findViewById(R.id.buttonDownload);
		buttonBan = (ImageButton)findViewById(R.id.buttonHate);
		buttonRateUnrate = (ImageButton)findViewById(R.id.buttonLike);
		//textButtonRateUnrate = (TextView)findViewById(R.id.textButtonRateUnrate);
		buttonMenu = (ImageButton)findViewById(R.id.buttonMenu);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		textPosition = (TextView)findViewById(R.id.textMusicPosition);
		//progressPower = (ProgressBar)findViewById(R.id.progressPower);
		
		positionTimer = new Timer();
		buttonSkip.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Global.ACTION_PLAYER_SKIP);
				i.setComponent(new ComponentName(EasyDoubanFm.this, DoubanFmService.class));
				startService(i);
			}
			
		});
		
		buttonPlayPause.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				//_this.progressBar.setVisibility(ProgressBar.VISIBLE);
				//_this.imageCover.setVisibility(ImageView.GONE);
				Intent i = new Intent(Global.ACTION_PLAYER_PLAYPAUSE);
				i.setComponent(new ComponentName(EasyDoubanFm.this, DoubanFmService.class));
				startService(i);
			}
			
		});
		
		buttonDownload.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Global.ACTION_DOWNLOADER_DOWNLOAD);
				i.setComponent(new ComponentName(EasyDoubanFm.this, DoubanFmService.class));
				startService(i);
			}
			
		});
		
		buttonBan.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Global.ACTION_PLAYER_TRASH);
				i.setComponent(new ComponentName(EasyDoubanFm.this, DoubanFmService.class));
				startService(i);
			}
			
		});
		
		buttonRateUnrate.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Global.ACTION_PLAYER_RATEUNRATE);
				i.setComponent(new ComponentName(EasyDoubanFm.this, DoubanFmService.class));
				startService(i);
			}
			
		});
		
		buttonChannel.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(EasyDoubanFm.this, ChannelSelectorActivity.class);
				startActivity(i);
			}
		});
		
		buttonMenu.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(EasyDoubanFm.this, PreferenceActivity.class);
				startActivity(i);
			}
			
		});
		
		
		
		playerEventListener = new PlayerEventListener();
		IntentFilter mfilter = new IntentFilter();
		mfilter.addAction(Global.EVENT_CHANNEL_CHANGED);
		mfilter.addAction(Global.EVENT_LOGIN_STATE_CHANGED);
		mfilter.addAction(Global.EVENT_PLAYER_MUSIC_BANNED);
		mfilter.addAction(Global.EVENT_PLAYER_MUSIC_PREPARE_PROGRESS);
		mfilter.addAction(Global.EVENT_PLAYER_MUSIC_PROGRESS);
		mfilter.addAction(Global.EVENT_PLAYER_MUSIC_RATED);
		mfilter.addAction(Global.EVENT_PLAYER_MUSIC_STATE_CHANGED);
		mfilter.addAction(Global.EVENT_PLAYER_MUSIC_UNRATED);
		mfilter.addAction(Global.EVENT_PLAYER_PICTURE_STATE_CHANGED);
		mfilter.addAction(Global.EVENT_PLAYER_POWER_STATE_CHANGED);
		registerReceiver(playerEventListener, mfilter);
    }
    
    Timer positionTimer;
    UpdatePositionTask positionTask = new UpdatePositionTask();
    @Override
    protected void onStart() {
    	super.onStart();    	
 	
    	//_this = this;
    	
    	this.progressBar.setVisibility(ProgressBar.GONE);
    	
    	try {
    		Thread.sleep(100,0);
    	} catch (Exception e) {
    		
    	}
		
    	Intent i = new Intent(Global.ACTION_ACTIVITY_UPDATE);
    	i.setComponent(new ComponentName(this, DoubanFmService.class));
    	startService(i);
    	
		mHandler.removeCallbacks(mPositionTask);
		mHandler.removeCallbacks(mOpenPlayerTask);
        
		
    }

	private Runnable mOpenPlayerTask = new Runnable() {
		   public void run() {
		    	//_this.progressBar.setVisibility(ProgressBar.VISIBLE);
				//_this.imageCover.setVisibility(ImageView.GONE);
		    	Intent i = new Intent(Global.ACTION_PLAYER_ON);
		    	i.setComponent(new ComponentName(EasyDoubanFm.this, DoubanFmService.class));
		    	startService(i);
		   }
		};
    
    @Override
    protected void onResume() {
    	super.onResume();


    }
    
    @Override
    protected void onStop() {

    	super.onStop();
    	mHandler.removeCallbacks(mPositionTask);
    	//positionTimer.cancel();
    	//_this = null;
    }
    
    @Override
    protected void onDestroy() {
    	if (playerEventListener != null)
    		unregisterReceiver(playerEventListener);
    	
    	super.onDestroy();
    }
 
    Handler mHandler = new Handler();
	private Runnable mPositionTask = new Runnable() {
		   public void run() {
				synchronized(EasyDoubanFm.class) {
					curPos += 1000;
				}
				textPosition.setText(DateFormat.format("mm:ss", curPos).toString() 
						+ " / " + DateFormat.format("mm:ss", duration).toString());	
				
				mHandler.postDelayed(mPositionTask, 1000);
		   }
		};	
		
	private class UpdatePositionTask extends TimerTask {
		@Override
		public void run() {
			synchronized(EasyDoubanFm.class) {
				curPos ++;
			}
			textPosition.setText(DateFormat.format("mm:ss", curPos).toString() 
					+ " / " + DateFormat.format("mm:ss", duration).toString());	
		}
	}
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
		Debugger.info("EasyDoubanFm.onPrepareOptionsMenu");
		boolean loggedIn = Preference.getLogin(this);
		if (menu.size() > 0) {
			menu.getItem(0).setTitle(loggedIn? "账户登出": "账户登入");
		}
		return true;
	}
    @Override
    public boolean onCreateOptionsMenu (Menu aMenu) {   
    	Debugger.info("EasyDoubanFm.onCreateOptionsMenu");
        super.onCreateOptionsMenu(aMenu);
    	boolean loggedIn = Preference.getLogin(this);
        aMenu.add(0, MENU_LOGIN_ID, 0, loggedIn? "账户登出": "账户登入");
        aMenu.add(0, MENU_ABOUT_ID, 0, "使用说明");
        aMenu.add(0, MENU_TIMER_ID, 0, "设置定时");
        aMenu.add(0, MENU_FEEDBACK_ID, 0, "意见反馈");           
        aMenu.add(0, MENU_CLOSE_ID, 0, "退出程序");
        return true;  
          
    }  
	/*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	Debugger.debug( "onActivityResult: " + requestCode + ", " + resultCode );
    	switch (requestCode) {
    	case 0: {
    		if (resultCode != RESULT_OK) {
    			return;
    		}
    		if (FmChannel.isChannelIdValid(pendingSelChanId)) {
    			Intent i = new Intent(DoubanFmService.ACTION_PLAYER_SELECT_CHANNEL);
                i.putExtra(DoubanFmService.EXTRA_CHANNEL, pendingSelChanId);
                startService(i);
                
                ChannelSelectorActivity.this.finish();
    		}
    		break;
    	}
    	default:
    		break;
    	}
    }*/
    @Override
    public boolean onOptionsItemSelected (MenuItem aMenuItem) {  
        
        switch (aMenuItem.getItemId()) {  
	        case MENU_LOGIN_ID: {
	        	if (!Preference.getLogin(this)) {
		        	Intent intent = new Intent();
	    			intent.setClass(EasyDoubanFm.this, LoginActivity.class);
	    			startActivity(intent);
	        	}
	        	else {
	        		Preference.setLogin(EasyDoubanFm.this, false);
					Preference.setAccountPasswd(EasyDoubanFm.this, null);
					
					popNotify(getResources().getString(R.string.notify_logout_succ));
	        	}
	        	break;
	        }
            case MENU_CLOSE_ID: { 
            	Intent intent = new Intent(Global.ACTION_PLAYER_OFF);
				intent.setComponent(new ComponentName(this, DoubanFmService.class));
				startService(intent);
				this.finish();
                break;  
            }
            case MENU_TIMER_ID: { 
            	Intent intent = new Intent();
    			intent.setClass(EasyDoubanFm.this, SchedulerActivity.class);
    			startActivity(intent);
                break;  
            }
            case MENU_FEEDBACK_ID: {
            	//建立Intent 对象
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                //设置文本格式
                emailIntent.setType("plain/text");
                //设置对方邮件地址
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"saturdaycoder@gmail.com"});
                //设置标题内容
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "豆瓣电台意见反馈");
                //设置邮件文本内容
                //emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                //启动一个新的ACTIVITY,"Sending mail..."是在启动这个ACTIVITY的等待时间时所显示的文字
                startActivity(Intent.createChooser(emailIntent, "Sending mail..."));
                
                break;
            }
            case MENU_ABOUT_ID: {  
            	Intent intent = new Intent(this, IntroductionActivity.class);
            	startActivity(intent);
                break;  
            }
            default:
            	break;
        }  
        return super.onOptionsItemSelected(aMenuItem);
    }  
    private void popNotify(String msg)
    {
        Toast.makeText(this, msg,
                Toast.LENGTH_LONG).show();
    }
    
    private class PlayerEventListener extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (context == null || intent == null) {
				Debugger.error("PlayerEventListener: null context or null intent");
				return;
			}
			
			String action = intent.getAction();
			
			if (action == null) {
				Debugger.error("PlayerEventListener: null action");
				return;
			}
			
			Debugger.verbose("PlayerEventListener got action: " + action);
			
			if (action.equals(Global.EVENT_PLAYER_MUSIC_BANNED)) {
				popNotify("该音乐已被禁止播放");
			}
			
			if (action.equals(Global.EVENT_PLAYER_MUSIC_PREPARE_PROGRESS)) {
				
			}
			
			if (action.equals(Global.EVENT_PLAYER_MUSIC_PROGRESS)) {
				
			}
			
			if (action.equals(Global.EVENT_PLAYER_MUSIC_RATED)) {
				buttonRateUnrate.setImageResource(R.drawable.btn_rated);
			}
			
			if (action.equals(Global.EVENT_PLAYER_MUSIC_STATE_CHANGED)) {
				int state = intent.getIntExtra(Global.EXTRA_STATE, Global.INVALID_STATE);
				switch (state) {
				case Global.STATE_CANCELLED:
				case Global.STATE_ERROR:
				case Global.STATE_FAILED:
				case Global.STATE_IDLE:					
				case Global.STATE_FINISHED:
				case Global.STATE_MUSIC_SKIPPED:
					//imageCover.setVisibility(ImageView.GONE);
					//progressBar.setVisibility(ProgressBar.VISIBLE);
					textArtist.setText("");
					textTitle.setText("");
					buttonRateUnrate.setImageResource(R.drawable.btn_unrated);
					buttonPlayPause.setImageResource(R.drawable.btn_pause);
					break;
				case Global.STATE_MUSIC_PAUSED: {
					String artist = intent.getStringExtra(Global.EXTRA_MUSIC_ARTIST);
					String title = intent.getStringExtra(Global.EXTRA_MUSIC_TITLE);
					boolean israted = intent.getBooleanExtra(Global.EXTRA_MUSIC_ISRATED, false);
					textArtist.setText(artist);
					textTitle.setText(title);
					buttonRateUnrate.setImageResource(israted? R.drawable.btn_rated: R.drawable.btn_unrated);
					buttonPlayPause.setImageResource(R.drawable.btn_play);
					break;
				}
				case Global.STATE_MUSIC_RESUMED: {
					String artist = intent.getStringExtra(Global.EXTRA_MUSIC_ARTIST);
					String title = intent.getStringExtra(Global.EXTRA_MUSIC_TITLE);
					boolean israted = intent.getBooleanExtra(Global.EXTRA_MUSIC_ISRATED, false);
					textArtist.setText(artist);
					textTitle.setText(title);
					buttonRateUnrate.setImageResource(israted? R.drawable.btn_rated: R.drawable.btn_unrated);
					buttonPlayPause.setImageResource(R.drawable.btn_pause);
					break;
				}
				case Global.STATE_PREPARE:
					imageCover.setVisibility(ImageView.GONE);
					progressBar.setVisibility(ProgressBar.VISIBLE);
					break;
				case Global.STATE_STARTED: {
					
					String artist = intent.getStringExtra(Global.EXTRA_MUSIC_ARTIST);
					String title = intent.getStringExtra(Global.EXTRA_MUSIC_TITLE);
					boolean israted = intent.getBooleanExtra(Global.EXTRA_MUSIC_ISRATED, false);
					textArtist.setText(artist);
					textTitle.setText(title);
					buttonRateUnrate.setImageResource(israted? R.drawable.btn_rated: R.drawable.btn_unrated);
					buttonPlayPause.setImageResource(R.drawable.btn_pause);
					break;
				}
				default:
					break;
				}
			}
			
			if (action.equals(Global.EVENT_PLAYER_MUSIC_UNRATED)) {
				buttonRateUnrate.setImageResource(R.drawable.btn_unrated);
			}
			
			
			
			if (action.equals(Global.EVENT_PLAYER_PICTURE_STATE_CHANGED)) {
				int picState = intent.getIntExtra(Global.EXTRA_STATE, Global.INVALID_STATE);
				switch (picState) {
				case Global.STATE_ERROR:
				case Global.STATE_CANCELLED:
				case Global.STATE_IDLE:
				case Global.STATE_PREPARE:
					break;
				case Global.STATE_STARTED:
				case Global.STATE_FINISHED:
					String url = intent.getStringExtra(Global.EXTRA_PICTURE_URL);
					byte[] picdata = HttpFetcher.getInstance().getContent(url);
					
					if (picdata == null) {
						Debugger.error("HttpFetcher can't find " + url);
						return;
					}
					Bitmap bmp = BitmapFactory.decodeByteArray(picdata, 0, picdata.length);
					Debugger.debug("PlayerEventListener.onReceive set image");
					if (bmp != null) {
						imageCover.setImageBitmap(bmp);
					}
					imageCover.setVisibility(ImageView.VISIBLE);
					progressBar.setVisibility(ProgressBar.GONE);
				default:
				}
			}
			
			if (action.equals(Global.EVENT_CHANNEL_CHANGED)) {
				String chan = intent.getStringExtra(Global.EXTRA_CHANNEL);
				if (chan != null && !chan.equals("")) {
					buttonChannel.setText(chan);
				}
			}
			
			if (action.equals(Global.EVENT_LOGIN_STATE_CHANGED)) {
				
			}
			
			if (action.equals(Global.EVENT_PLAYER_POWER_STATE_CHANGED)) {
				
			}
		}
    	
    }
    
}