package jenpack;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

public class JenkinsAPIExample {
	JenkinsServer jenkins;

	public JenkinsAPIExample() throws Exception {
		// TODO Auto-generated constructor stub
		connectJenkins();
	}
	
	public void connectJenkins() throws Exception {
		//String jenkinsUrl="http://10.76.66.43:8080/";
		String jenkinsUrl1="http://10.74.22.53:8080/";
		String jenkinsUser="admin";
		//String jenkinsToken="77c356322e364d0f9c3f646e78ed558b";
		String jenkinsToken1="95dadaf69976a137bc33acbfeec0135b";//Legacy Token
		//String jenkinsToken1="95dadaf69976a137bc33acbfeec0135b";
		/*jenkins=new JenkinsServer(new URI(jenkinsUrl),jenkinsUser,jenkinsToken);*/
		jenkins=new JenkinsServer(new URI(jenkinsUrl1),jenkinsUser,jenkinsToken1);
		
	}
	
	public void getJobDetails(String jobName) throws Exception {
		JobWithDetails jobDetails=jenkins.getJob(jobName);
		if(!checkJobNameExists(jobName))
			return;
		System.out.println("Job Name: "+jobDetails.getName()+" Job Last build: "+jobDetails.getLastBuild().getNumber());
		//System.out.println("Job status"+jobDetails.getLastBuild().details().getResult().toString());
		getLastJobStatus(jobName);
		//System.out.println(jenkins.getJobXml(jobName));
	}
	
	public String getJobDetailsString(String jobName) throws Exception {
		JobWithDetails jobDetails=jenkins.getJob(jobName);
		if(!checkJobNameExists(jobName))
			return checkJobNameExistsS(jobName);
		/*return "Job Name: "+jobDetails.getName()+"|| Job Last build: "+jobDetails.getLastBuild().getNumber()+
				"\n||"+getLastJobStatusString(jobName);*/
		return "Job Name: "+jobDetails.getName()+"|| Job Last build: "+jobDetails.getLastBuild().getNumber();
		//System.out.println("Job status"+jobDetails.getLastBuild().details().getResult().toString());
		//getLastJobStatus(jobName);
		//System.out.println(jenkins.getJobXml(jobName));
	}
	
	public void getLastJobStatus(String jobName) throws IOException {
		JobWithDetails jobDetails=jenkins.getJob(jobName);
		System.out.println("Job status of "+jobDetails.getLastBuild().getNumber()+" is "+jobDetails.getLastBuild().details().getResult().toString());
	}
	
	public String getLastJobStatusString(String jobName) throws IOException {
		JobWithDetails jobDetails=jenkins.getJob(jobName);
		return "Job status of "+jobDetails.getLastBuild().getNumber()+" is "+jobDetails.getLastBuild().details().getResult().toString();
	}
	
	public void getXMLOfJob(String jobName) throws IOException {
		System.out.println(jenkins.getJobXml(jobName));
	}
	
	public String getXMLOfJobString(String jobName) throws IOException {
		return jenkins.getJobXml(jobName);
	}
	
	public void printAllJobs() throws IOException {
		Map<String, Job> jobs=jenkins.getJobs();
		System.out.println(jobs.size()+" jobs: ");
		for (String jobName:jobs.keySet()) {
			System.out.println(jobName);
		}
	}
	
	public void createJob(String name, String xml) throws IOException
	{
		if(!checkJobNameExists(name)) {
			jenkins.createJob(name, xml);
			System.out.println("Job created");
		}else
			return;
	}
	
	public String createJobString(String name, String xml) throws IOException
	{
		if(!checkJobNameExists(name)) {
			jenkins.createJob(name, xml);
			System.out.println("Job created");
			return "Job created";
		}else
			return checkJobNameExistsS(name);
	}
	
	public boolean createJobBoolean(String name, String xml) throws IOException
	{
		if(!checkJobNameExists(name)) {
			jenkins.createJob(name, xml);
			System.out.println("Job created");
			return true;
		}else
			return false;
	}
	
	public void buildJob(String jobName) throws IOException, InterruptedException {
		JobWithDetails job=jenkins.getJob(jobName);
		if(!checkJobNameExists(jobName))
			return;
		int buildNo=job.getNextBuildNumber();
		job.build();
		boolean built=false;
		//System.out.println(job.build());
		System.out.println("Job is building with number "+buildNo);
		//Thread.sleep(2000);
		//System.out.println("Building:"+job.details().isBuildable());
		while(!built) {		
			//System.out.print("Building:"+job.details().isBuildable()+"\t");
			if(jenkins.getJob(jobName).getLastBuild().getNumber()==buildNo) {
				built=true;
			}
		}
		//System.out.println("Build No :"+buildNo+"\tBuilding:"+job.getBuildByNumber(buildNo).details().isBuilding());
		System.out.println("Job Build is complete");
		Thread.sleep(5000);
		getLastJobStatus(jobName);//Works
		getConsoleOutputOfJob(jobName);
		//if(getLastJobStatus(jobName)!="SUCCESS")
	}
	
	public String buildJobString(String jobName) throws IOException, InterruptedException {
		if(!checkJobNameExists(jobName))
			return checkJobNameExistsS(jobName);
		JobWithDetails job=jenkins.getJob(jobName);
		int buildNo=job.getNextBuildNumber();
		job.build();
		//boolean built=false;
		//System.out.println(job.build());
		return "Job is building with number "+buildNo;
	}
	
	public void getConsoleOutputOfJob(String jobName) throws IOException {
		if(!checkJobNameExists(jobName))
			return;
		System.out.println("Console output:\n"+jenkins.getJob(jobName).getLastBuild().details().getConsoleOutputText());
	}
	
	public String getConsoleOutputOfJobString(String jobName) throws IOException {
		if(!checkJobNameExists(jobName))
			return checkJobNameExistsS(jobName);
		System.out.println("Console output:\n"+jenkins.getJob(jobName).getLastBuild().details().getConsoleOutputText());
		return "Console output:\n"+jenkins.getJob(jobName).getLastBuild().details().getConsoleOutputText();
	}
	
	public void deleteJob(String jobName) throws IOException {
		if(checkJobNameExists(jobName)) {
			jenkins.deleteJob(jobName);
			System.out.println(jobName+" Job deleted");
		}else
			return;
//		jenkins.deleteJob(jobName);
//		System.out.println(jobName+" Job deleted");
	}
	
	public String deleteJobString(String jobName) throws IOException {
		if(checkJobNameExists(jobName)) {
			jenkins.deleteJob(jobName);
			System.out.println(jobName+" Job deleted");
			return jobName+" Job deleted";
		}else
			return checkJobNameExistsS(jobName);
	}
	
	public boolean deleteJobBoolean(String jobName) throws IOException {
		if(checkJobNameExists(jobName)) {
			jenkins.deleteJob(jobName);
			System.out.println(jobName+" Job deleted");
			return true;
		}else
			return false;
	}
	

	/*private void waitForBuildToComplete(String jobName) throws InterruptedException, IOException {
	    boolean buildCompleted = false;
	    Long timeoutCounter = 0L;
	    while (!buildCompleted) {
	        Thread.sleep(2000);
	        timeoutCounter = timeoutCounter + 2000L;
	        //When the build is in the queue, the nextbuild number didn't change.
	        //When it changed, It might still be running.
	        JobWithDetails wrkJobData = jenkins.getJob(jobName);
	        int newNextNbr =wrkJobData.getNextBuildNumber();
	        //log.info("New Next Nbr:" + newNextNbr);
	        if (wrkJobData.getNextBuildNumber() == newNextNbr) {
	            //log.info("The expected build is there");
	            boolean isBuilding = wrkJobData.getLastBuild().details().isBuilding();
	            if (!isBuilding) {
	                buildCompleted = true;
	            }
	        }
	    }
	    getLastJobStatus(jobName);
	}*/

	
	public boolean checkJobNameExists(String jobName) throws IOException {
		JobWithDetails job=jenkins.getJob(jobName);
		if(job==null) {
			System.out.println(jobName+" doesn't exist!");
			return false;
		}else {
			System.out.println(jobName+" exist!");
			return true;
		}
	}
	
	public String checkJobNameExistsS(String jobName) throws IOException {
		JobWithDetails job=jenkins.getJob(jobName);
		if(job==null) {
			System.out.println(jobName+" doesn't exist!");
			return jobName+" doesn't exist!";
		}else {
			System.out.println(jobName+" exist!");
			return jobName+" exist!";
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		JenkinsAPIExample jen=new JenkinsAPIExample();
		jen.connectJenkins();
		jen.printAllJobs();
		jen.checkJobNameExists("APITesting");
		jen.checkJobNameExists("WebsiteLogin");
		jen.getJobDetails("WebsiteLogin");
		jen.getJobDetails("PracAPi");
		//jen.getJobDetails("practice");
		//jen.deleteJob("PracApi");
		jen.deleteJob("PracApi");
		//jen.getJobDetails("PracApi");
		XMLParserClass x1=new XMLParserClass("C:\\Users\\tirth.joshi\\Desktop\\Work\\dockerjenkins-master\\src\\main\\resources\\confignew.xml");
		//XMLParserClass x1=new XMLParserClass("D:\\Java eclipse\\workspace\\SimpleJspWeb\\src\\main\\resources\\configSimpleWithToken.xml");
		x1.getXMLProperty("builders.hudson..tasks..BatchFile.command");
		String s="java -version\n"
				+ "mvn -version";
		x1.setXMLProperty("builders.hudson..tasks..BatchFile.command", s);
		jen.createJob("PracApi", x1.convertXMlToString());
		jen.buildJob("PracApi");
		//jen.buildJob("Docker");
		//jen.buildJob("PracApi");
		//jen.buildJob("practice");
		/*XMLParserClass x2=new XMLParserClass("src/main/resources/configDockerParam.xml");
		//x2.setXMLProperty("scm.userRemoteConfigs.hudson..plugins..git..UserRemoteConfig.credentialsId", "");
		jen.createJob("DockerParam",x2.convertXMlToString());
		jen.checkJobNameExists("DockerParam");*/
		
		//jen.getConsoleOutputOfJob("Pracapi");
		//jen.waitForBuildToComplete("PracAPi");
		//jen.getXMLOfJob("Docker");
	}

}
