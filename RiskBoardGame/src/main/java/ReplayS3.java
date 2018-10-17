package riskboardgame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class ReplayS3 {
	Properties prop = new Properties();
	InputStream input = null;
	String bucketName;
	String key;
    AWSCredentials credentials = null;
	AmazonS3 s3;
	Date currentDate;
	
	public ReplayS3()
	{
		currentDate = new Date();
		
		try {
    		input = new FileInputStream("secret_MeowCat.properties");
    		prop.load(input);

    		String accessKey = prop.getProperty("accessKey");
    		String secretKey = prop.getProperty("secretKey");

    	    credentials = new BasicAWSCredentials(accessKey, secretKey);


    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
		
        s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion("us-west-2")
                .build();

        bucketName = "riskdemo3replaynew";
        key = currentDate.toString();
	}
	public String getBucketName()
	{
		return this.bucketName;
	}
	public String getKey()
	{
		return this.key;
	}
	public String createBucket()
	{
		System.out.println("Creating bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
        return bucketName;
	}
	public int listBuckets()
	{
		int index = 1;
		 System.out.println("Listing buckets");
         for (Bucket bucket : s3.listBuckets()) {
             System.out.println(index++ + " : " + bucket.getName());
         }
         System.out.println();
         return index;
	}
	public void putObject(File file) throws IOException
	{
		System.out.println("Uploading a new object to S3 from a file\n");
        s3.putObject(new PutObjectRequest(bucketName, key, file));
	}
	public boolean downloadObject() throws IOException
	{
		System.out.println("Downloading replay");
        S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));

        System.out.println("Replay Starts: "  + object.getObjectMetadata().getContentType());
        displayTextInputStream(object.getObjectContent());
     
        return true;
	}
	public int listObjects()
	{
		int index = 1;
		 System.out.println("Listing objects");
         ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                 .withBucketName(bucketName)
                 .withPrefix("My"));
         for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
             System.out.println(index++ + " : " + objectSummary.getKey() + "  " +
                                "(size = " + objectSummary.getSize() + ")");
         }
         System.out.println();
         return index;
	}
	public void deleteObject()
	{
		
      System.out.println("Deleting an object\n");
      s3.deleteObject(bucketName, key);
      
	}
	public String deleteBucket()
	{
      System.out.println("Deleting bucket " + bucketName + "\n");
      s3.deleteBucket(bucketName);
      return bucketName;
	}

    public File createFile(ArrayList<String> s) throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        for (int i = 0; i < s.size(); i++)
        {
            writer.write(s.get(i).toString());

        }

        writer.close();

        return file;
    }
    public void displayTextInputStream(InputStream input) throws IOException {
    	int i = 1;
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println(i++ + " : "+ line);
        }
        System.out.println();
    }


}

