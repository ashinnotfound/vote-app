pragma solidity >=0.4.24 <0.6.11;

import "./Table.sol";

contract Vote{

    //event
    event AddCandidate(bytes candidate);
    event VoteCandidate(bytes candidate, address voter);
    event AppointCandidate(bytes appointee);

    bytes[] public candidateList;
    mapping (bytes=>uint) votesCount;

    //add a person to the list of candidates
    function addCandidate(bytes candidateName) public{
        if(!checkCandidate(candidateName)){
            //if the person isn't in the list,add it.
            candidateList.push(candidateName);
            votesCount[candidateName]=0;
            emit AddCandidate(candidateName);
        }
    }

    //vote for a candidate
    function voteCandidate(bytes candidateName) public{
        if(checkCandidate(candidateName)){
            //if the person is in the list,votesCount adds.
            votesCount[candidateName]++;
            emit VoteCandidate(candidateName,msg.sender);
        }
    }

    //check whether the person you want to operate is in the list
    function checkCandidate(bytes candidateName) public view returns(bool){
        for(uint i=0;i<candidateList.length;i++){
            bytes storage a=candidateList[i];
            if(candidateName.length==a.length){
                for(uint j=0;j<candidateName.length;j++){
                    if(candidateName[j]!=a[j]){
                        return false;
                    }
                }return true;
            }
        }
        return false;
    }

    //find the one who have gotten the most votes
    function appointCandidate() public returns(bytes){
        bytes storage appointee;
        if(candidateList.length!=0)appointee=candidateList[0];
        if(candidateList.length>1){
            for(uint i=1;i<candidateList.length;i++){
                if(votesCount[candidateList[i]]>votesCount[appointee]){
                    appointee=candidateList[i];
                }
            }
        }
        emit AppointCandidate(appointee);
        return appointee;
    }
}