package org.it611.das.service;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public interface MusicAssetService {

    JSONObject musicAssetList(int currentPage, int numberOfPages, String title,String state);

    public JSONObject musicAssetDetail(String id) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException;

    public JSONObject CheckMusicAssetAndChangeState(String id, String state) throws Exception;


}
