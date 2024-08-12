package com.prajjawal.Trading_Platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.model.Asset;
import com.prajjawal.Trading_Platform.model.Coin;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.repository.AssetRepository;

@Service
public class AssetServiceImpl implements AssetService {
  @Autowired
  private AssetRepository assetRepository;

  @Override
  public Asset createAsset(User user, Coin coin, double quantity) {
    Asset asset = new Asset();
    asset.setUser(user);
    asset.setCoin(coin);
    asset.setQuantity(quantity);
    asset.setBuyPrice(coin.getCurrentPrice());
    return assetRepository.save(asset);
  }

  @Override
  public Asset getAssetById(Long assetId) throws Exception {
    return assetRepository.findById(assetId).orElseThrow(() -> new Exception("Asset is Not Found!"));
  }

  @Override
  public Asset getAssetByUserIdandId(Long userId, Long assetId) {
    return null;
  }

  @Override
  public List<Asset> getUsersAssets(Long userId) {
    return assetRepository.findByUserId(userId);
  }

  @Override
  public Asset updateAsset(Long assetId, double quantity) throws Exception {
    Asset oldAsset = getAssetById(assetId);
    oldAsset.setQuantity(quantity + oldAsset.getQuantity());
    assetRepository.save(oldAsset);
    return assetRepository.save(oldAsset);
  }

  @Override
  public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
    return assetRepository.findByUserIdAndCoinId(userId, coinId);
  }

  @Override
  public void deleteAsset(Long assetId) {
    assetRepository.deleteById(assetId);
  }

}
