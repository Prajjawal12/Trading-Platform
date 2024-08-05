package com.prajjawal.Trading_Platform.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crypto_currency")
public class Coin {

  @Id
  private String id;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("name")
  private String name;

  @JsonProperty("image")
  private String image;

  @JsonProperty("current_price")
  private double currentPrice;

  @JsonProperty("market_cap")
  private long marketCap;

  @JsonProperty("market_cap_rank")
  private int marketCapRank;

  @JsonProperty("fully_diluted_valuation")
  private BigDecimal fullyDilutedValuation;

  @JsonProperty("total_volume")
  private long totalVolume;

  @JsonProperty("high_24h")
  private double high24h;

  @JsonProperty("low_24h")
  private double low24h;

  @JsonProperty("price_change_24h")
  private double priceChange24h;

  @JsonProperty("price_change_percentage_24h")
  private double priceChangePercentage24h;

  @JsonProperty("market_cap_change_24h")
  private long marketCapChange24h;

  @JsonProperty("market_cap_change_percentage_24h")
  private long marketCapChangePercentage24h;

  @JsonProperty("circulating_supply")
  private BigDecimal circulatingSupply;

  @JsonProperty("total_supply")
  private long totalSupply;

  @JsonProperty("max_supply")
  private BigDecimal maxSupply;

  @JsonProperty("ath")
  private BigDecimal ath;

  @JsonProperty("ath_change_percentage")
  private BigDecimal athChangePercentage;

  @JsonProperty("ath_date")
  private LocalDateTime athDate;

  @JsonProperty("atl")
  private BigDecimal atl;

  @JsonProperty("roi")
  private BigDecimal roi;

  @JsonProperty("atl_date")
  private LocalDateTime atlDate;

  @JsonProperty("last_updated")
  private LocalDateTime lastUpdated;

}