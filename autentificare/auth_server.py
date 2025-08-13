from concurrent import futures
import uuid
import jwt
import mysql.connector
import time
import grpc
import auth_service_pb2_grpc
import auth_service_pb2
import hashlib

secret_key = "secret"

blacklist={}

class AuthenticateServicer(auth_service_pb2_grpc.AuthServiceServicer):
    def Authenticate(self, request, context):
        username = request.username
        password = request.password

        connection = mysql.connector.connect(
            host="localhost",
            user="root",
            password="diana",
            database="utilizator"
        )
        cursor = connection.cursor()
        cursor.execute("SELECT * FROM users WHERE username=%s", (username,))
        result = cursor.fetchone()

        if result is None:
            return auth_service_pb2.AuthResponse(JWS="")

        hashed_password= result[2]
        role=result[3]

        if hashlib.md5(password.encode()).hexdigest() != hashed_password:
            return auth_service_pb2.AuthResponse(JWS="")

        payload = {
            "iss": "http://localhost:50051",
            "sub": str(result[0]),
            "exp": int(time.time()) + 3600,
            "jti": str(uuid.uuid4()),
            "role": role,
            "username": username
        }

        token = jwt.encode(payload, secret_key, algorithm="HS256")
        print(f"Token generated for user {username}: {token}")
        return auth_service_pb2.AuthResponse(JWS=token)
    
    def Validate(self, request,context):
        token=request.JWS
        try:
            if token in blacklist:
                return auth_service_pb2.ValidateResponse(message="Token invalid: este in blacklist")
            
            payload=jwt.decode(token, secret_key, algorithms=["HS256"], options={"verify_exp":False})

            exp_timestamp= payload.get("exp")
            if exp_timestamp and exp_timestamp < time.time():
                blacklist[token]="expirat"
                return auth_service_pb2.ValidateResponse(message="Token expirat si invalidat")               
            return auth_service_pb2.ValidateResponse(message=f"valid {payload.get("role")}:{payload.get("sub")}")
        except jwt.ExpiredSignatureError:
            blacklist[token]="expirat"
            return auth_service_pb2.ValidateResponse(message="Token expirat si invalidat")
        except jwt.InvalidTokenError as e:
            print(f"Invalid token error: {e}")
            return auth_service_pb2.ValidateResponse(message="Token invalid")


    def Invalidate(self, request, context):
        token=request.JWS
        blacklist[token]="invalidat"
        return auth_service_pb2.InvalidateResponse(message="Token invalidat cu succes")


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    auth_service_pb2_grpc.add_AuthServiceServicer_to_server(AuthenticateServicer(), server)
    server.add_insecure_port("[::]:50051")
    print("Server gRPC ruleaza pe portul 50051...")
    server.start()
    server.wait_for_termination()


if __name__ == "__main__":
    serve()