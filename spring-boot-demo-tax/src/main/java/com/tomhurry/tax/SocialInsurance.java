package com.tomhurry.tax;

/**
 * 社保
 *
 * @author TaoZhi
 * @date 2021/10/31 21:33
 * @since 1.0.0
 */
public class SocialInsurance {
    /**
     * 默认养老保险
     */
    public static final double DEFAULT_PENSION_RATIO = 0.08;

    /**
     * 默认医疗保险
     */
    public static final double DEFAULT_MEDICAL_RATIO = 0.02;

    /**
     * 默认失业保险
     */
    public static final double DEFAULT_UNEMPLOYMENT_RATIO = 0.005;

    /**
     * 社保基数
     */
    private final Integer socialInsuranceBase;

    private final Integer housingFundBase;

    private final InsuranceFundRatio insuranceFundRatio;

    public SocialInsurance(Integer socialInsuranceBase, Integer housingFundBase, InsuranceFundRatio insuranceFundRatio) {
        this.socialInsuranceBase = socialInsuranceBase;
        this.housingFundBase = housingFundBase;
        this.insuranceFundRatio = insuranceFundRatio;
    }


}
