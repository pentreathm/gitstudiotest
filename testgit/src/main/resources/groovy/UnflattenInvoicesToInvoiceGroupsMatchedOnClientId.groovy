// Unflatten a list of invoices to a list of invoice groups - match on connection client id
// 1. iterate invoices 
// 2. add new group if not exists
// 3. add invoice to group
import com.vatit.blaze.dto.InvoiceGroup
import com.vatit.blaze.dto.Invoice

groups = new ArrayList<InvoiceGroup>()

for (Invoice invoice : payload) {
	group = groups.find { it.connection.client.id == invoice.connection.client.id }
	if (group == null) {
	  group = new InvoiceGroup()
	  group.connection = invoice.connection
	  groups.add group
	 }
	group.invoices.add invoice
}

groups

