package gameFiles.Battle;
public class ReadString {
	public static String readString(char filterKey, char[] readString) {
		char[] chr = readString;
		int tempCount = 1;
		String outText = "";
		
		for (int i = 0; i < chr.length; i++) {
			if (chr[i] == filterKey) {
				i++;
				outText = outText + chr[i];
				
				while (chr[i + tempCount] != filterKey) {
					//System.out.println(outText);
					//System.out.println(chr[i + tempCount]);
					outText = outText + chr[i + tempCount];
					tempCount++;
				}
				
				i += tempCount;
			}
		}
		
		return outText;
	}
}
