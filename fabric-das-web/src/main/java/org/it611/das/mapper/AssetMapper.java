package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.BaseAsset;
import org.it611.das.domain.StudentIdCardAsset;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface AssetMapper {

    int insertAssetBase(BaseAsset baseAsset);

    int insertStudentIdCardAsset(StudentIdCardAsset studentIdCardAsset);

    int insertAssetUser(List assetUser);

    int insertAssetFiles(List assetFiles);

    HashMap selectStudentIdCardAssetDetailById(@Param("id")String assetId);



}
