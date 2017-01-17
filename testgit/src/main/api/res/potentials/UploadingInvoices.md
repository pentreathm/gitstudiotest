###External Id's
These are used for identification from an external system; they are not strictly necessary, but must be used in the following situations:

* You are posting to _Blaze_, and want to update what you have posted at a later stage. _E.g._ You want to add invoices to an existing group; or you want to update existing invoices. **Note:** updates will only be allowed under Blaze workflow rules for freezing updates from external sources.
* You want to attach documents to invoice or groups.
* You want to read back invoices or groups you have posted, and want identify them.

In response to a _post_, you will receive the **Blaze Id's** associated to your external Id's. You may store these Id's and use them for identification, as with external Id's.

###Mandatory Fields
The following fields are needed for calculations:

* **Invoice Amount**
* **Country Code**
* **Invoice Date**
* **Expense Type**

Other fields are informational and will be used:

* By CSE's when identifying the invoices
* To enhance the accuracy of calculations
* To ease the claim process
* To ensure greater chance of positive claim results

###Expense Types
Expense types must all be mapped to internal _Blaze_ expense types, before they can be calculated for VAT potential. There are a few ways in which these can be resolved:

* Post the **dragonId** (Blaze internal expense type Id) with the expense type - this will achieve an immediate mapping; if you know this Id
* Post an **externalId** - this will need to be mapped by Blaze administrators, either in advance, or retro-actively. Either way, calculations will be resolved only after expense types are mapped.

If you choose to use external Id's, and these Id's are non-descriptive, please populate the **externalName** field, so that mappings can be determined.

###Suppliers
Supplier information is not needed for calculations, but is used during the claim process. Giving as musch information as possible here, will ease that process.   
###Location Map

###Connections