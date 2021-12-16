package com.tomhurry.tax;

import lombok.Data;

/**
 * 五险一金缴纳比例
 *
 * @author TaoZhi
 * @date 2021/10/31 21:58
 * @since 1.0.0
 */
@Data
public class InsuranceFundRatio {

    /**
     * 养老保险个人缴纳比例
     */
    private double personalPensionRatio;
    /**
     * 医疗保险个人缴纳比例
     */
    private double personalMedicalRatio;
    /**
     * 失业保险个人缴纳比例
     */
    private double personalUnemploymentRatio;
    /**
     * 住房公积金个人缴纳比例
     */
    private double personalHousingFundRatio;
    /**
     * 养老保险企业缴纳比例
     */
    private double companyPensionRatio;
    /**
     * 医疗保险企业缴纳比例
     */
    private double companyMedicalRatio;
    /**
     * 失业保险企业缴纳比例
     */
    private double companyUnemploymentRatio;
    /**
     * 工伤保险企业缴纳比例
     */
    private double companyInjuryRatio;
    /**
     * 生育保险企业缴纳比例
     */
    private double companyMaternityRatio;
    /**
     * 住房公积金企业缴纳比例
     */
    private double companyHousingFundRatio;
}
