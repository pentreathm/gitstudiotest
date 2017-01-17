package groovy

import com.vatit.blaze.dto.ExchangeRates
import com.vatit.wyvern.shared.dto.ConvertCurrencyDef
import com.vatit.wyvern.shared.dto.ConvertCurrencyListDef
import com.vatit.wyvern.shared.dto.convertCurrencyMultipleRequest

rates = (ExchangeRates)payload
request = new convertCurrencyMultipleRequest()
request.setCurrencyList(ConvertCurrencyListDef.create())

rates.items.each {
	cp = new ConvertCurrencyDef()
	cp.currencyFrom = it.fromCurrencyCode
	cp.currencyTo = it.toCurrencyCode
	cp.canUseSpot = true
	cp.requestDate = it.date
	request.getCurrencyList().addCurrencyPair cp
}

return request