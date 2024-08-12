package com.prajjawal.Trading_Platform.service;

import java.util.List;

import com.prajjawal.Trading_Platform.model.Asset;
import com.prajjawal.Trading_Platform.model.Coin;
import com.prajjawal.Trading_Platform.model.User;

public interface AssetService {
  Asset createAsset(User user, Coin coin, double quantity);

  Asset getAssetById(Long assetId) throws Exception;

  Asset getAssetByUserIdandId(Long userId, Long assetId) throws Exception;

  List<Asset> getUsersAssets(Long userId);

  Asset updateAsset(Long assetId, double quantity) throws Exception;

  Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

  void deleteAsset(Long assetId);
}
