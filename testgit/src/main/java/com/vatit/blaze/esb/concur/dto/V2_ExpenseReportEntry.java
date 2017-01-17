package com.vatit.blaze.esb.concur.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vatit.blaze.dto.ConcurExpenseEntryBase;
import com.vatit.blaze.dto.base.CustomBooleanDeserializer;

@JsonIgnoreProperties(ignoreUnknown=true)
public class V2_ExpenseReportEntry extends ConcurExpenseEntryBase {

	@JsonProperty("ReportEntryID")
	private String reportEntryId;
	@JsonProperty("ExpenseTypeID")
	private String expenseTypeId;
	private String spendCategory;
	private String paymentTypeCode;
	private String transactionCurrencyName;
	private String businessPurpose;
	@JsonProperty("EntryImageID")
	private String entryImageId;
	private Integer commentCount;
	@JsonDeserialize(using = CustomBooleanDeserializer.class)
	private Boolean isItemized;
	@JsonDeserialize(using = CustomBooleanDeserializer.class)
	private Boolean hasAllocation;
	@JsonDeserialize(using = CustomBooleanDeserializer.class)
	private Boolean isCreditCardCharge;
	@JsonDeserialize(using = CustomBooleanDeserializer.class)
	private Boolean receiptRequired;
	@JsonDeserialize(using = CustomBooleanDeserializer.class)
	private Boolean imageRequired;
	@JsonProperty("E-ReceiptID")
	private String eReceiptId;
	@JsonProperty("Allocation-Url")
	private String allocationUrl;
	private String reportEntryVendorName;
	@JsonDeserialize(using = CustomBooleanDeserializer.class)
	private Boolean reportEntryReceiptReceived;
	private String reportEntryReceiptType;
	private String cardTransaction;
	private Double billingAmount;
	@JsonDeserialize(using = CustomBooleanDeserializer.class)
	private Boolean expensePay;
	private String orgUnit1;
	private String orgUnit2;
	private String orgUnit3;
	private String orgUnit4;
	private String orgUnit5;
	private String orgUnit6;

	public String getReportEntryId() {
		return reportEntryId;
	}

	public void setReportEntryId(String reportEntryId) {
		this.reportEntryId = reportEntryId;
	}

	public String getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(String expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getSpendCategory() {
		return spendCategory;
	}

	public void setSpendCategory(String spendCategory) {
		this.spendCategory = spendCategory;
	}

	public String getPaymentTypeCode() {
		return paymentTypeCode;
	}

	public void setPaymentTypeCode(String paymentTypeCode) {
		this.paymentTypeCode = paymentTypeCode;
	}

	public String getTransactionCurrencyName() {
		return transactionCurrencyName;
	}

	public void setTransactionCurrencyName(String transactionCurrencyName) {
		this.transactionCurrencyName = transactionCurrencyName;
	}

	public String getBusinessPurpose() {
		return businessPurpose;
	}

	public void setBusinessPurpose(String businessPurpose) {
		this.businessPurpose = businessPurpose;
	}

	public String getEntryImageId() {
		return entryImageId;
	}

	public void setEntryImageId(String entryImageId) {
		this.entryImageId = entryImageId;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Boolean getIsItemized() {
		return isItemized;
	}

	public void setIsItemized(Boolean isItemized) {
		this.isItemized = isItemized;
	}

	public Boolean getHasAllocation() {
		return hasAllocation;
	}

	public void setHasAllocation(Boolean hasAllocation) {
		this.hasAllocation = hasAllocation;
	}

	public Boolean getIsCreditCardCharge() {
		return isCreditCardCharge;
	}

	public void setIsCreditCardCharge(Boolean isCreditCardCharge) {
		this.isCreditCardCharge = isCreditCardCharge;
	}

	public Boolean getReceiptRequired() {
		return receiptRequired;
	}

	public void setReceiptRequired(Boolean receiptRequired) {
		this.receiptRequired = receiptRequired;
	}

	public Boolean getImageRequired() {
		return imageRequired;
	}

	public void setImageRequired(Boolean imageRequired) {
		this.imageRequired = imageRequired;
	}

	public String geteReceiptId() {
		return eReceiptId;
	}

	public void seteReceiptId(String eReceiptId) {
		this.eReceiptId = eReceiptId;
	}

	public String getAllocationUrl() {
		return allocationUrl;
	}

	public void setAllocationUrl(String allocationUrl) {
		this.allocationUrl = allocationUrl;
	}

	public String getReportEntryVendorName() {
		return reportEntryVendorName;
	}

	public void setReportEntryVendorName(String reportEntryVendorName) {
		this.reportEntryVendorName = reportEntryVendorName;
	}

	public Boolean getReportEntryReceiptReceived() {
		return reportEntryReceiptReceived;
	}

	public void setReportEntryReceiptReceived(Boolean reportEntryReceiptReceived) {
		this.reportEntryReceiptReceived = reportEntryReceiptReceived;
	}

	public String getReportEntryReceiptType() {
		return reportEntryReceiptType;
	}

	public void setReportEntryReceiptType(String reportEntryReceiptType) {
		this.reportEntryReceiptType = reportEntryReceiptType;
	}

	public String getCardTransaction() {
		return cardTransaction;
	}

	public void setCardTransaction(String cardTransaction) {
		this.cardTransaction = cardTransaction;
	}

	public Double getBillingAmount() {
		return billingAmount;
	}

	public void setBillingAmount(Double billingAmount) {
		this.billingAmount = billingAmount;
	}

	public Boolean getExpensePay() {
		return expensePay;
	}

	public void setExpensePay(Boolean expensePay) {
		this.expensePay = expensePay;
	}

	public String getOrgUnit1() {
		return orgUnit1;
	}

	public void setOrgUnit1(String orgUnit1) {
		this.orgUnit1 = orgUnit1;
	}

	public String getOrgUnit2() {
		return orgUnit2;
	}

	public void setOrgUnit2(String orgUnit2) {
		this.orgUnit2 = orgUnit2;
	}

	public String getOrgUnit3() {
		return orgUnit3;
	}

	public void setOrgUnit3(String orgUnit3) {
		this.orgUnit3 = orgUnit3;
	}

	public String getOrgUnit4() {
		return orgUnit4;
	}

	public void setOrgUnit4(String orgUnit4) {
		this.orgUnit4 = orgUnit4;
	}

	public String getOrgUnit5() {
		return orgUnit5;
	}

	public void setOrgUnit5(String orgUnit5) {
		this.orgUnit5 = orgUnit5;
	}

	public String getOrgUnit6() {
		return orgUnit6;
	}

	public void setOrgUnit6(String orgUnit6) {
		this.orgUnit6 = orgUnit6;
	}


}
