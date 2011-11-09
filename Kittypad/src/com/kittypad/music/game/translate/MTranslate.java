package com.kittypad.music.game.translate;

import java.util.ArrayList;

import com.memetix.mst.translate.Translate;
import com.memetix.mst.language.Language;





public class MTranslate {
	
   private static String key="88640666ED3FC12E62E5D28F70E1C67C4AA95E08";
	/*public static void main(String []argv) throws Exception{
	  //Translate translate=new Translate();
	Translate.setKey(key);
	String text=Translate.execute("hi",Language.ENGLISH,Language.CHINESE_SIMPLIFIED);
	System.out.println(text);
    String translate=Translate.execute("您好", Language.ENGLISH);
    System.out.println(translate);
	}*/
	public static String translate(String orignal) throws Exception{
		Translate.setKey(key);
		String result=Translate.execute(orignal, Language.ENGLISH);
		
		return result;
		
	}
	public static String translate(String orignal,String toLanguage) throws Exception{
		Translate.setKey(key);
		String result=Translate.execute(orignal,StringToLanguage.getLanguage(toLanguage));
		return result;
	}
	
	public static void main(String []argv) throws Exception{
		String orginal="天空之城";
		String english=translate(orginal);
		System.out.println(english);
		String finall=translate(english,"zh-Hans");
		System.out.println(finall);
		
	}
}
