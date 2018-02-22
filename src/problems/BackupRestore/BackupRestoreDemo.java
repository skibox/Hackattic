package problems.BackupRestore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utils.Common;

public class BackupRestoreDemo {
	protected static final String PROBLEM_URL 	= "backup_restore/problem?access_token=942335cd269f9e0d";
	protected static final String SOLUTION_URL = "backup_restore/solve?access_token=942335cd269f9e0d";
	
	protected static String trimDump() throws Exception{
		String dump = Common.httpGET(PROBLEM_URL);
		StringBuilder output = new StringBuilder();
		output.append(dump.substring(10, dump.length() - 2));
		
		return output.toString();
	}

	protected static byte[] decompress(byte[] decodedBytes) throws Exception {
		ByteArrayInputStream byteInput = new ByteArrayInputStream(decodedBytes);
		GZIPInputStream gzin = new GZIPInputStream(byteInput);
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

		int res = 0;
		byte buffer[] = new byte[1024];
		while (res >= 0) {
			res = gzin.read(buffer, 0, buffer.length);
			if (res > 0) {
				byteOutput.write(buffer, 0, res);
			}
		}
		byte uncompressed[] = byteOutput.toByteArray();

		return uncompressed;
	}

	protected static void createDecodedTxt(String decodedOutput, String filename) throws Exception {
		String decodedFileName = filename;
		File outputFile = new File(decodedFileName);
		outputFile.createNewFile();

		FileWriter fWriter = new FileWriter(outputFile);
		fWriter.write(decodedOutput);
		fWriter.close();

	}

	protected static String[] getDecodedLines(String decodedOutput) {
		String[] decodedOutputLines;
		int lineCounter = 0;
		for (int i = 0; i < decodedOutput.length(); i++) {
			if (decodedOutput.charAt(i) == '\n')
				lineCounter++;
		}
		decodedOutputLines = new String[lineCounter];

		int charsInStringCounter = 0;
		int indexOfEndOfLine = 0;
		String temp;

		for (int i = 0; i < lineCounter; i++) {
			temp = decodedOutput.substring(charsInStringCounter);
			indexOfEndOfLine = temp.indexOf('\n');
			decodedOutputLines[i] = temp.substring(0, indexOfEndOfLine);
			charsInStringCounter += indexOfEndOfLine + 1;
		}

		return decodedOutputLines;

	}

	protected static String getSSN(String decoded) {
		String decodedTrimmed = decoded.substring(4);
		char[] SSN = new char[11];
		boolean end = false;
		int i = 0;
		while (!end) {
			if (Character.isDigit(decodedTrimmed.charAt(i))) {
				for (int j = 0; j < SSN.length; j++) {
					SSN[j] = decodedTrimmed.charAt(i + j);
				}
				end = true;
			} else
				i++;
		}

		return String.valueOf(SSN);
	}

	protected static Vector<String> getAliveLines(String[] decodedOutputLines) {
		String aliveLine = "alive";
		Vector<String> SSNlist = new Vector<String>();

		for (int i = 0; i < decodedOutputLines.length; i++) {
			if (decodedOutputLines[i].contains(aliveLine)) {
				SSNlist.add(getSSN(decodedOutputLines[i]));
			}
		}

		return SSNlist;
	}
	
	protected static String generateJSON(Vector<String> ssn_list) throws Exception {
		Gson gsonOut = new GsonBuilder().create();

		return gsonOut.toJson(new problems.BackupRestore.OutputJSON(ssn_list));
	}
	
	public static void main(String[] args) throws Exception {

		String encodedInput = trimDump();
		byte[] decodedBytes = Base64.getDecoder().decode(encodedInput);
		byte[] decodedString = decompress(decodedBytes);
		
		String decodedOutput = new String(decodedString, "UTF-8");
		createDecodedTxt(encodedInput, "encoded.txt");
		createDecodedTxt(decodedOutput, "decoded.txt");

		String[] decodedOutputLines = getDecodedLines(decodedOutput);

		Vector<String> SSNlist = getAliveLines(decodedOutputLines);
		String payload = generateJSON(SSNlist);
		
		Common.httpPOST(SOLUTION_URL, payload);
	}
}
