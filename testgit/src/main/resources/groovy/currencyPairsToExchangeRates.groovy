package groovy

import com.vatit.blaze.dto.ExchangeRates
import com.vatit.wyvern.shared.dto.convertCurrencyMultipleResponse
import org.mule.api.MuleMessage

MuleMessage msg = message

inputRates = (ExchangeRates)msg.payload
def fromDragon = (convertCurrencyMultipleResponse)msg.getInvocationProperty('dragon.exchange.rates')

inputRates.items.each {
	def inputRate = it
	dragonRate = fromDragon.currencyList.currencyPairList.find { it.currencyFrom == inputRate.fromCurrencyCode && it.currencyTo == inputRate.toCurrencyCode }
	inputRate.setRate dragonRate.rate
}

return inputRates