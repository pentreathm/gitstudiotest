package com.vatit.blaze.esb.ocr;

import com.vatit.blaze.dto.xpenditureOcr.VatCheckGetResultsResponseOcrProcess;
import com.vatit.blaze.esb.utils.FileMover;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class FileMoverComponent implements Callable {

	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();
		VatCheckGetResultsResponseOcrProcess ocrProcess = (VatCheckGetResultsResponseOcrProcess) message.getPayload();
		VatCheckFileMover vatCheckFileMover = eventContext.getMuleContext().getRegistry().lookupObject(VatCheckFileMover.class);

		Collection<String> allClientCodes = retrieveClientCodesFromFolderStructure(vatCheckFileMover.getRootFolderName());
		File matchingFile = FileMover.findMatchingFile(vatCheckFileMover.getRootFolderName(), vatCheckFileMover.getSentFolderName(), allClientCodes, ocrProcess.getExternalProcessId());
		message.setInvocationProperty("fileToMove", matchingFile);

		String destinationFolderName = vatCheckFileMover.determineDestinationFolderName(ocrProcess);
		message.setInvocationProperty("destinationFolder", destinationFolderName);

		String clientFolderPath = determineClientFolderPath(matchingFile, vatCheckFileMover.getSentFolderName());
		String destinationPath = clientFolderPath + destinationFolderName;
		FileMover.moveFile(matchingFile, destinationPath);
		return message.getPayload();
	}

	private Collection<String> retrieveClientCodesFromFolderStructure(String rootPath) {
		Collection<String> clientCodes = new ArrayList<>();
		File rootFolder = new File(rootPath);
		File[] clientFolders = rootFolder.listFiles();
		for (File clientFolder : clientFolders) {
			if (clientFolder.isDirectory()) {
				clientCodes.add(clientFolder.getName());
			}
		}
		return clientCodes;
	}

	private String determineClientFolderPath(File clientFile, String relativeFolderName) {
		String clientFilePath = clientFile.getParent();
		if (clientFilePath.endsWith(relativeFolderName)) {
			return clientFilePath.substring(0, clientFilePath.length() - relativeFolderName.length());
		}
		return clientFilePath;
	}
}
