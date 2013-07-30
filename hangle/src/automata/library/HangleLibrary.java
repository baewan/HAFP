/**
 * you can put a one sentence description of your library 
 *
 * (c) 2010
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author		BaeWan
 * @modified	2013.07.30
 * @version		0.1 v
 */

package automata.library;

import java.io.UnsupportedEncodingException;
import processing.core.*;

/**
 * ���� ���̺귯���� �ѱۿ��丶Ÿ �ڹ� ���̺귯���� ���μ��� ���̺귯�� ���ۿ����� ���� ����������ϴ�.
 * 
 */
enum CodeType{chosung, jungsung, jongsung}

public class HangleLibrary {
	
	// myParent is a reference to the parent sketch
	PApplet myParent;
	
	
	public final static String VERSION = "0.1.1";
	

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 * 
	 * @example Hello
	 * @param theParent
	 */
	public HangleLibrary(PApplet theParent) {
		myParent = theParent;
		welcome();
	}
	
	
	private void welcome() {
		System.out.println("hangleAutomata, 0.1.1 by Baewan http://www.baewan.com");
	}
	
	/**
	 * ���̺귯����
	 * 
	 * @return String
	 */
	public String hangleAuto(String value) {
		String result = engToKor(value);
		return result;
	}

	public static String version() {
		return VERSION;
	}
 
 /** 
   * ��� �ѱ۷�... �ٲ��ִ� �޼ҵ��... 
   */ 
 public String engToKor(String eng){
  StringBuffer sb = new StringBuffer();
  
  int initialCode = 0, medialCode = 0, finalCode = 0;
  int tempMedialCode, tempFinalCode;
  
  for(int i = 0; i < eng.length(); i++){
   
     initialCode = getCode(CodeType.chosung, eng.substring(i, i+1));
     if(i !=0 ){
      if(eng.charAt(i-1) == ' '){
       sb.append(" ");
      }
     }
     i++;
     
     tempMedialCode = getDoubleMedial(i, eng);
    
     if(tempMedialCode != -1){       
      medialCode = tempMedialCode;
  
      i += 2;
     }else{
      medialCode = getSingleMedial(i, eng);   // �� �ڷ� �̷���� �߼��ڵ� ����
      i++;
     }
     
     
     // �����ڵ� ����
     tempFinalCode = getDoubleFinal(i, eng);    // �� �ڷ� �̷���� �����ڵ� ����    
     if(tempFinalCode != -1){
      finalCode = tempFinalCode;
      // �� ������ �߼� ���ڿ� ���� �ڵ带 �����Ѵ�. 
      tempMedialCode = getSingleMedial(i+2, eng);
      if( tempMedialCode != -1 ){      // �ڵ� ���� ���� ��� 
       finalCode = getSingleFinal(i, eng);   // ���� �ڵ� ���� �����Ѵ�.
               }else{
                i++;
               }
     }else{            // �ڵ� ���� ���� ��� ,
      tempMedialCode = getSingleMedial(i+1, eng);  // �� ������ �߼� ���ڿ� ���� �ڵ� ����. 
      if(tempMedialCode != -1){      // �� ������ �߼� ���ڰ� ������ ���,     
       finalCode = 0;        // ���� ���ڴ� ����.
       i--;     
      }else{
       finalCode = getSingleFinal(i, eng);   // ���� ���� ����
       if( finalCode == -1 )
        finalCode = 0;
      }
      
     }
    // ������ �ʼ� ���� �ڵ�, �߼� ���� �ڵ�, ���� ���� �ڵ带 ���� �� ��ȯ�Ͽ� ��Ʈ�����ۿ� �ѱ�
    
    sb.append((char)(0xAC00 + (initialCode + medialCode + finalCode)));
 
  }  
  return sb.toString();
 }
 
 /** 
   * �ش� ���ڿ� �� �ڵ带 �����Ѵ�. 
   * @param type �ʼ� : chosung, �߼� : jungsung, ���� : jongsung ���� 
   * @param char �ش� ���� 
   */ 
 private int getCode(CodeType type, String c){
  // �ʼ�
  String init = "rRseEfaqQtTdwWczxvg";
  // �߼�
  String[] mid = {"k","o","i","O","j","p","u","P","h","hk", "ho","hl","y","n","nj","np", "nl", "b", "m", "ml", "l"};
  // ����
  String[] fin = {"r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a", "q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g"};
  
  switch(type){
   case chosung :
    int index = init.indexOf(c);
    if( index != -1 ){
     return index * 21 * 28;
    }
    break;
   case jungsung :
    
    for(int i = 0; i < mid.length; i++){
     if(mid[i].equals(c)){
       return i * 28;
      }
    }
    break;
   case jongsung :
    for(int i = 0; i < fin.length; i++){
     if(fin[i].equals(c)){
       return i + 1;
      }
    }
    break;
   default:
    //System.out.println("�߸�� Ÿ�� �Դϴ�");
  }
  //System.out.println("�������˻��� �;���");
  return -1;
 }
 
 // �� �ڷ� �� �߼����� �����Ѵ�
 // �ε����� ����ٸ� -1�� ����
 private int getSingleMedial(int i, String eng){
  if((i+1) <= eng.length()){
   return getCode(CodeType.jungsung, eng.substring(i, i+1));
  }else{
   return -1;
  }
 }
 
 // �� �ڷ� �� �߼��� üũ�ϰ�, �ִٸ� ���� �����Ѵ�.
 // ������ ���ϰ��� -1
 private int getDoubleMedial(int i, String eng){
  int result;
  if((i+2) > eng.length()){
   return -1;
  }else{
   result = getCode(CodeType.jungsung, eng.substring(i, i+2));
   if(result != -1){
    return result;
   }else{
    return -1;
   }
  }
 }
 
 // �� �ڷε� �������� �����Ѵ�
 // �ε����� ����ٸ� -1�� ����
 private int getSingleFinal(int i, String eng){
  if((i+1) <= eng.length()){
   return getCode(CodeType.jongsung, eng.substring(i, i+1));
  }else{
   return -1;
  }
 }
 
 // �� �ڷε� ������ üũ�ϰ�, �ִٸ� ���� �����Ѵ�.
 // ������ ���ϰ��� -1
 private int getDoubleFinal(int i, String eng){
  if((i+2) > eng.length()){
   return -1;
  }else{
   return getCode(CodeType.jongsung, eng.substring(i, i+2));
  }
 }
}