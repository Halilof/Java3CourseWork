/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.xml.internal.ws.util.StringUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eltix
 */
   class JThread extends Thread
    {
        int Key1;
        int Key2;
        int Key3;
        DatagramSocket socket;
        InetAddress address;
        String TextToEncode;
        int[] rotor1 = { 0, 38  , 36  , 57  , 61  , 50  , 45  , 41  , 39  , 52  , 34  , 35  , 46  , 47  , 51  , 60  ,
		59  , 48  , 56  , 42  , 55  , 49  , 37  , 40  , 58  , 43  , 33  , 44  , 53  , 62  , 54  , 32  , 31  ,
		26  , 10  , 11  , 2  , 22  , 1  , 8  , 23  , 7  , 19  , 25  , 27  , 6  , 12  , 13  , 17  , 21  , 5  ,
		14  , 9  , 28  , 30  , 20  , 18  , 3  , 24  , 16  , 15  , 4  , 29 };
	int[] rotor2 = { 0, 59  , 35  , 40  , 60  , 32  , 56  , 38  , 42  , 50  , 62  , 47  , 36  , 55  , 57  , 52  ,
		34  , 54  , 53  , 58  , 41  , 39  , 37  , 46  , 33  , 44  , 45  , 49  , 43  , 48  , 61  , 51  , 5  ,
		24  , 16  , 2  , 12  , 22  , 7  , 21  , 3  , 20  , 8  , 28  , 25  , 26  , 23  , 11  , 29  , 27  , 9  ,
		31  , 15  , 18  , 17  , 13  , 6  , 14  , 19  , 1  , 4  , 30  , 10 };
	int[] rotor3 = { 0, 45 , 41 , 57 , 58 , 38 , 32 , 60 , 52 , 40 , 35 , 49 , 43 , 48 , 51 , 62 , 34 , 55 ,
		61 , 59 , 50 , 37 , 53 , 36 , 39 , 42 , 56 , 47 , 54 , 33 , 46 , 44 , 6 , 29 , 16 , 10 , 23 , 21 , 5 ,
		24 , 9 , 2 , 25 , 12 , 31 , 1 , 30 , 27 , 13 , 11 , 20 , 14 , 8 , 22 , 28 , 17 , 26 , 3 , 4 , 19 , 7 , 18 , 15 };
	int[] reflector = { 0, 56 , 34 , 38 , 41 , 50 , 58 , 42 , 51 , 36 , 46 , 48 , 40 , 39 , 31 , 47 , 55 , 37 ,
		59 , 33 , 52 , 60 , 32 , 44 , 54 , 53 , 49 , 45 , 43 , 35 , 57 , 14 , 22 , 19 , 2 , 29 , 9 , 17 , 3 , 
		13 , 12 , 4 , 7 , 28 , 23 , 27 , 10 , 15 , 11 , 26 , 5 , 8 , 20 , 25 , 24 , 16 , 1 , 30 , 6 , 18 , 21 , 62 , 61 };
        
        char[] abc = new char[]{ 0, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
		's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'а', 'б', 'в', 'г', 'д', 'е', 'Є', 'ж', 'з', 'и', 'й', 'к', 'л',
		'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ' , 'ъ', 'ы' , 'ь' , 'э' , 'ю', '¤', ' ' , '.' ,',' };
        
        JThread(String name, int _a, int _b, int _c, DatagramSocket _socket, InetAddress _address,String text)
        {
            super(name);
            Key1 = _a;
            Key2 = _b;
            Key3 = _c;
            TextToEncode = text;
            socket = _socket;
            address = _address; 
        }
        
        public void run()
        {
               int razmr = 62;
            String message = TextToEncode;
           char[] TextToEncodeArray = TextToEncode.toCharArray();
           char[] TextToOutput = new char[TextToEncodeArray.length];    
           Arrays.fill(TextToOutput,'~');
           int marker;
           for (int i = 0; i < TextToEncodeArray.length; i++)
		{
			if (Key1 < razmr-1)
				Key1 = Key1 + 1;
			else
			{
				Key1 = 0;
				if (Key2 < razmr-1)
					Key2 = Key2 + 1;
				else
				{
					Key2 = 0;
					if (Key3 < razmr-1)
						Key3 = Key3 + 1;
					else
					{
						Key3 = 0;
					}
				}
			}
                        
                        
                        for (int j = 1; j != razmr && TextToOutput[i] == '~'; j++)
			{
				if (TextToEncodeArray[i] == abc[j])
				{
					int pmarker;
					marker = j + Key1;
						if (marker > razmr)
							marker = marker - razmr;
					marker = rotor1[marker]+Key2-Key1;
						if (marker > razmr)
							marker = marker - razmr;
						if (marker < 1)
							marker = marker + razmr;
					marker = rotor2[marker] + Key3-Key2;
						if (marker > razmr)
							marker = marker - razmr;
						if (marker < 1)
							marker = marker + razmr;
					marker = rotor3[marker] - Key3;
						if (marker > razmr)
							marker = marker - razmr;
						if (marker < 1)
							marker = marker + razmr;
					marker = reflector[marker]; 	
					pmarker = marker;
					if (pmarker + Key3 > razmr)
						marker = marker - razmr;
					if (pmarker + Key3 < 1)
						marker = marker + razmr;
					marker = rotor3[marker + Key3];
						if (marker > razmr)
							marker = marker - razmr;
					pmarker = marker;
					if (pmarker + Key2-Key3 > razmr)
						marker = marker - razmr;
					if (pmarker + Key2 - Key3 < 1)
						marker = marker + razmr;
					marker = rotor2[marker + Key2-Key3];
						if (marker > razmr)
							marker = marker - razmr;
					pmarker = marker;
					if (pmarker + Key1 - Key2 > razmr)
						marker = marker - razmr;
					if (pmarker + Key1 - Key2 < 1)
						marker = marker + razmr;
					marker = rotor1[marker + Key1-Key2];
						if (marker > razmr)
							marker = marker - razmr;
					marker = marker - Key1;
						if (marker < 0)
							marker = marker + 62;
					TextToOutput[i] = abc[marker];
				}
				if (j == (razmr-1) && TextToEncodeArray[i] != abc[j])
					TextToOutput[i] = TextToEncodeArray[i];
			}                        
                }
           String newMessage = String.valueOf(TextToOutput);
            byte[]  buf = newMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 26);
            try {
                socket.send(packet);
            } catch (IOException ex) {
                Logger.getLogger(JThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException, InterruptedException {
         DatagramSocket socket = new DatagramSocket(17);
          DatagramSocket socketSend = new DatagramSocket();
          int Key1,Key2,Key3;
          
          InetAddress address = InetAddress.getByName("localhost");
          while(true){
           byte[] buffer = new byte[255];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);
            if(request.getLength() != 0){
                String Message =  new String(request.getData(), 0, request.getLength());
                String arr[] = Message.split(" ");
                
                int MessageLendth = Integer.valueOf(arr[0]);
                Key1 = Integer.valueOf(arr[1]);
                Key2 = Integer.valueOf(arr[2]);
                Key3 = Integer.valueOf(arr[3]);
                
                byte[] TextBuffer = new byte[MessageLendth];
                DatagramPacket EncodeReques = new DatagramPacket(TextBuffer, TextBuffer.length);
             socket.receive(EncodeReques);
             String TextToEncode =  new String(EncodeReques.getData(), 0, EncodeReques.getLength());

                JThread RunEncode = new JThread("RunEncode", Key1, Key2 , Key3 , socketSend, address, TextToEncode);
                RunEncode.start();
            
            }
        }
}
}