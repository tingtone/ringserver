	/** 
	 *  @author yuan mingzhu 
	 *  this class is used to create an bucket for user uploaded music file  
	 * 
	 */
package com.kittypad.ringtone.init;

import java.io.IOException;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.*;

public class BucketInit {
	public static void main(String []args) throws IOException{

	 AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(
             BucketInit.class.getResourceAsStream("AwsCredentials.properties")));
     String bucketName = "ringtone_user" ;
     String key = "kittypad_ring_piano";
     try {
       
         System.out.println("Creating bucket " + bucketName + "\n");
         s3.createBucket(bucketName);

     }catch(Exception e){
    	 
     }
}



}
