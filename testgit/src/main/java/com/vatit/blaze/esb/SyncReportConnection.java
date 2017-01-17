package com.vatit.blaze.esb;

import com.vatit.blaze.dto.Connection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SyncReportConnection {
	@XmlElement
	public int reportsRetrieved = 0;
	@XmlElement
	public int imagesRetrieved = 0;
	@XmlElement
	public int faultyImages = 0;
	@XmlElement
	public int calculationsRetrieved = 0;
	@XmlElement
	public int faultyCalculations = 0;
	@XmlElement
	public int entriesRetrieved = 0;
	@XmlElement
	public int faultyInvoices = 0;
	@XmlElement
	public int groupImagesPolled = 0;
	@XmlElement
	public int groupImagesFound = 0;
	@XmlElement
	public List<String> errors;
	@XmlElement
	public List<String> warnings;
	@XmlElement
	public List<String> imagesNotFound;
	@XmlElement
	public Connection connection;

	public SyncReportConnection() {
		this(new Connection());
	}

	public SyncReportConnection(Connection connection) {
		errors = new ArrayList<>();
		warnings = new ArrayList<>();
		imagesNotFound = new ArrayList<>();
		this.connection = connection;
	}
}
