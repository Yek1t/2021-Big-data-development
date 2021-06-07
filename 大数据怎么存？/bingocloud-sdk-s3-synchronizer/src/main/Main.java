package main;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * @author Yekit
 */
public class Main {

	private static long partSize = 20 << 20;
	
	private final static String bucketName = "yekit";
	private final static String bucketPath = "/upload";
	private final static String filePath = "C:\\Users\\57256\\Desktop\\shixun\\upload";
	private final static String accessKey = "";
	private final static String secretKey = "";
	private final static String serviceEndpoint = "http://10.16.0.1:81";
	private final static String signingRegion = "";

	
	private final static BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
	private final static ClientConfiguration ccfg = new ClientConfiguration().withUseExpectContinue(true);
	private final static EndpointConfiguration endpoint = new EndpointConfiguration(serviceEndpoint, signingRegion);
	
	private final static AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withClientConfiguration(ccfg)
            .withEndpointConfiguration(endpoint)
            .withPathStyleAccessEnabled(true)
            .build();
	
	public static void main(String args[]) {	
		System.out.println("This is a simple file synchronization software.");
		System.out.println("1. Download all files from the bucket.");
		System.out.println("2. Synchronize all local files to bucket.");
		System.out.println("3. Exit.\n");
		System.out.println("Please enter your choice:");
		
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		while(scan.hasNextInt()) {
			choice = scan.nextInt();
			
			switch(choice) {
			case 1:
				downloadAllFile();
				break;
			case 2:
				sync();
				break;
			case 3:
				System.out.println("Shut down the software.");
				System.exit(0);
				break;
			default:
				System.exit(0);
			}
			System.out.println("1. Download all files from the bucket.");
			System.out.println("2. Synchronize all local files to bucket.");
			System.out.println("3. Exit.\n");
			System.out.println("Please enter your choice:");
		}
		scan.close();
	}
		
		
	/**
	 * This method synchronizes the files: 
	 * the local added and modified files are uploaded, 
	 * and the local deleted files are deleted from the bucket.
	 * @param none
	 * @return none
	 */
	public static void sync() {
		File file = new File(filePath);
		List<String> fileList = new ArrayList<String>(Arrays.asList(file.list()));
		List<String> list = getFile();
		List<String> newFileList = new ArrayList<String>(fileList);
		List<String> newList = new ArrayList<String>(list);
		
		for(String s1 : fileList) {
			for(String s2 : list) {
				if(s1.equals(s2)) {
					if(compare(s1)) { // 比较本地文件与桶中文件的ETag值，若不相同则重新上传。
						newFileList.remove(s1);
						newList.remove(s2);
					}
					else newList.remove(s2);
				}
			}
		}
		
		System.out.println("Start syncing......\n");
		
		for(String s1 : newFileList) {
			System.out.println(s1 + " doesn't exist in the bucket.\n");
			uploadFile(s1);
			System.out.println("Uploading " + s1 + " successfully!\n");
		}
		
		for(String s2 : newList) {
			System.out.println(s2 + " doesn't exist at local.\n");
			s3.deleteObject(bucketName + bucketPath, s2);
			System.out.println("Deleting " + s2 + " successfully!\n");
		}
		System.out.println("Complete synchronization!\n");
	}
	
	
	/**
	 * Description: This method obtains the information of the objects in the bucket.
	 * @param none
	 * @return result: a list of the information of the bucket's object.
	 */
	public static List<String> getFile() {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
		listObjectsRequest.setBucketName(bucketName);
		listObjectsRequest.setDelimiter("/");
		listObjectsRequest.setPrefix("upload/");
		ObjectListing listing = s3.listObjects(listObjectsRequest);

		List<String> result = new ArrayList<String>();
		for (S3ObjectSummary objectSummary : listing.getObjectSummaries()) {
		    result.add(objectSummary.getKey().toString().substring(7));
		}
		return result;
	}
	
	/**
	 * This method downloads files from the bucket.
	 * @param object file name
	 * @return none
	 */
	public static void downloadFile(String object) {		
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName + bucketPath, object);
		ObjectMetadata oMetaData = s3.getObjectMetadata(bucketName + bucketPath, object);
		long contentLength = oMetaData.getContentLength();
		
		S3ObjectInputStream s3is = null;
		FileOutputStream fos = null;
		
		if(contentLength<=partSize) {
			oMetaData = s3.getObject(getObjectRequest, new File(filePath + "\\" + object));
		}
		
		else {
			try {
				File file = new File(filePath + "\\" + object);
				S3Object o = null;
				fos = new FileOutputStream(file);
				// Step 2: Download parts.
				long filePosition = 0;
				for (int i = 1; filePosition < contentLength; i++) {
					// Last part can be less than 20 MB. Adjust part size.
					partSize = Math.min(partSize, contentLength - filePosition);
	
					// Create request to download a part.
					getObjectRequest.setRange(filePosition, filePosition + partSize);
					o = s3.getObject(getObjectRequest);
	
					// download part and save to local file.
					System.out.format("Downloading part %d\n", i);
	
					filePosition += partSize+1;
					s3is = o.getObjectContent();
					byte[] read_buf = new byte[64 * 1024];
					int read_len = 0;
					while ((read_len = s3is.read(read_buf)) > 0) {
						fos.write(read_buf, 0, read_len);
					}
				}
	
				// Step 3: Complete.
				System.out.println("Completing download");
	
				System.out.format("save %s to %s\n", object, filePath + "\\" + object);
			} catch (Exception e) {
				System.err.println(e.toString());
				
				System.exit(1);
			} finally {
				if (s3is != null) try { s3is.close(); } catch (IOException e) { }
				if (fos != null) try { fos.close(); } catch (IOException e) { }
			}
		}
		
		System.out.println("Downloading " + object + " successfully!\n");
	}
	
	/**
	 * This method downloads all files from the bucket through method downloadFile().
	 * @param none
	 * @return none
	 */
	public static void downloadAllFile() {
		List<String> list = new ArrayList<String>();
		list = getFile();
		System.out.println("All files in the bucket has been read.");
		System.out.println("Trying to download all files......\n");
		for(String s : list) {
			downloadFile(s);
		}
		System.out.println("Done!\n");
	}
	
	/**
	 * This method uploads files to the bucket.
	 * When the file size is bigger than 20MB,
	 * multipart upload is enabled.
	 * @param object: file name
	 * @return none
	 */
	public static void uploadFile(String object) {
		File file = new File(filePath + "\\" + object);
		if(file.length()<=partSize) {
			try {
				s3.putObject(bucketName + bucketPath, object, file);
			}
			catch (AmazonServiceException e) {
	            if (e.getErrorCode().equalsIgnoreCase("NoSuchBucket")) {
	                s3.createBucket(bucketName);
	            }
	            System.err.println(e.toString());
	            System.exit(1);
	        }
			catch (AmazonClientException e) {
	            try {
	                // detect bucket whether exists
	                s3.getBucketAcl(bucketName);
	            } catch (AmazonServiceException ase) {
	                if (ase.getErrorCode().equalsIgnoreCase("NoSuchBucket")) {
	                    s3.createBucket(bucketName);
	                }
	            } catch (Exception ignore) {
	            }
	            System.err.println(e.toString());
	            System.exit(1);
	        }
		}
		else {
			ArrayList<PartETag> partETags = new ArrayList<PartETag>();
			long contentLength = file.length();
			String uploadId = null;
			try {
				// Step 1: Initialize.
				InitiateMultipartUploadRequest initRequest = 
						new InitiateMultipartUploadRequest(bucketName + bucketPath, object);
				uploadId = s3.initiateMultipartUpload(initRequest).getUploadId();
				System.out.format("Created upload ID was %s\n", uploadId);

				// Step 2: Upload parts.
				long filePosition = 0;
				for (int i = 1; filePosition < contentLength; i++) {
					// Last part can be less than 20 MB. Adjust part size.
					partSize = Math.min(partSize, contentLength - filePosition);

					// Create request to upload a part.
					UploadPartRequest uploadRequest = new UploadPartRequest()
							.withBucketName(bucketName + bucketPath)
							.withKey(object)
							.withUploadId(uploadId)
							.withPartNumber(i)
							.withFileOffset(filePosition)
							.withFile(file)
							.withPartSize(partSize);

					// Upload part and add response to our list.
					System.out.format("Uploading part %d\n", i);
					partETags.add(s3.uploadPart(uploadRequest).getPartETag());

					filePosition += partSize;
				}

				// Step 3: Complete.
				System.out.println("Completing upload");
				CompleteMultipartUploadRequest compRequest = 
						new CompleteMultipartUploadRequest(bucketName + bucketPath, object, uploadId, partETags);

				s3.completeMultipartUpload(compRequest);
			} catch (Exception e) {
				System.err.println(e.toString());
				if (uploadId != null && !uploadId.isEmpty()) {
					// Cancel when error occurred
					System.out.println("Aborting upload");
					s3.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName + bucketPath, object, uploadId));
				}
				System.exit(1);
			}
		}
		System.out.format("Uploading %s to %s successfully!\n", object, bucketName + bucketPath);
	}
	
	/**
	 * This method calculates the local file's ETag value.
	 * @param object: file name
	 * @return s: local file's ETag value
	 */
	public static String calculateETag(String object) {
		File file = new File(filePath + "\\" + object);
		String s = "";
		try {
			InputStream is = new FileInputStream(file);
			String digest = DigestUtils.md5Hex(is); // 计算MD5值
			s = digest;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * The method determines whether two values are equal.
	 * @param object: file name
	 * @return flag: a boolean value 
	 */
	public static boolean compare(String object) {
		boolean flag = false;
		String local = calculateETag(object);
		String bucket = s3.getObjectMetadata(bucketName + bucketPath, object).getETag(); // 获取桶中相同文件名的ETag
		if(local.equals(bucket)) 
			flag = true;
		return flag;
	}
}
