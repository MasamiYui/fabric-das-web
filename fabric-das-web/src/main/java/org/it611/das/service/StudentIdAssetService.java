package org.it611.das.service;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.vo.StudentIdCardAssetVO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StudentIdAssetService {

    Map<String, Object> InsertStudentIdCardAsset(StudentIdCardAssetVO vo) throws Exception;

    HashMap<String, Object> selectStudentIdCardAssetById(String id) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException;

    HashMap<String, Object> selectAssetList(int currentPage,int numberOfPages, String title,String id,String txid);

    List<HashMap> studentDetail(String id);


}
