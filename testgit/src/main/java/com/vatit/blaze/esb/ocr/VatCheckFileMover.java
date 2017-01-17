package com.vatit.blaze.esb.ocr;

import com.vatit.blaze.dto.xpenditureOcr.VatCheckGetResultsResponseOcrProcess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VatCheckFileMover {

	@Value("${blaze.xpenditureOcr.vatChecking.root.folder}")
	private String rootFolderName;
	@Value("${blaze.xpenditureOcr.sent.folder}")
	private String sentFolderName;
	@Value("${blaze.xpenditureOcr.badImage.folder}")
	private String badImageFolderName;
	@Value("${blaze.xpenditureOcr.hasVat.folder}")
	private String hasVatFolderName;
	@Value("${blaze.xpenditureOcr.noVat.folder}")
	private String noVatFolderName;

	public String determineDestinationFolderName(VatCheckGetResultsResponseOcrProcess ocrProcess) {
		String destinationFolderName;
		if (ocrProcess.getImageIsBadQuality()) {
			destinationFolderName = getBadImageFolderName();
		} else if (ocrProcess.getInvoice().getHasVat()) {
			destinationFolderName = getHasVatFolderName();
		} else {
			destinationFolderName = getNoVatFolderName();
		}
		return destinationFolderName;
	}

	public String getRootFolderName() {
		return rootFolderName;
	}

	public void setRootFolderName(String rootFolderName) {
		this.rootFolderName = rootFolderName;
	}

	public String getSentFolderName() {
		return sentFolderName;
	}

	public void setSentFolderName(String sentFolderName) {
		this.sentFolderName = sentFolderName;
	}

	public String getBadImageFolderName() {
		return badImageFolderName;
	}

	public void setBadImageFolderName(String badImageFolderName) {
		this.badImageFolderName = badImageFolderName;
	}

	public String getHasVatFolderName() {
		return hasVatFolderName;
	}

	public void setHasVatFolderName(String hasVatFolderName) {
		this.hasVatFolderName = hasVatFolderName;
	}

	public String getNoVatFolderName() {
		return noVatFolderName;
	}

	public void setNoVatFolderName(String noVatFolderName) {
		this.noVatFolderName = noVatFolderName;
	}
}
