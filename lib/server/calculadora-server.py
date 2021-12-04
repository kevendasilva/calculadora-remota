# 
# Definição dos comportamentos, do processo servidor - Calculadora remota
# 

from dotenv import load_dotenv
import os
import socket

load_dotenv()

# Informações do servidor
host = os.getenv('HOST')
port = int(os.getenv('PORT'))
# Configurando o socket
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind((host, port))

print("\n")

while (True):
    print("Aguardando nova mensagem...")

    try:
        # Recebendo a mensagem
        operacao, address = sock.recvfrom(1024)
        
        print("\n === Nova mensagem recebida ===")
        print(f'Mensagem enviada do endereço: {address}')
        print(f'Mensagem recebida: {operacao.decode("utf-8")}')
        
        # Determinando a resposta
        resposta = str(eval(operacao))
        print("Resposta: ", resposta)

        sock.sendto(bytes(resposta, 'utf-8'), address)
        print(" === Resposta enviada === \n")
    except:
        print("Um erro foi identificado.")
        sock.sendto(bytes("Um erro foi identificado. Por favor, verifique a mensagem enviada e tente novamente",
                         'utf-8'), address)
