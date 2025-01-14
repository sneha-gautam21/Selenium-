package billing;

public enum EnumClass {
	
	    DL60, PL60, LT30, JCK60, P30 , GL60;

	    public static boolean isValidClassName(String className) {
	        try {
	            // Try to match the class name with an enum value
	        	EnumClass.valueOf(className);
	            return true;
	        } catch (IllegalArgumentException e) {
	            // If exception occurs, it means the class name is invalid
	            return false;
	        }
	    }
	}


