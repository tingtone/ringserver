package com.kittypad.music.game.translate;

import java.util.HashMap;
import java.util.Map;

import com.memetix.mst.language.Language;


public class StringToLanguage {
	public static Map<String,Language> languageMap=new HashMap<String,Language>();
	public static String []languageStr= {
		"zh-Hans","en","ja","fr",
		"de","nl","it","es",
		"pt","pt-PT","da","fi",
		"nb","sv","ko","zh-Hant",
		"ru","pl","tr","uk",
		"ar","hr","cs","el",
		"he","ro","sk","th",
		"id","ms","en-GB","ca",
		"hu","vi"
	};
	public static Language[] lang={
		Language.CHINESE_SIMPLIFIED,Language.ENGLISH,Language.JAPANESE,Language.FRENCH,
		Language.GERMAN,Language.DUTCH,Language.ITALIAN,Language.SPANISH,
		Language.PORTUGUESE,Language.PORTUGUESE,Language.DANISH,Language.FINNISH,
		Language.NORWEGIAN,Language.SWEDISH,Language.KOREAN,Language.CHINESE_TRADITIONAL,
		Language.RUSSIAN,Language.POLISH,Language.TURKISH,Language.UKRANIAN,
		Language.ARABIC,Language.ENGLISH,Language.CZECH,Language.GREEK,
		Language.HEBREW,Language.ROMANIAN,Language.SLOVAK,Language.THAI,
		Language.INDONESIAN,Language.ENGLISH,Language.ENGLISH,Language.ENGLISH,
		Language.HUNGARIAN,Language.VIETNAMESE
	};
	static{
		for(int i=0;i<lang.length;i++)
			languageMap.put(languageStr[i], lang[i]);	
	}
	public static Language getLanguage(String languageStr){
		return languageMap.get(languageStr);
	}
	
}
