package com.calculadora;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		DatagramSocket clientSocket = null;
		Scanner dataInput = new Scanner(System.in);
		
		float operando1 = 0;
		float operando2 = 0;
		String sinal = "";
		
		try {
			clientSocket = new DatagramSocket();
			
			System.out.println("Digite o primeiro n�mero: ");
			operando1 = dataInput.nextFloat();
			dataInput = new Scanner(System.in);
			
			System.out.println("Digite o segundo n�mero: ");
			operando2 = dataInput.nextFloat();
			dataInput = new Scanner(System.in);
			
			System.out.println("Digite a opera��o: ");
			sinal = dataInput.nextLine();
			
			byte[] messageToSend = (operando1 + sinal + operando2).getBytes();
			
			InetAddress serverIP = InetAddress.getByName("localhost");
			int port = 6789;
			
			DatagramPacket packetToSend = new DatagramPacket(messageToSend, messageToSend.length, 
					serverIP, port);
			clientSocket.send(packetToSend);
			System.out.println("Mensagem enviada!");
			
			byte[] respostaBytes = new byte[1024];
			DatagramPacket resposta = new DatagramPacket(respostaBytes, respostaBytes.length);
			clientSocket.receive(resposta);
			String respostaString = new String(respostaBytes, 0, resposta.getLength());
			System.out.println("Resposta: " + respostaString);
			
			
		} catch (InputMismatchException e) {
			System.out.println("Valor esperado n�o foi digitado corretamente. "
					+ "Por favor, tente novamente.");
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (clientSocket != null) clientSocket.close();
		}
	}

}
