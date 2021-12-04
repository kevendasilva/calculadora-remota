from dotenv import load_dotenv
import os
import socket

load_dotenv()

i = 0
host = os.getenv('HOST')
port = int(os.getenv('PORT'))
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind((host, port))

while (True):
    i += 1
    print("Aguardando mensagem...")


    try:
        operacao, address = sock.recvfrom(1024)
        print("Mensagem recebida: ", operacao.decode("utf-8"))
        resposta = str(eval(operacao))
        print("Resposta: ", resposta)

        sock.sendto(bytes(resposta, 'utf-8'), address)
    except:
        print("Um erro foi identificado.")
        sock.sendto(bytes("Um erro foi identificado. Por favor, verifique a mensagem enviada e tente novamente",
                         'utf-8'), address)
