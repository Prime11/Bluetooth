import java.io.*;
import javax.bluetooth.RemoteDevice;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;


public class BTSend
{
	public static void main(String[] args) throws Exception
	{
		// Change this to the name of your receiver
		String name = "Slave11";
		
		LCD.clear();
		LCD.drawString("Connecting...", 0, 0);
		LCD.refresh();
		try
		{
			RemoteDevice receiver = Bluetooth.getKnownDevice(name);
			
			BTConnection connection = Bluetooth.connect(receiver);
			if (connection == null)
				throw new IOException("Connect fail");

			LCD.drawString("connected.", 1, 0);
			DataInputStream input = connection.openDataInputStream();
			DataOutputStream output = connection.openDataOutputStream();

			output.writeInt(42);
			output.writeInt(-42);
			output.flush();
			LCD.drawString("Sent data", 2, 0);
			
			LCD.drawString("Waiting ...", 3, 0);
			int answer = input.readInt();
			LCD.drawString("# = " + answer, 4, 0);
			
			input.close();
			output.close();
			connection.close();
			LCD.drawString("Bye ...", 5, 0);
		}
		catch(Exception ioe)
		{
			LCD.clear();
			LCD.drawString("ERROR", 0, 0);
			LCD.drawString(ioe.getMessage(), 2, 0);
			LCD.refresh();
		}
		Thread.sleep(4000);
	}
}
