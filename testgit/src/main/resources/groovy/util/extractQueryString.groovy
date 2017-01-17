str = message.getInvocationProperty('http.query.string')
message.setProperty('blaze.queryString', str == null || str == '' ? '' : "?$str")
payload

