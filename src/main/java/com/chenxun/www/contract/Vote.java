package com.chenxun.www.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.DynamicBytes;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Vote extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50610bb8806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630201cbe2146100725780634c03df5a146100f3578063ac1289631461015c578063b13c744b146101ec578063bf328b2114610292575b600080fd5b34801561007e57600080fd5b506100d9600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506102fb565b604051808215151515815260200191505060405180910390f35b3480156100ff57600080fd5b5061015a600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061049b565b005b34801561016857600080fd5b506101716105fa565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101b1578082015181840152602081019050610196565b50505050905090810190601f1680156101de5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101f857600080fd5b50610217600480360381019080803590602001909291905050506108cf565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561025757808201518184015260208101905061023c565b50505050905090810190601f1680156102845780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561029e57600080fd5b506102f9600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061098a565b005b600080600080600092505b60008054905083101561048e5760008381548110151561032257fe5b906000526020600020019150818054600181600116156101000203166002900490508551141561048157600090505b8451811015610478578181815460018160011615610100020316600290048110151561037957fe5b8154600116156103985790600052602060002090602091828204019190065b9054901a7f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191685828151811015156103ee57fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614151561046b5760009350610493565b8080600101915050610351565b60019350610493565b8280600101935050610306565b600093505b505050919050565b6104a4816102fb565b15156105f75760008190806001815401808255809150509060018203906000526020600020016000909192909190915090805190602001906104e7929190610ae7565b505060006001826040518082805190602001908083835b60208310151561052357805182526020820191506020810190506020830392506104fe565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055507f48d9d32132a6ad82dc285f3f3b0ce03bc7e08b039cc3036d4e56e4fbe8f24c77816040518080602001828103825283818151815260200191508051906020019080838360005b838110156105bc5780820151818401526020810190506105a1565b50505050905090810190601f1680156105e95780820380516001836020036101000a031916815260200191505b509250505060405180910390a15b50565b6060600080600080805490501415156106295760008081548110151561061c57fe5b9060005260206000200191505b6001600080549050111561077557600190505b6000805490508110156107745760018260405180828054600181600116156101000203166002900480156106a75780601f106106855761010080835404028352918201916106a7565b820191906000526020600020905b815481529060010190602001808311610693575b505091505090815260200160405180910390205460016000838154811015156106cc57fe5b9060005260206000200160405180828054600181600116156101000203166002900480156107315780601f1061070f576101008083540402835291820191610731565b820191906000526020600020905b81548152906001019060200180831161071d575b505091505090815260200160405180910390205411156107675760008181548110151561075a57fe5b9060005260206000200191505b808060010191505061063c565b5b7f84468a23723971da86d09c3385f0ec021a8cc347de5ed9f30adc3fa77117dbf28260405180806020018281038252838181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156108205780601f106107f557610100808354040283529160200191610820565b820191906000526020600020905b81548152906001019060200180831161080357829003601f168201915b50509250505060405180910390a1818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108c35780601f10610898576101008083540402835291602001916108c3565b820191906000526020600020905b8154815290600101906020018083116108a657829003601f168201915b50505050509250505090565b6000818154811015156108de57fe5b906000526020600020016000915090508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109825780601f1061095757610100808354040283529160200191610982565b820191906000526020600020905b81548152906001019060200180831161096557829003601f168201915b505050505081565b610993816102fb565b15610ae4576001816040518082805190602001908083835b6020831015156109d057805182526020820191506020810190506020830392506109ab565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600081548092919060010191905055507f438ccc295f6d1ca8852b619b43984b8f608498253a8f58e8e42f0add4f5d1897813360405180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610aa8578082015181840152602081019050610a8d565b50505050905090810190601f168015610ad55780820380516001836020036101000a031916815260200191505b50935050505060405180910390a15b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b2857805160ff1916838001178555610b56565b82800160010185558215610b56579182015b82811115610b55578251825591602001919060010190610b3a565b5b509050610b639190610b67565b5090565b610b8991905b80821115610b85576000816000905550600101610b6d565b5090565b905600a165627a7a72305820655a4dd23c0dba1c3b086ac21b88e4fe0502d8ad5a78f3ebaa291c12f11273d70029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50610bb8806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635a017a0e1461007257806392cc2ad514610102578063addc2f981461016b578063c57ad4a0146101d4578063c7ba32e514610255575b600080fd5b34801561007e57600080fd5b506100876102fb565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100c75780820151818401526020810190506100ac565b50505050905090810190601f1680156100f45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561010e57600080fd5b50610169600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506105d0565b005b34801561017757600080fd5b506101d2600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061072d565b005b3480156101e057600080fd5b5061023b600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061088c565b604051808215151515815260200191505060405180910390f35b34801561026157600080fd5b5061028060048036038101908080359060200190929190505050610a2c565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102c05780820151818401526020810190506102a5565b50505050905090810190601f1680156102ed5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60606000806000808054905014151561032a5760008081548110151561031d57fe5b9060005260206000200191505b6001600080549050111561047657600190505b6000805490508110156104755760018260405180828054600181600116156101000203166002900480156103a85780601f106103865761010080835404028352918201916103a8565b820191906000526020600020905b815481529060010190602001808311610394575b505091505090815260200160405180910390205460016000838154811015156103cd57fe5b9060005260206000200160405180828054600181600116156101000203166002900480156104325780601f10610410576101008083540402835291820191610432565b820191906000526020600020905b81548152906001019060200180831161041e575b505091505090815260200160405180910390205411156104685760008181548110151561045b57fe5b9060005260206000200191505b808060010191505061033d565b5b7f270b9c9fbbb4ec8ad7442d1761085b6321ddf3081e922dfde0b40ccd99fa5cc58260405180806020018281038252838181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156105215780601f106104f657610100808354040283529160200191610521565b820191906000526020600020905b81548152906001019060200180831161050457829003601f168201915b50509250505060405180910390a1818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105c45780601f10610599576101008083540402835291602001916105c4565b820191906000526020600020905b8154815290600101906020018083116105a757829003601f168201915b50505050509250505090565b6105d98161088c565b1561072a576001816040518082805190602001908083835b60208310151561061657805182526020820191506020810190506020830392506105f1565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600081548092919060010191905055507f1957c513e208c9563da9a9d302287e150eb5a93fd3351a4d9799c5d532350a4b813360405180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156106ee5780820151818401526020810190506106d3565b50505050905090810190601f16801561071b5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a15b50565b6107368161088c565b1515610889576000819080600181540180825580915050906001820390600052602060002001600090919290919091509080519060200190610779929190610ae7565b505060006001826040518082805190602001908083835b6020831015156107b55780518252602082019150602081019050602083039250610790565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055507faf1377875a25a0823b541cd240c70e58f0f05309d0923f876f73d9cafa081573816040518080602001828103825283818151815260200191508051906020019080838360005b8381101561084e578082015181840152602081019050610833565b50505050905090810190601f16801561087b5780820380516001836020036101000a031916815260200191505b509250505060405180910390a15b50565b600080600080600092505b600080549050831015610a1f576000838154811015156108b357fe5b9060005260206000200191508180546001816001161561010002031660029004905085511415610a1257600090505b8451811015610a09578181815460018160011615610100020316600290048110151561090a57fe5b8154600116156109295790600052602060002090602091828204019190065b9054901a7f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916858281518110151561097f57fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff19161415156109fc5760009350610a24565b80806001019150506108e2565b60019350610a24565b8280600101935050610897565b600093505b505050919050565b600081815481101515610a3b57fe5b906000526020600020016000915090508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610adf5780601f10610ab457610100808354040283529160200191610adf565b820191906000526020600020905b815481529060010190602001808311610ac257829003601f168201915b505050505081565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b2857805160ff1916838001178555610b56565b82800160010185558215610b56579182015b82811115610b55578251825591602001919060010190610b3a565b5b509050610b639190610b67565b5090565b610b8991905b80821115610b85576000816000905550600101610b6d565b5090565b905600a165627a7a723058203fdd28ca255d88910c639dfbf643885e0e7f99f749da2dd84b6ca4c32c53f5f10029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":true,\"inputs\":[{\"name\":\"candidateName\",\"type\":\"bytes\"}],\"name\":\"checkCandidate\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"candidateName\",\"type\":\"bytes\"}],\"name\":\"addCandidate\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"appointCandidate\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"candidateList\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"candidateName\",\"type\":\"bytes\"}],\"name\":\"voteCandidate\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"candidate\",\"type\":\"bytes\"}],\"name\":\"AddCandidate\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"candidate\",\"type\":\"bytes\"},{\"indexed\":false,\"name\":\"voter\",\"type\":\"address\"}],\"name\":\"VoteCandidate\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"appointee\",\"type\":\"bytes\"}],\"name\":\"AppointCandidate\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_CHECKCANDIDATE = "checkCandidate";

    public static final String FUNC_ADDCANDIDATE = "addCandidate";

    public static final String FUNC_APPOINTCANDIDATE = "appointCandidate";

    public static final String FUNC_CANDIDATELIST = "candidateList";

    public static final String FUNC_VOTECANDIDATE = "voteCandidate";

    public static final Event ADDCANDIDATE_EVENT = new Event("AddCandidate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event VOTECANDIDATE_EVENT = new Event("VoteCandidate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event APPOINTCANDIDATE_EVENT = new Event("AppointCandidate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
    ;

    protected Vote(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public Boolean checkCandidate(byte[] candidateName) throws ContractException {
        final Function function = new Function(FUNC_CHECKCANDIDATE, 
                Arrays.<Type>asList(new DynamicBytes(candidateName)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public TransactionReceipt addCandidate(byte[] candidateName) {
        final Function function = new Function(
                FUNC_ADDCANDIDATE, 
                Arrays.<Type>asList(new DynamicBytes(candidateName)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] addCandidate(byte[] candidateName, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADDCANDIDATE, 
                Arrays.<Type>asList(new DynamicBytes(candidateName)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAddCandidate(byte[] candidateName) {
        final Function function = new Function(
                FUNC_ADDCANDIDATE, 
                Arrays.<Type>asList(new DynamicBytes(candidateName)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<byte[]> getAddCandidateInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADDCANDIDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public TransactionReceipt appointCandidate() {
        final Function function = new Function(
                FUNC_APPOINTCANDIDATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] appointCandidate(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_APPOINTCANDIDATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAppointCandidate() {
        final Function function = new Function(
                FUNC_APPOINTCANDIDATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<byte[]> getAppointCandidateOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_APPOINTCANDIDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public byte[] candidateList(BigInteger param0) throws ContractException {
        final Function function = new Function(FUNC_CANDIDATELIST, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public TransactionReceipt voteCandidate(byte[] candidateName) {
        final Function function = new Function(
                FUNC_VOTECANDIDATE, 
                Arrays.<Type>asList(new DynamicBytes(candidateName)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] voteCandidate(byte[] candidateName, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_VOTECANDIDATE, 
                Arrays.<Type>asList(new DynamicBytes(candidateName)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForVoteCandidate(byte[] candidateName) {
        final Function function = new Function(
                FUNC_VOTECANDIDATE, 
                Arrays.<Type>asList(new DynamicBytes(candidateName)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<byte[]> getVoteCandidateInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_VOTECANDIDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public List<AddCandidateEventResponse> getAddCandidateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ADDCANDIDATE_EVENT, transactionReceipt);
        ArrayList<AddCandidateEventResponse> responses = new ArrayList<AddCandidateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AddCandidateEventResponse typedResponse = new AddCandidateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.candidate = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeAddCandidateEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(ADDCANDIDATE_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeAddCandidateEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(ADDCANDIDATE_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<VoteCandidateEventResponse> getVoteCandidateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(VOTECANDIDATE_EVENT, transactionReceipt);
        ArrayList<VoteCandidateEventResponse> responses = new ArrayList<VoteCandidateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            VoteCandidateEventResponse typedResponse = new VoteCandidateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.candidate = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.voter = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeVoteCandidateEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(VOTECANDIDATE_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeVoteCandidateEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(VOTECANDIDATE_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<AppointCandidateEventResponse> getAppointCandidateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(APPOINTCANDIDATE_EVENT, transactionReceipt);
        ArrayList<AppointCandidateEventResponse> responses = new ArrayList<AppointCandidateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AppointCandidateEventResponse typedResponse = new AppointCandidateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.appointee = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeAppointCandidateEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(APPOINTCANDIDATE_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeAppointCandidateEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(APPOINTCANDIDATE_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static Vote load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Vote(contractAddress, client, credential);
    }

    public static Vote deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Vote.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class AddCandidateEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] candidate;
    }

    public static class VoteCandidateEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] candidate;

        public String voter;
    }

    public static class AppointCandidateEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] appointee;
    }
}
