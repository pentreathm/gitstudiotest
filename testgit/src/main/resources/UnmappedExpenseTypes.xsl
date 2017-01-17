<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/list">
    <html>
      <body>
        <h2><xsl:value-of select="list/string[3]"/></h2>
        <table border="1">
          <tr>
            <th style="text-align:left">External Expense Type Id</th>
            <th style="text-align:left">External Expense Type Name</th>
          </tr>
          <xsl:for-each select="list">
            <tr>
              <td><xsl:value-of select="string[2]"/></td>
              <td><xsl:value-of select="string[1]"/></td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
