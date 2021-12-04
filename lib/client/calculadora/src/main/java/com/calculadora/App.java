package com.calculadora;

/*
 * Definição dos comportamentos, do processo cliente - Calculadora remota
 */

import io.github.cdimascio.dotenv.Dotenv;
import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
        // Socket
		DatagramSocket clientSocket = null;
		Scanner dataInput = new Scanner(System.in);
		
    // Carregando variáveis de ambiente
    Dotenv dotenv = Dotenv.load();

    // Operandos
		float operando1 = 0;
		float operando2 = 0;
    // Operador
		String operador = "";

    // Opção do usuário
    Boolean option = true;
		
    System.out.println("\n-=- Seja bem-vindo a Calculadora Remota -=-\n");

    do {
      try {
        // Recebendo as informações do usuário
        System.out.print("Digite o primeiro número: ");
        operando1 = dataInput.nextFloat();
        dataInput.nextLine();
        
        System.out.print("Digite o segundo número: ");
        operando2 = dataInput.nextFloat();
        dataInput.nextLine();
        
        System.out.print("Digite a operação desejada (+, -, *, /): ");
        operador = dataInput.nextLine();
        
        // Mensagem
        byte[] messageToSend = (operando1 + operador + operando2).getBytes();
        
        // Informações do servidor
        int port = Integer.parseInt(dotenv.get("PORT"));
        String host = dotenv.get("HOST");
        // Verificando o IP do servidor
        InetAddress serverIP = InetAddress.getByName(host);
        // Socket Cliente
        clientSocket = new DatagramSocket();

        // Pacote a ser enviado
        DatagramPacket packetToSend = new DatagramPacket(messageToSend, messageToSend.length, 
                serverIP, port);
        // Enviando a mensagem
        clientSocket.send(packetToSend);
        System.out.println("\n Mensagem enviada!\n");
        

        // Resposta
        byte[] respostaBytes = new byte[1024];
        DatagramPacket resposta = new DatagramPacket(respostaBytes, respostaBytes.length);
        clientSocket.receive(resposta);
        String respostaString = new String(respostaBytes, 0, resposta.getLength());
        
        // Retornando a resposta
        System.out.println("> Resposta da operação: " + respostaString + "\n");
      } catch (InputMismatchException e) {
        System.out.println("Valor esperado não foi digitado corretamente. "
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

      System.out.print("Deseja continuar [S/n]? ");
      String input = dataInput.next();
      option = input.equals("S") ? true:false;

      System.out.print("\n");
    } while (option);

    System.out.println("Até a próxima :D\n");
    // Fechando o Scanner
    dataInput.close();
	}
}
