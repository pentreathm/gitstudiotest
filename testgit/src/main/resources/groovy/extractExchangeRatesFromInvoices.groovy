package main.resources.groovy

import com.vatit.blaze.dto.ExchangeRate
import com.vatit.blaze.dto.Invoice

for (Invoice invoice : payload) {
	invoice.exchangeRate = new ExchangeRate()
	invoice.exchangeRate.setFromCurrencyCode(invoice.currencyCode)
	invoice.exchangeRate.setDate(invoice.date)
	invoice.exchangeRate.setId(invoice.id)
}

payload