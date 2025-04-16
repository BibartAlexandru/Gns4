package networking.other;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Address can be of the format 0000.0000.0000 or 00:00:00:00:00:00
 * Will convert all letters to uppercase
 */
public class MAC {
	
	private String address;

	public String getAddress() {
		return address;
	}
	
	/**
	 * Throws exception if format is not correct
	 * @throws Exception 
	 */
	public void setAddress(String address) throws Exception {
		Pattern pattern = Pattern.compile("(^[a-f0-9]{4}\\.[a-f0-9]{4}\\.[a-f0-9]{4}$)|(^[a-f0-9]{2}\\:[a-f0-9]{2}\\:[a-f0-9]{2}\\:[a-f0-9]{2}\\:[a-f0-9]{2}\\:[a-f0-9]{2})$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(address);
		
		if(!matcher.matches())
			throw new Exception("The string " + address + " is not a valid MAC address.");
		String format1 = matcher.group(1);
		String format2 = matcher.group(2);
		if(format1 != null)
			this.address = format1.toUpperCase();
		else
			this.address = format2.toUpperCase();
	}
	
	/**
	 * Converts from 00:00:00:00:00:00 to 0000.0000.0000 if not already.
	 */
	private String convertToFormat1(String format2MAC) {
		Pattern format2 = Pattern.compile("^([a-f0-9]{2})\\:([a-f0-9]{2})\\:([a-f0-9]{2})\\:([a-f0-9]{2})\\:([a-f0-9]{2})\\:([a-f0-9]{2})$");
		Matcher m = format2.matcher(format2MAC);
		if(m.find()) 
			return m.group(1) + m.group(2) + "." + m.group(3) + m.group(4) + "." + m.group(5) + m.group(6);
		return format2MAC;
	}
	
	/**
	 * Transforms the address to 0000.0000.0000 format before returning the bytes of 
	 * the ASCII encoded MAC address string.
	 */
	public ArrayList<Byte> encode(){
		String format1Addr = convertToFormat1(this.address);
		byte[] bytes = format1Addr.getBytes(StandardCharsets.US_ASCII);
		ArrayList<Byte> res = new ArrayList<Byte>();
		for(byte b: bytes)
			res.add(b);
		
		return res;
	}
	
	public MAC(String address) throws Exception {
		setAddress(address);
	}
	
	public static MAC decode(ArrayList<Byte> bytes) throws Exception{
		if(bytes.size() != 14)
			throw new Exception(bytes + "has invalid number of bytes: " + bytes.size());
		String address = bytes.stream()
				.map(b -> Character.toString((char)(int)b))
				.collect(Collectors.joining());
		System.out.println(address);
		return new MAC(address);
	}
	
	public static void main(String[] args) throws Exception {
		MAC m1 = new MAC("0412.0Ab0.0d30");
		System.out.println(MAC.decode(m1.encode()));
	}
}
