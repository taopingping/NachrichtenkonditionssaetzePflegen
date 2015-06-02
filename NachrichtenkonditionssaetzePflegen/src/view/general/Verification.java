package view.general;



public class Verification {

	public static boolean verifyPasswort(char[] cs){
		if(cs.length <= 0){
			//no password
			return false;
		}
		return true;
	}

	public static boolean verifyUserName(String text) {
		if(text.isEmpty()){
			//no user name
			return false;
		}
		return true;
	}

	public static boolean verifyMandant(String mandant) {
		if(mandant.length()<3)
			//number without leading zero
			return false;
		try{
			int mandantInt = Integer.parseInt(mandant);			
			if(mandantInt<0||mandantInt>100)
				return false;
			else 
				return true;
		}
		catch(NumberFormatException nfe){
			//it not no number
			return false;
		}
	}

}
