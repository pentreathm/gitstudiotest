package com.vatit.blaze.esb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SyncReport {
	@XmlElement(name = "connection")
	public List<SyncReportConnection> connections;
	@XmlElement
	public int numberOfConnections;
	public Boolean isSummary;

	public SyncReport() {
		connections = new ArrayList<>();
	}

	public SyncReport(int numberOfConnections) {
		this();
		this.numberOfConnections = numberOfConnections;
	}
}
