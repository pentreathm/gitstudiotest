package com.vatit.blaze.esb.transformer;

import com.vatit.blaze.esb.utils.FileMover;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

/**
 * Moves, and optionally renames, a file without changing the payload.
 */
public class FileMoverTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String currentPath = message.getInvocationProperty("currentDirectory");
		String currentFileName = message.getInvocationProperty("currentFilename");
		String newPath = message.getInvocationProperty("newDirectory");
		String newFileName = message.getInvocationProperty("newFilename");
		FileMover.moveFile(currentFileName, currentPath, (newFileName != null)? newFileName : currentFileName, newPath);
		return message.getPayload();
	}
}
