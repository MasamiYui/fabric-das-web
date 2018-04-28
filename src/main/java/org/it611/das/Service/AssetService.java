package org.it611.das.Service;

import org.it611.das.vo.StudentIdCardAssetVO;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface AssetService {

    int InsertStudentIdCardAsset(StudentIdCardAssetVO vo) throws Exception;

}
