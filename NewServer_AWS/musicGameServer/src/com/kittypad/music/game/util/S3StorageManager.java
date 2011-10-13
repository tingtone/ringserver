/*
 * Copyright 2010-2011 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.kittypad.music.game.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.StorageClass;


/**
 * This is a class for storage of any kind of data on S3.  There is some functionality included in this
 * class that's not used in the TravelLog application but should serve to illustrate additional
 * capabilities of S3.
 *
 */
public class S3StorageManager {

	private static Date lastUpdate;

	/*
	 * The s3 client class is thread safe so we only ever need one static instance.
	 * While you can have multiple instances it is better to only have one because it's
	 * a relatively heavy weight class.
	 */
	private static AmazonS3 s3client;
	private static String bucketName="kittypad_music_game";
	private static String mimeType="text/html";
	
	 static 
	{

		
			if(s3client==null){
			try {
				s3client=new AmazonS3Client(new PropertiesCredentials(
				        S3StorageManager.class.getClassLoader().getResourceAsStream("/AwsCredentials.properties")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		
	}
	
    

	

	public  Date getLastUpdate() {
		return lastUpdate;
	}

	private static final Logger logger = Logger.getLogger(S3StorageManager.class.getName());


	/**
	 * Stores a given item on S3
	 * @param obj the data to be stored
	 * @param reducedRedundancy whether or not to use reduced redundancy storage
	 * @param acl a canned access control list indicating what permissions to store this object with (can be null to leave it set to default)
	 */
	public  void store(MusicItem obj,byte[] data ,boolean reducedRedundancy, CannedAccessControlList acl) {
		// Make sure the bucket exists before we try to use it
		//checkForAndCreateBucket(this.bucketName);
		String key=obj.getUUID()+obj.getMusicName()+"."+obj.getType();
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentType(mimeType);
		omd.setContentLength(obj.getSize());
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		PutObjectRequest request = new PutObjectRequest(bucketName, key,is,omd);
		// Check if reduced redundancy is enabled
		if (reducedRedundancy) {
			request.setStorageClass(StorageClass.ReducedRedundancy);
		}
		s3client.putObject(request);
		// If we have an ACL set access permissions for the the data on S3
		if (acl!=null) {
			s3client.setObjectAcl(bucketName, key, acl);
		}
	}

	

	/**
	 * This is a convenience method that stores an object as publicly readable
	 *
	 * @param obj object to be stored
	 * @param reducedRedundancy flag indicating whether to use reduced redundancy or not
	 */
	public  void storePublicRead (MusicItem obj,byte[]data, boolean reducedRedundancy) {
		store(obj,data,reducedRedundancy,CannedAccessControlList.PublicRead);
	}

	

}
