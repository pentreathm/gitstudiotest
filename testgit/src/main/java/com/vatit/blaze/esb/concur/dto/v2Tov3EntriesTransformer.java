package com.vatit.blaze.esb.concur.dto;

import com.vatit.blaze.dto.ConcurCustomField;
import com.vatit.blaze.dto.ConcurExpenseEntry;
import com.vatit.blaze.dto.ConcurExpenseEntryBase;
import com.vatit.blaze.dto.ConcurExpenseReport;
import com.vatit.blaze.esb.exception.SimpleTransformerException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class v2Tov3EntriesTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		V2_ExpenseReport v2ExpenseReport = (V2_ExpenseReport)message.getPayload();
		ConcurExpenseReport expenseReport = message.getInvocationProperty("blaze.concur.report");

		for (V2_ExpenseReportEntry v2Entry : v2ExpenseReport.getExpenseEntriesList()) {
			ConcurExpenseEntry v3Entry = new ConcurExpenseEntry();

			// Copy same fields from base class
			List<Field> fields = Arrays.asList(ConcurExpenseEntryBase.class.getDeclaredFields());
			for (Field field : fields)
				try {
					PropertyUtils.setSimpleProperty(v3Entry, field.getName(), PropertyUtils.getSimpleProperty(v2Entry, field.getName()));
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new SimpleTransformerException(this, String.format("Unable to extract field values for %s.", ConcurExpenseEntryBase.class.getSimpleName()), e);
				}

			// Copy different fields
			v3Entry.setConcurId(v2Entry.getReportEntryId());
			v3Entry.setExpenseTypeCode(v2Entry.getExpenseTypeId());
			v3Entry.setSpendCategoryName(v2Entry.getSpendCategory());
			v3Entry.setHasImage(v2Entry.getEntryImageId() != null);
			v3Entry.setHasItemizations(v2Entry.getIsItemized());
			// Not sure the accuracy of this
			if (v2Entry.getIsCreditCardCharge())
				v3Entry.setPaymentTypeName("Credit Card");
			v3Entry.setReceiptReceived(v2Entry.getReportEntryReceiptReceived());
			v3Entry.setTaxReceiptType(v2Entry.getReportEntryReceiptType());
			v3Entry.setElectronicReceiptId(v2Entry.geteReceiptId());
			v3Entry.setVendorName(v2Entry.getReportEntryVendorName());
			v3Entry.setOrgUnit1(strToOrgUnit(v2Entry.getOrgUnit1()));
			v3Entry.setOrgUnit2(strToOrgUnit(v2Entry.getOrgUnit2()));
			v3Entry.setOrgUnit3(strToOrgUnit(v2Entry.getOrgUnit3()));
			v3Entry.setOrgUnit4(strToOrgUnit(v2Entry.getOrgUnit4()));
			v3Entry.setOrgUnit5(strToOrgUnit(v2Entry.getOrgUnit5()));
			v3Entry.setOrgUnit6(strToOrgUnit(v2Entry.getOrgUnit6()));

			expenseReport.getEntries().add(v3Entry);

			// skipped
			//String transactionCurrencyName;
			//Integer commentCount;
			//String cardTransaction;
			//Boolean expensePay;
		}

		return expenseReport;
	}

	private ConcurCustomField strToOrgUnit(String val) {
		return StringUtils.isEmpty(val) ? null : new ConcurCustomField(val);
	}

}
