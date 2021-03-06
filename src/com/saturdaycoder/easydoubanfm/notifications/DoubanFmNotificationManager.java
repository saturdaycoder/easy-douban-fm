package com.saturdaycoder.easydoubanfm.notifications;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.widget.Toast;

import com.saturdaycoder.easydoubanfm.Debugger;
import com.saturdaycoder.easydoubanfm.DoubanFmService;
import com.saturdaycoder.easydoubanfm.EasyDoubanFm;
import com.saturdaycoder.easydoubanfm.Global;
import com.saturdaycoder.easydoubanfm.R;
import com.saturdaycoder.easydoubanfm.SchedulerActivity;
import com.saturdaycoder.easydoubanfm.downloader.IDownloaderObserver;
import com.saturdaycoder.easydoubanfm.player.IPlayerObserver;
import com.saturdaycoder.easydoubanfm.scheduling.ISchedulerObserver;
import com.saturdaycoder.easydoubanfm.scheduling.SchedulerManager;
import com.saturdaycoder.easydoubanfm.scheduling.SchedulerTask;

public class DoubanFmNotificationManager 
								implements ISchedulerObserver, 
										   IPlayerObserver, 
										   IDownloaderObserver {
	
	private NotificationManager notManager; 
	private Context context;
	
	private static DoubanFmNotificationManager inst;
	public static DoubanFmNotificationManager getInstance(Context context) {
		synchronized(DoubanFmNotificationManager.class) {
			if (inst == null) {
				synchronized(DoubanFmNotificationManager.class) {
					if (inst == null) {
						inst = new DoubanFmNotificationManager(context);
					}
				}
			}
		}
		return inst;
	}
	
	private DoubanFmNotificationManager(Context context) {
		this.context = context;
		this.notManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	
    private void popNotify(String msg)
    {
        Toast.makeText(context, msg,
                Toast.LENGTH_LONG).show();
    }
    
	@Override
	public void onPosition(long pos, long total) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFinished() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPaused() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onResumed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSkipped() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRated() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUnrated() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChannelSwitched(int chanId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTaskEnabled(int type, Date when) {
		// TODO Auto-generated method stub
		Debugger.debug("DoubanFmNotification.onTaskEnabled(" + type + ", " + when.getTime() + ")");
		
		Intent it = new Intent(context, SchedulerActivity.class);
		PendingIntent pi = PendingIntent.getActivity(context, 0, it, 0);
		
		String notText = "";
		int notIconId = android.R.drawable.ic_dialog_alert;
		int notId = 0;
		String popText = "";
		switch (type) {
		case Global.SCHEDULE_TYPE_START_PLAYER:
			notText = context.getResources().getString(R.string.notify_start_timer);
			notIconId = android.R.drawable.ic_lock_idle_alarm;
			notId = Global.NOTIFICATION_ID_SCHEDULE_START;
			popText = "开启";
			break;
		case Global.SCHEDULE_TYPE_STOP_PLAYER:
			notText = context.getResources().getString(R.string.notify_stop_timer);
			notIconId = android.R.drawable.ic_lock_idle_alarm;
			notId = Global.NOTIFICATION_ID_SCHEDULE_STOP;
			popText = "关闭";
			break;
		default:
			return;
		}
		
		
		Notification notification = new Notification(notIconId, 
				notText,
                System.currentTimeMillis());
		
		notification.contentIntent = pi;
		notification.flags |= Notification.FLAG_NO_CLEAR;
		notification.setLatestEventInfo(context, notText, when.toLocaleString(), pi);	
		
		try {
			notManager.notify(notId, notification);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onTaskDisabled(int type) {
		// TODO Auto-generated method stub
		Debugger.debug("DoubanFmNotification.onTaskDisabled(" + type + ")");
		cancelSchedulerNotification(type);
	}
	
	private void cancelAllSchedulerNotifications() {
		cancelSchedulerNotification(Global.NOTIFICATION_ID_SCHEDULE_START);
		cancelSchedulerNotification(Global.NOTIFICATION_ID_SCHEDULE_STOP);
	}
	
	private void cancelSchedulerNotification(int type) {
		Debugger.debug("DoubanFmNotification.onTaskDisabled(" + type + ")");
		int notId = 0;
		switch (type) {
		case Global.SCHEDULE_TYPE_START_PLAYER:
			notId = Global.NOTIFICATION_ID_SCHEDULE_START;
			break;
		case Global.SCHEDULE_TYPE_STOP_PLAYER:
			notId = Global.NOTIFICATION_ID_SCHEDULE_STOP;
			break;
		default:
			return;
		}
		
		try {
			notManager.cancel(notId);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	@Override
	public void onTaskFinished(int type) {
		// TODO Auto-generated method stub
		Debugger.debug("DoubanFmNotification.onTaskFinished(" + type + ")");
		cancelSchedulerNotification(type);
	}
	@Override
	public void onTaskTicked(int type, long millisUntilFinish) {
		// TODO Auto-generated method stub
		Debugger.debug("DoubanFmNotification.onTaskTicked(" + type + ", " + millisUntilFinish + ")");
	}
	
	
}
