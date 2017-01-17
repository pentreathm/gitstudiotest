package com.vatit.blaze.esb.concur.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class V2_ExpenseReport {
	private List<V2_ExpenseReportEntry> expenseEntriesList;

	public List<V2_ExpenseReportEntry> getExpenseEntriesList() {
		return expenseEntriesList;
	}

	public void setExpenseEntriesList(List<V2_ExpenseReportEntry> expenseEntriesList) {
		this.expenseEntriesList = expenseEntriesList;
	}

}
