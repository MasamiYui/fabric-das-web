package org.it611.das.service;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public interface DrivingLicenceAssetService {

    JSONObject drivingLicenceList(int currentPage, int numberOfPages, String id, String state);

    public JSONObject selectDrivingLicenceDetailById(String id) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    public JSONObject CheckDrivingLicenceAndChangeState(String id, String state) throws Exception;
}
