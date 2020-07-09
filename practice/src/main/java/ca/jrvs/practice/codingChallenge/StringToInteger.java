package ca.jrvs.practice.codingChallenge;

/**
 * tricker URL: https://www.notion.so/String-to-Integer-atoi-33dd625b220b42c99107c86f443aa2fa
 */

public class StringToInteger {

  /**
   *
   * @param str
   * @return
   */
 public int atoiWithAPI(String str){
   str=str.trim();
   if(str.length()==0){
     return 0;
   }
   int i=0;
   if(str.charAt(0)==43||str.charAt(0)==45||Character.isDigit(str.charAt(0))){
     for(i=1;i<str.length();i++){
       if(!(str.charAt(i)>=48&&str.charAt(i)<=57)){
         break;
       }
     }
     str=str.substring(0,i);
     try{
       if(str.length()==1&&(!Character.isDigit(str.charAt(0)))){
         return 0;
       }else{
         return Integer.parseInt(str);
       }

     }catch(NumberFormatException e){
       return  0;
     }
   }
   return 0;
 }

  public int atoiWithoutAPI(String str){
   if(str.length()==0){
     return 0;
   }
   int start=0;
   int result=0;
   boolean negative = false;
   char[] strArray = str.toCharArray();
   int i=0;
    while (i < strArray.length && strArray[i] == ' ') {
      i++;
    }
    try{
      if(i<strArray.length&&(strArray[i]==43||strArray[i]==45||Character.isDigit(strArray[i]))){
        start=i;
        for(i=i+1;i<strArray.length;i++){
          if(!Character.isDigit(strArray[i]))break;
        }
        if(strArray[start]== '-') {
          negative = true;
          start++;
        }else if(strArray[start]== '+'){
          negative=false;
          start++;
        }
        int mutip =0;
        for(int j=start;j<=i;i--){
          result+=strArray[i]*Math.pow(mutip++,10);
        }
        if(negative==true){
          result=-1*result;
        }
      }
    }catch(NullPointerException e){
      return 0;
    }
    return result;
  }
}
