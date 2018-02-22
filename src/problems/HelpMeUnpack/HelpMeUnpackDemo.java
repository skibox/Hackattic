package problems.HelpMeUnpack;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;

import utils.Common;

public class HelpMeUnpackDemo {
	public static final String PROBLEM_URL 	= "help_me_unpack/problem?access_token=942335cd269f9e0d";
	public static final String SOLUTION_URL = "help_me_unpack/solve?access_token=942335cd269f9e0d";
	
	public static String trimInput(String input) {
		return new String(input.substring(11, input.length() - 2));
	}
	
	public static byte[] decodeInput(String input) {
		return Base64.getDecoder().decode(input);
	}
	
	public static long getUnsignedInt(int x) {
		return x & 0x00000000FFFFFFFFL;
	}

	public static void main(String[] args) throws Exception {
		String input = Common.httpGET(PROBLEM_URL);
		System.out.println(input);
		input = trimInput(input);
		byte[] decoded = decodeInput(input);
		
		ArrayList<Byte> decodeToList = new ArrayList<>();
		for (int i = 0; i < decoded.length; i++) {
			decodeToList.add(decoded[i]);
		}
		
		byte[] temp = new byte[8];
		int intPayload = 0;
		long unsignedIntPayload = 0;
		short shortPayload = 0;
		float floatPayload = 0;
		double doubleLEPayload = 0.0;
		double doubleBEPayload = 0.0;
		
		System.out.println(decodeToList.toString());
		
		for (int i = 0; i < 6; i++) {
			switch (i) {
			case 0: // int
				for (int j = 0; j < 4; j++) {
					temp[j] = decodeToList.get(j);
				}
				intPayload = ByteBuffer.wrap(temp).getInt();
				
				for(int j = 0; j < 4; j++) {
					decodeToList.remove(0);
				}
				
				break;
			case 1: // uint
				for (int j = 0; j < 4; j++) {
					temp[j] = decodeToList.get(j);
				}
				unsignedIntPayload = getUnsignedInt(ByteBuffer.wrap(temp).getInt());
				
				for(int j = 0; j < 4; j++) {
					decodeToList.remove(0);
				}
				
				break;
			case 2: // short
				for (int j = 0; j < 2; j++) {
					temp[j] = decodeToList.get(1 - j);
				}
				shortPayload = ByteBuffer.wrap(temp).getShort();
				
				for(int j = 0; j < 4; j++) {
					decodeToList.remove(0);
				}
				
				break;
			case 3: // float
				for (int j = 0; j < 4; j++) {
					temp[j] = decodeToList.get(j);
				}
				floatPayload = ByteBuffer.wrap(temp).getFloat();
				
				for(int j = 0; j < 4; j++) {
					decodeToList.remove(0);
				}
				
				break;
			case 4: // double LE
				for (int j = 0; j < 8; j++) {
					temp[j] = decodeToList.get(j);
				}
				doubleLEPayload = ByteBuffer.wrap(temp).getDouble();
				
				for(int j = 0; j < 8; j++) {
					decodeToList.remove(0);
				}
				
				break;
			case 5: // double BE
				
				for (int j = 0; j < 8; j++) {
					temp[j] = decodeToList.get(7 - j);
				}
				doubleBEPayload = ByteBuffer.wrap(temp).getDouble();
				
				for(int j = 0; j < 8; j++) {
					decodeToList.remove(0);
				}
				
				break;
			default:
				break;
			}
		}
		
		shortPayload = (short) shortPayload;
		StringBuilder output = new StringBuilder();
		output
		.append("{")
		.append("\"int\": \"").append(intPayload).append("\"")
		.append(",\"uint\": \"").append(unsignedIntPayload).append("\"")
		.append(",\"short\": \"").append(shortPayload).append("\"")
		.append(",\"float\": \"").append(floatPayload).append("\"")
		.append(",\"double\": \"").append(doubleLEPayload).append("\"")
		.append(",\"big_endian_double\": \"").append(doubleBEPayload).append("\"")
		.append("}");
		
		Common.httpPOST(SOLUTION_URL, output.toString());
	}
}


//public static byte[] get4Bytes(byte[] bytes, int varOfChoose) {
//	byte[] output = new byte[4];
//	int startingIndex;
//	int insideCounter = 0;
//			
//	if(varOfChoose < 3) startingIndex = (varOfChoose - 1) * 4;
//	else 				startingIndex = ((varOfChoose - 1) * 4) + 2;
//	
//	for (int i = startingIndex; i < (varOfChoose * 4 ); i++) {
//		output[insideCounter] = bytes[i];
//		insideCounter++;
//	}
//	
//	return output;
//}
