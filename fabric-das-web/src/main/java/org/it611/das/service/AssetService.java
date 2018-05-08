package org.it611.das.service;

import org.it611.das.vo.StudentIdCardAssetVO;

import java.util.Map;

public interface AssetService {

    Map<String, Object> InsertStudentIdCardAsset(StudentIdCardAssetVO vo) throws Exception;

}
