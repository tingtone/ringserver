package com.kittypad.ringtone.fileupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

/*Codes here are just used to backup
 * Anyone who needs to use this function needs to refer to Amason S3 interfaces and import the Libraries
 */


/*
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
*/
/*
public class S3Sample {
    public static String bucketName = "kittypad_ringtone";
	public static String bucketName = "kittypad_ringtone_midi";
	public static String bucketName = "kittypad_ringtone_m4r";
    public static AmazonS3 s3;
    public static ObjectListing objectListing;
    public static int uploadCount = 0;
    public static void main(String[] args) throws IOException {
    	
        s3 = new AmazonS3Client(new PropertiesCredentials(
                S3Sample.class.getResourceAsStream("AwsCredentials.properties")));   
        
        
        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon S3");
        System.out.println("===========================================\n");
        
        
        
        try {

            String path = "/Users/apple/Desktop/midi1";
    		File file = new File(path);
    		if(!file.exists()){
    			System.out.println("File Not Exist");
    		}
    		uploadmidi(file);
    		
    		String path2 = "/Users/apple/Desktop/midi2";
     		file = new File(path2);
     		if(!file.exists()){
     			System.out.println("File Not Exist");
     		}
     		uploadmidi(file);
            
     		String path3 = "/Users/apple/Desktop/midi3";
     		file = new File(path3);
     		if(!file.exists()){
     			System.out.println("File Not Exist");
     		}
     		uploadmidi(file);
     		
     		String path = "/Users/apple/Desktop/m4r";
    		File file = new File(path);
    		if(!file.exists()){
    			System.out.println("File Not Exist");
    		}
    		uploadm4r(file);
            
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        
        
        try {

            String path = "/Users/apple/Desktop/Funny music";
    		File file = new File(path);
    		if(!file.exists()){
    			System.out.println("File Not Exist");
    		}
    		upload(file);
            

            
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        
    }
    */
    /*This method must be used carefully because it can not be undo*/
/*
    static void deleteAll(String bucketName){
    	objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName));
    	while(objectListing.getObjectSummaries().size() != 0){
    		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
        		System.out.println("Deleting an object\n");
                s3.deleteObject(bucketName, objectSummary.getKey());
            }
    		objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName));
    	}
    	
    }
    
    static void uploadmidi(File f) throws FileNotFoundException{
		File[] ff = f.listFiles();
		for(File child:ff){
			String fileName = child.getName();
			if(child.isDirectory()){
				uploadmidi(child);
			}
			else if(fileName.equals("midilist.txt")){
				Scanner s = new Scanner(child);
				while(s.hasNext()){
					String line = s.nextLine();
					String[] ss = line.split("\\*");
					String id = ss[0];
					String musicName = ss[1];
					String type = ss[3];
					String path = f.getAbsolutePath()+"/"+id+musicName+"."+type;
					String key = id+musicName+"."+type;
					System.out.println("Uploading "+path + " to S3 from a file count number is " + (uploadCount++));
					if(uploadCount >= 0){
						s3.putObject(new PutObjectRequest(bucketName, key, new File(path)));
						s3.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
					}
				}
			}
		}
	}
    
    
    static void uploadm4r(File f) throws FileNotFoundException{
		File[] ff = f.listFiles();
		for(File child:ff){
			String fileName = child.getName();
			if(child.isDirectory()){
				uploadmidi(child);
			}
			else if(fileName.equals("m4rlist.txt")){
				Scanner s = new Scanner(child);
				while(s.hasNext()){
					String line = s.nextLine();
					String[] ss = line.split("\\*");
					String id = ss[0];
					String musicName = ss[1];
					String type = ss[3];
					String path = f.getAbsolutePath()+"/"+id+musicName+"."+type;
					String key = id+musicName+"."+type;
					System.out.println("Uploading "+path + " to S3 from a file count number is " + (uploadCount++));
					if(uploadCount >= 0){
						s3.putObject(new PutObjectRequest(bucketName, key, new File(path)));
						s3.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
					}
				}
			}
		}
	}
	
    static void upload(File f) throws FileNotFoundException{
		File[] ff = f.listFiles();
		for(File child:ff){
			String fileName = child.getName();
			if(child.isDirectory()){
				upload(child);
			}
			else if(fileName.equals("songlist.txt")){
				Scanner s = new Scanner(child);
				while(s.hasNext()){
					String line = s.nextLine();
					String[] ss = line.split("\\*");
					String id = ss[0];
					String musicName = ss[1];
					String type = ss[3];
					String path = f.getAbsolutePath()+"/"+musicName+"."+type;
					String key = id+musicName+"."+type;
					boolean needupload = true;
					System.out.println("Uploading "+path + " to S3 from a file count number is " + (uploadCount++));
					if(uploadCount >= 0){
						for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
							if(objectSummary.getKey().equals(key)){
								needupload = false;
								break;
							}
						}
						if(needupload){
							s3.putObject(new PutObjectRequest(bucketName, key, new File(path)));
							s3.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
						}
					}
				}
			}
		}
	}
}
*/
