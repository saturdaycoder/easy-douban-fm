package com.saturdaycoder.easydoubanfm.channels;
import android.content.res.*;
import android.content.*;
public class FmChannel {
	//public static Context context;
	//private static final String randomChannelName = "随机频道";
	
	public String abbrEn;
	public String nameEn;
	public int channelId;
	public String name;
	public int seqId;
	public String getDisplayName(boolean login) {
		//if (channelId == 0) {
		//	return login? name: randomChannelName;
		//}
		return name;
	}
	public static boolean channelNeedLogin(int chanId) {
		return (chanId == 0);
	}
	
	
	
	/*public static int getChannelIndex(int chanId) {
		int i = 0;
		for (; i < PreinstalledChannels.length; ++i) {
			if (PreinstalledChannels[i].channelId == chanId) {
				return i;
			}
		}
		
		return -1;
		
		
	}
	public static int getFirstPublicChannel() {
		for (int i = 0; i < AllChannels.length; ++i) {
			if (AllChannels[i].channelId > 0) {
				return AllChannels[i].channelId;
			}
		}
		return 0;
	}
	public static boolean isChannelIdValid(int id) {
		//boolean chanFound = false;
		
		for (int i = 0; i < AllChannels.length; ++i) {
			if (id == AllChannels[i].channelId) {
				//chanFound = true;
				//break;
				return true;
			}
		}
		return false;
	}*/
	
	public FmChannel(int channelId, String abbrEn, String nameEn, String name, int seqId) {
		this.channelId = channelId;
		this.seqId = seqId;
		this.name = name;
		this.nameEn = nameEn;
		this.abbrEn = abbrEn;
	}
	public static final FmChannel PrivateChannel = new FmChannel(0, "", "Personal Radio", "私人频道", 0);
	
	public static final FmChannel[] PreinstalledChannels = new FmChannel[] {
		PrivateChannel,
		new FmChannel(1, "CH", "Chinese", "华语", 1),
		new FmChannel(2, "EN", "Euro-American", "欧美", 2),
		new FmChannel(6, "HK", "Cantonese", "粤语", 3),
		new FmChannel(22, "FR", "French", "法语", 4),
		new FmChannel(17, "JPA", "Japanese", "日语", 5),
		new FmChannel(18, "KRA", "Korea", "韩语", 6),
		
		
		new FmChannel(8, "Folk", "Folk", "民谣", 7),
		new FmChannel(7, "Rock", "Rock", "摇滚", 8),
		new FmChannel(13, "Jazz", "Jazz", "爵士", 9),
		new FmChannel(27, "Cla", "Classic", "古典", 10),
		new FmChannel(9, "Easy", "Easy Listening", "轻音乐", 11),
		new FmChannel(14, "Elec", "Electronic", "电子", 12),
		new FmChannel(16, "R&B", "R&B", "R&B", 13),
		new FmChannel(15, "Rap", "Rap", "说唱", 14),
		new FmChannel(10, "Ori", "Original", "电影原声", 15),
		
		new FmChannel(3, "70", "70", "七零", 16),
		new FmChannel(4, "80", "80", "八零", 17),
		new FmChannel(5, "90", "90", "九零", 18),

		new FmChannel(26, "Ar", "Artist", "豆瓣音乐人", 19),
		new FmChannel(48, "Who", "Who's Next?!", "Who's Next?!", 20),
		
		new FmChannel(20, "FEM", "Female", "女声", 21),
		new FmChannel(28, "Ani", "Anime", "动漫", 22),
		new FmChannel(32, "Caf", "Cafe", "咖啡", 23),
		
		new FmChannel(50, "CN", "China original", "中国原创盛典", 24),
		new FmChannel(51, "K5", "K5 classic", "K5经典乐赏", 25),
		new FmChannel(52, "Le", "LeHun", "乐混翻唱", 26),

	};
}
