package com.chenxun.www.client;

import com.chenxun.www.contract.Vote;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;


public class VoteClient {

    static Logger logger = LoggerFactory.getLogger(VoteClient.class);

    private BcosSDK bcosSDK;
    private Client client;
    private CryptoKeyPair cryptoKeyPair;
    public static VoteClient voteClient=new VoteClient();

    static {
        //initialize
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        voteClient.bcosSDK=context.getBean(BcosSDK.class);
        voteClient.client =voteClient.bcosSDK.getClient(1);
        voteClient.cryptoKeyPair=voteClient.client.getCryptoSuite().createKeyPair();
        logger.debug("create a client successfully, whose address is: "+voteClient.cryptoKeyPair.getAddress());
    }

    public void deployVoteAddress(){
        try{
            Vote vote=Vote.deploy(voteClient.client,voteClient.cryptoKeyPair);
            System.out.println("deploy is successful : "+vote.getContractAddress());
            //store the address (so it can be loaded)
            Properties prop = new Properties();
            prop.setProperty("address", vote.getContractAddress());
            final Resource contractResource = new ClassPathResource("contract.properties");
            FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
            prop.store(fileOutputStream, "contract address");
        }catch (Exception e){
            System.out.println("deploy is failed : "+e.getMessage());
            System.exit(0);
        }
    }
    public String loadVoteAddress() throws Exception {
        Properties prop =new Properties();
        final Resource contractResource=new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress =prop.getProperty("address");
        if (contractAddress == null || contractAddress.trim().equals("")){
            throw new Exception("load the contract failed , it seems hasn't been deployed ");
        }
        logger.info("load successfully,the contract address is : "+contractAddress);
        return contractAddress;
    }

    public void addCandidate(String name) throws Exception {
        String contractAddress = loadVoteAddress();
        Vote vote= Vote.load(contractAddress,client,cryptoKeyPair);
        byte[] bytes = name.getBytes();

        TransactionReceipt receipt = vote.addCandidate(bytes);
        List<Vote.AddCandidateEventResponse> response = vote.getAddCandidateEvents(receipt);
        if (!response.isEmpty()){
            System.out.println("operated successfully, you added a candidate : "+ new String(response.get(0).candidate));
        }else {
            System.out.println("event log not found , maybe the event didn't execute");
        }
    }

    public void voteForCandidate(String name) throws Exception{
        String contractAddress = loadVoteAddress();
        Vote vote =Vote.load(contractAddress,client,cryptoKeyPair);
        TransactionReceipt receipt = vote.voteCandidate(name.getBytes());
        List<Vote.VoteCandidateEventResponse> response = vote.getVoteCandidateEvents(receipt);
        if (!response.isEmpty()){
            System.out.println("operated successfully, you voted for : "+ new String(response.get(0).candidate));
        }else {
            System.out.println("event log not found , maybe the event didn't execute");
        }
    }

    public void appointCandidate()throws Exception{
        String contractAddress = loadVoteAddress();
        Vote vote=Vote.load(contractAddress,client,cryptoKeyPair);
        TransactionReceipt receipt = vote.appointCandidate();
        List<Vote.AppointCandidateEventResponse> response = vote.getAppointCandidateEvents(receipt);
        System.out.println("the appointee is : "+ new String(response.get(0).appointee));
    }
    public static void instruction(){
        System.out.println("instruction:");
        System.out.println("you should deploy it first : $ bash vote_run.sh deploy");
        System.out.println("1. to add someone into the list of candidates : $ bash vote_run.sh add name");
        System.out.println("2. to vote for someone: $ bash vote_run.sh vote name");
        System.out.println("3. to find out who is the appointee: $ bash appoint");
    }

    public static void main(String[] args) throws Exception {

//        voteClient.addCandidate("mike");
        if (args.length!=0){
            switch(args[0]){
                case "add" :{
                    if (args.length==2){
                        voteClient.addCandidate(args[1]);
                    }
                }break;
                case "vote" :{
                    if (args.length==2){
                        voteClient.voteForCandidate(args[1]);
                    }
                }break;
                case "appoint":{
                    if (args.length == 1) {
                        voteClient.appointCandidate();
                    }
                }break;
                case "deploy":{
                    if (args.length==1){
                        voteClient.deployVoteAddress();
                    }
                }break;
                default:{
                    System.out.println("wrong input!!!!!!!");
                    instruction();
                }
            }
        }
        System.exit(0);
    }
}
