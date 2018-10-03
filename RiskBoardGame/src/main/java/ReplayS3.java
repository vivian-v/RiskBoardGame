package demo3;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
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

	String bucketName;
	String key;
	AWSCredentials credentials;
	AmazonS3 s3;
	public ReplayS3()
	{
		credentials = null;
		
		try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\ChangJin\\.aws\\credentials), and is in valid format.",
                    e);
        }

        s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion("us-west-2")
            .build();

        bucketName = "riskdemo3" + UUID.randomUUID();
        key = "MyObjectKey";
	}
	
	public void createBucket()
	{
		System.out.println("Creating bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
	}
	public void listBuckets()
	{
		 System.out.println("Listing buckets");
         for (Bucket bucket : s3.listBuckets()) {
             System.out.println(" - " + bucket.getName());
         }
         System.out.println();
	}
	public void putObject() throws IOException
	{
		System.out.println("Uploading a new object to S3 from a file\n");
        s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));
	}
	public void downloadObject() throws IOException
	{
		System.out.println("Downloading an object");
        S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
        System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
        displayTextInputStream(object.getObjectContent());
	}
	public void listObjects()
	{
		 System.out.println("Listing objects");
         ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                 .withBucketName(bucketName)
                 .withPrefix("My"));
         for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
             System.out.println(" - " + objectSummary.getKey() + "  " +
                                "(size = " + objectSummary.getSize() + ")");
         }
         System.out.println();
	}
	public void deleteObject()
	{
      System.out.println("Deleting an object\n");
      s3.deleteObject(bucketName, key);
	}
	public void deleteBucket()
	{
      System.out.println("Deleting bucket " + bucketName + "\n");
      s3.deleteBucket(bucketName);
	}
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("Chang is God\n");
//        writer.write("01234567890112345678901234\n");
//        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
//        writer.write("01234567890112345678901234\n");
//        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
}

