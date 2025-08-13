import grpc
import auth_service_pb2
import auth_service_pb2_grpc

def authenticate(username, password):
    channel = grpc.insecure_channel('localhost:50051')
    stub = auth_service_pb2_grpc.AuthServiceStub(channel)

    request = auth_service_pb2.AuthRequest(username=username, password=password)

    try:
        response = stub.Authenticate(request)
        if response.JWS:
            print("Autentificare reusita!")
            print(f"Token JWS: {response.JWS}")
        else:
            print("Autentificare esuata.")
    except grpc.RpcError as e:
        print(f"Eroare la comunicarea cu serverul: {e}")

def validate_token(token):
    channel=grpc.insecure_channel('localhost:50051')
    stub=auth_service_pb2_grpc.AuthServiceStub(channel)

    request= auth_service_pb2.ValidateRequest(JWS=token)

    try:
        response=stub.Validate(request)
        print(f"{response.message}")
    except grpc.RpcError as e:
        print(f"Eroare la server: {e}")

def invalidate_token(token):
    channel=grpc.insecure_channel('localhost:50051')
    stub= auth_service_pb2_grpc.AuthServiceStub(channel)

    request=auth_service_pb2.InvalidateRequest(JWS=token)

    try:
        response= stub.Invalidate(request)
        print(f"Raspuns invalidare:{response.message}")
    except grpc.RpcError as e:
        print(f"Eroare la server: {e}")

if __name__ == '__main__':
    print("1. Autentificare")
    print("2. Validare token")
    print("3. Invalidare token")

    option = input("Selectati o optiune (1/2/3): ")
    if option == "1":
        username = input("Introduceti username-ul: ")
        password = input("Introduceti parola: ")
        token=authenticate(username, password)
        if token:
            print(f"Token generat:{token}")
    elif option=="2":
        token = input("Introduceti token-ul pentru validare: ")
        validate_token(token)
    elif option=="3":
        token = input("Introduceti token-ul pentru invalidare: ")
        invalidate_token(token)
    else: 
        print("Optiune invalida!")
