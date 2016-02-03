package com.psl.speechtext;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechModel;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.util.MediaType;

public class SpeechText {
	
	public static String url = "https://stream.watsonplatform.net/speech-to-text/api";
	public static String username = "40828e3d-e386-4d88-a7ba-86096a6b8d88";
	public static String password = "8tz46qWM2IxF";
	
	
	public static synchronized String getText(File audiofile) {
		
		
		System.out.println("Uploaded file name :" + audiofile.getName());
		System.out.println("Uploaded file Size :" + audiofile.length());
		
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword(username,password);		
		Map params = new HashMap();
		params.put("audio", audiofile);
		params.put("content_type", MediaType.AUDIO_WAV);
		params.put("word_confidence", false);
		params.put("continuous", false);
		params.put("timestamps", false);
		params.put("inactivity_timeout", 30);
		params.put("max_alternatives", 1);

		SpeechResults transcript = service.recognize(params)	;

		return transcript.toString();
		
		
	}

}
