package com.vatit.blaze.esb.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;

/**
 * It finds and moves files.
 */
public class FileMover {

	/**
	 * Looks for a single file whose name starts with <code>fileNameStartsWith</code> in the set of folders defined by
	 * <code>rootPath</code> + "\\" + <code>personalisedFolderName</code> + <code>relativePath</code>. Once a matching file
	 * is found, the search stops without looking at the remaining <code>personalisedFolderNames</code>.
	 *
	 * @param rootPath
	 * @param relativePath
	 * @param personalisedFolderNames
	 * @param fileNameStartsWith
	 * @return the matching file
	 * @throws RuntimeException if a matching file is not found, or if more than one matching file is found for the same <code>personalisedFolderName</code>
	 */
	public static File findMatchingFile(String rootPath, String relativePath, Collection<String> personalisedFolderNames, final String fileNameStartsWith) {
		File[] matchingFiles = null;
		for (String personalisedFolderName : personalisedFolderNames) {
			File rootFolder = new File(rootPath + "\\" + personalisedFolderName + relativePath);
			matchingFiles = rootFolder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.startsWith(fileNameStartsWith);
				}
			});
			if (matchingFiles != null && matchingFiles.length > 0) {
				break;
			}
		}

		if (matchingFiles == null)
			throw new RuntimeException("Error while looking for files starting with " + fileNameStartsWith);
		else if (matchingFiles.length == 0)
			throw new RuntimeException("Could not find a file starting with " + fileNameStartsWith);
		else if (matchingFiles.length > 1)
			throw new RuntimeException(String.format("Found more than one (%s) file starting with " + fileNameStartsWith, matchingFiles.length));

		File matchingFile = matchingFiles[0];
		return matchingFile;
	}

	/**
	 * Moves <code>sourceFileName</code> from <code>sourcePath</code> to <code>destinationPath</code>.
	 * If <code>destinationFileName</code> is specified, the file is renamed to this name as well. If any of the folders
	 * in <code>destinationPath</code> don't exist, they are created in the process.
	 *
	 * @param sourceFileName         File to move
	 * @param sourcePath             Folder path where the file is currently located
	 * @param destinationFileName    If not <code>null</code>, the new name of the file; otherwise the current name will be kept
	 * @param destinationPath        Folder path where the file must be moved to
	 */
	public static void moveFile(String sourceFileName, String sourcePath, String destinationFileName, String destinationPath) {
		File sourceFile = new File(sourcePath + "\\" + sourceFileName);
		File destinationFile = new File(destinationPath + "\\" + destinationFileName);
		destinationFile.getParentFile().mkdirs();
		if (!sourceFile.renameTo(destinationFile))
			throw new RuntimeException(String.format("Error while moving file %s in folder %s to file %s in folder %s", sourceFileName, sourcePath, destinationFileName, destinationPath));
	}

	/**
	 * Moves <code>fileToMove</code> to <code>destinationPath</code>. If any of the folders in <code>destinationPath</code>
	 * don't exist, they are created in the process.
	 *
	 * @param fileToMove         File to move
	 * @param destinationPath    The folder path to move the file to
	 */
	public static void moveFile(File fileToMove, String destinationPath) {
		File movedFile = new File(destinationPath + "\\" + fileToMove.getName());
		movedFile.getParentFile().mkdirs();
		if (!fileToMove.renameTo(movedFile))
			throw new RuntimeException(String.format("Error while moving file %s to folder %s", fileToMove, destinationPath));
	}
}
