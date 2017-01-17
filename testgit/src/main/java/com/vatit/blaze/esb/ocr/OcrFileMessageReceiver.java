package com.vatit.blaze.esb.ocr;

import org.apache.commons.lang.StringUtils;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.Connector;
import org.mule.transport.file.FileMessageReceiver;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;

public class OcrFileMessageReceiver extends FileMessageReceiver {

	private String readFolderName;
	private int recursionDepth = 1;

	public OcrFileMessageReceiver(Connector connector, FlowConstruct flowConstruct, InboundEndpoint endpoint,
	                              String readDir, String moveDir, String moveToPattern, long frequency) throws CreateException {
		super(connector, flowConstruct, endpoint, readDir, moveDir, moveToPattern, frequency);
		setReadFolderName(beautifyFolderName(readDir));
	}

	private String beautifyFolderName(String folderName) {
		StringBuilder mutableFolderName = new StringBuilder(folderName);
		if (folderName.startsWith("/"))
			mutableFolderName.deleteCharAt(0);
		if (folderName.endsWith("/"))
			mutableFolderName.deleteCharAt(mutableFolderName.length() - 1);
		String trimmedFolderName = mutableFolderName.toString();
		String beautifiedFolderName = trimmedFolderName.replaceAll("/", Matcher.quoteReplacement("\\"));
		return beautifiedFolderName;
	}

	@Override
	protected void basicListFiles(File currentDirectory, List<File> discoveredFiles) {
		String currentFolderName = currentDirectory.getAbsolutePath();
		if (currentFolderName.length() > getReadFolderName().length()) {
			String relativeFolderName = currentFolderName.substring(getReadFolderName().length());
			int folderDepth = StringUtils.countMatches(relativeFolderName, "\\");
			if (getRecursionDepth() >= folderDepth)
				super.basicListFiles(currentDirectory, discoveredFiles);
		} else {
			super.basicListFiles(currentDirectory, discoveredFiles);
		}
	}

	private String getReadFolderName() {
		return readFolderName;
	}

	private void setReadFolderName(String readFolderName) {
		this.readFolderName = readFolderName;
	}

	private int getRecursionDepth() {
		return recursionDepth;
	}

	public void setRecursionDepth(int recursionDepth) {
		this.recursionDepth = recursionDepth;
	}
}