package com.bc.util;

public class CisMatcherUtil {

   public static int getCisCount(String searchStr, String text) {
      int count = 0;

      if (searchStr.length() == 0) {
         return 0;
      }

      String pattern = createPattern(searchStr);
      for (int i = 0; i < text.length() - searchStr.length() + 1; i++) {
         if (text.substring(i, i + searchStr.length()).matches(pattern)) {
            count++;
         }
      }
      return count;
   }

   private static String createPattern(String searchStr) {
	  String newStr = "";
	  for (int i = 0; i < searchStr.length(); i++)
	  {
		  switch(searchStr.charAt(i))
		  {
		  	case 'A': case 'a':
		  		newStr += 'A';
		  		break;
		  	case 'T': case 't':
		  		newStr += 'T';
		  		break;
		  	case 'C': case 'c':
		  		newStr += 'C';
		  		break;
		  	case 'G': case 'g':
		  		newStr += 'G';
		  		break;
		  	case 'U': case 'u':
		  		newStr += 'U';
		  		break;
		  	case 'R': case 'r':
		  		newStr += "[AG]";
		  		break;
		  	case 'Y': case 'y':
		  		newStr += "[CTU]";
		  		break;
		  	case 'M': case 'm':
		  		newStr += "[CA]";
		  		break;
		  	case 'K': case 'k':
		  		newStr += "[TUG]";
		  		break;
		  	case 'W': case 'w':
		  		newStr += "[TUA]";
		  		break;
		  	case 'S': case 's':
		  		newStr += "[CG]";
		  		break;
		  	case 'B': case 'b':
		  		newStr += "[CTUG]";
		  		break;
		  	case 'D': case 'd':
		  		newStr += "[ATUG]";
		  		break;
		  	case 'H': case 'h':
		  		newStr += "[ATUC]";
		  		break;
		  	case 'V': case 'v':
		  		newStr += "[ACG]";
		  		break;
		  	case 'N': case 'n':
		  		newStr += "[ACGTU]";
		  		break;
		  	default:
		  		//Throw an exception
		  		break;
		  }
	  }
      return newStr;
   }
}
