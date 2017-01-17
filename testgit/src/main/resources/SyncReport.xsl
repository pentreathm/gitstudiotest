<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:attribute-set name="generalCell">
		<xsl:attribute name="style">
			<xsl:attribute name="paddingAttribute">
				<xsl:text>padding: 5px; </xsl:text>
			</xsl:attribute>
			<xsl:attribute name="colourAttribute">
				<xsl:text>color:</xsl:text>
				<xsl:choose>
					<xsl:when test="connection/isActive = 'false'">#D3D3D3; </xsl:when>
					<xsl:otherwise></xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<xsl:attribute name="textAlignAttribute">
				<xsl:text>text-align: left; </xsl:text>
			</xsl:attribute>
			<xsl:attribute name="fontStyleAttribute">
				<xsl:text>font-style:</xsl:text>
				<xsl:choose>
					<xsl:when test="connection/isActive = 'false'">italic; </xsl:when>
					<xsl:otherwise>normal; </xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="clientName">
		<xsl:attribute name="style">
			<xsl:attribute name="paddingAttribute">
				<xsl:text>padding: 5px; </xsl:text>
			</xsl:attribute>
			<xsl:attribute name="colourAttribute">
				<xsl:text>color:</xsl:text>
				<xsl:choose>
					<xsl:when test="connection/isActive = 'false'">#D3D3D3; </xsl:when>
					<xsl:otherwise>black; </xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<xsl:attribute name="textAlignAttribute">
				<xsl:text>text-align: left; </xsl:text>
			</xsl:attribute>
			<xsl:attribute name="fontStyleAttribute">
				<xsl:text>font-style:</xsl:text>
				<xsl:choose>
					<xsl:when test="connection/isActive = 'false'">italic; </xsl:when>
					<xsl:otherwise>normal; </xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			<xsl:attribute name="fontWeightAttribute">
				<xsl:text>font-weight:</xsl:text>
				<xsl:choose>
					<xsl:when test="connection/isActive = 'false'">normal; </xsl:when>
					<xsl:otherwise>bold; </xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
		</xsl:attribute>
	</xsl:attribute-set>
  <xsl:template match="/">
    <html>
      <body style="font-family: Verdana">
        <h2>Sync Report</h2>
        <table style="font-size: 12; border-style: solid; border-width: 1px; border-color: lightgrey; text-align: right;">
          <thead style="color: white; background-color: MidnightBlue">
            <tr>
              <th style="text-align: left; padding-left: 5px">Client</th>
              <th style="padding: 5px">Groups</th>
              <th style="padding: 5px">Invoices</th>
              <th style="padding: 5px">Bad Invoices</th>
              <th style="padding: 5px">Calculations</th>
              <th style="padding: 5px">Bad Calculations</th>
              <th style="padding: 5px">Expense Images</th>
              <th style="padding: 5px">Bad Expense Images</th>
              <th style="padding: 5px">Report Images Polled</th>
              <th style="padding: 5px">Report Images Found</th>
              <th style="text-align: left; padding-left: 5px">Errors</th>
            </tr>
          </thead>
          <tbody>
            <xsl:for-each select="/syncReport/connection">
              <tr>
                <xsl:attribute name="style">
                  <xsl:if test="position() mod 2 = 0">
                      <xsl:value-of select="'background: #edf9fc'" />
                  </xsl:if>
                </xsl:attribute>
								<td xsl:use-attribute-sets="clientName">
                  <xsl:value-of select="connection/client/ClientName"/>
                </td>
                <td style="padding: 5px">
                  <xsl:if test="reportsRetrieved > 0">
                    <xsl:value-of select="reportsRetrieved"/>
                  </xsl:if>
                </td>
                <td style="padding: 5px">
                  <xsl:if test="entriesRetrieved > 0">
                    <xsl:value-of select="entriesRetrieved"/>
                  </xsl:if>
                </td>
                <td style="padding: 5px; color: red">
                  <xsl:if test="faultyInvoices > 0">
                    <xsl:value-of select="faultyInvoices"/>
                  </xsl:if>
                </td>
                <td style="padding: 5px">
                  <xsl:if test="calculationsRetrieved > 0">
                    <xsl:value-of select="calculationsRetrieved"/>
                  </xsl:if>
                </td>
                <td style="padding: 5px; color: red">
                  <xsl:if test="faultyCalculations > 0">
                    <xsl:value-of select="faultyCalculations"/>
                  </xsl:if>
                </td>
                <td style="padding: 5px">
                  <xsl:if test="imagesRetrieved > 0">
                    <xsl:value-of select="imagesRetrieved"/>
                  </xsl:if>
                </td>
                <td style="padding: 5px; color: red">
                  <xsl:if test="faultyImages > 0">
                    <xsl:value-of select="faultyImages"/>
                  </xsl:if>
                </td>
	              <td style="padding: 5px">
		              <xsl:if test="groupImagesPolled > 0">
			              <xsl:value-of select="groupImagesPolled"/>
		              </xsl:if>
	              </td>
	              <td style="padding: 5px">
		              <xsl:if test="groupImagesFound > 0">
			              <xsl:value-of select="groupImagesFound"/>
		              </xsl:if>
	              </td>
								<td xsl:use-attribute-sets="generalCell">
									<xsl:choose>
										<xsl:when test="connection/isActive = 'false'">
											<div>Inactive Connection</div>
										</xsl:when>
										<xsl:otherwise>
                  <xsl:if test="count(errors) > 0">
												<div style="color: Red; text-align: left;">
                    <xsl:choose>
                      <xsl:when test="/syncReport/isSummary = 'true'">
                        <div>There are <xsl:value-of select="count(errors)"/> errors</div>
                      </xsl:when>
                      <xsl:otherwise>
                        <details>
                          <summary>There are
                            <xsl:value-of select="count(errors)"/> errors
                          </summary>
                          <div>
                            <ol>
                              <xsl:for-each select="errors">
                                <li>
                                  <xsl:value-of select="."/>
                                </li>
                              </xsl:for-each>
                            </ol>
                          </div>
                        </details>
                      </xsl:otherwise>
                    </xsl:choose>
                    </div>
                  </xsl:if>
                  <xsl:if test="count(warnings) > 0">
												<div style="color: GoldenRod; text-align: left;">
                      <xsl:choose>
                        <xsl:when test="/syncReport/isSummary = 'true'">
                          <div>There are <xsl:value-of select="count(warnings)"/> warnings</div>
                        </xsl:when>
                        <xsl:otherwise>
                          <details>
                            <summary>There are
                              <xsl:value-of select="count(warnings)"/> warnings
                            </summary>
                            <div>
                              <ol>
                                <xsl:for-each select="warnings">
                                  <li>
                                    <xsl:value-of select="."/>
                                  </li>
                                </xsl:for-each>
                              </ol>
                            </div>
                          </details>
                        </xsl:otherwise>
                      </xsl:choose>
                    </div>
                  </xsl:if>
                  <xsl:if test="count(imagesNotFound) > 0">
												<div style="color: Navy; text-align: left;">
                            <xsl:choose>
                                <xsl:when test="/syncReport/isSummary = 'true'">
                                    <div>There are <xsl:value-of select="count(imagesNotFound)"/> images not found</div>
                                </xsl:when>
                                <xsl:otherwise>
                                    <details>
                                        <summary>There are
                                            <xsl:value-of select="count(imagesNotFound)"/> expense images not found
                                        </summary>
                                        <div>
                                            <ol>
                                                <xsl:for-each select="imagesNotFound">
                                                    <li>
                                                        <xsl:value-of select="."/>
                                                    </li>
                                                </xsl:for-each>
                                            </ol>
                                        </div>
                                    </details>
                                </xsl:otherwise>
                            </xsl:choose>
                        </div>
                    </xsl:if>
										</xsl:otherwise>
									</xsl:choose>
                </td>
              </tr>
            </xsl:for-each>
          </tbody>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
