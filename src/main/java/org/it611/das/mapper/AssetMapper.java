package org.it611.das.mapper;

import org.it611.das.domain.AssetFiles;
import org.it611.das.domain.BaseAsset;
import org.it611.das.domain.StudentIdCardAsset;
import org.it611.das.domain.AssetUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AssetMapper {

    int insertAssetBase(BaseAsset baseAsset);

    int insertStudentIdCardAsset(StudentIdCardAsset studentIdCardAsset);

    int insertAssetUser(List assetUser);

    int insertAssetFiles(List assetFiles);
}
