import com.vatit.blaze.dto.ExchangeRate

List<ExchangeRate> ratesList = message.payload.getItems()
List<ExchangeRate> originalList = message.getInvocationProperty('exchange.rates.list')

for (ExchangeRate rate : originalList){
	ExchangeRate convertedRate = ratesList.find { it.getFromCurrencyCode() == rate.getFromCurrencyCode() && it.getToCurrencyCode() == rate.getToCurrencyCode() }
	rate.setRate(convertedRate.getRate())
}

payload