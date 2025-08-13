import mysql.connector
import hashlib


def create_user(username, password, role):
    try:
        connection = mysql.connector.connect(
            host="localhost",
            user="root",
            password="diana",
            database="utilizator"
        )
        cursor = connection.cursor()

        cursor.execute("SELECT * FROM users WHERE username = %s", (username,))
        if cursor.fetchone() is not None:
            print("Utilizatorul exista deja.")
            return

        hashed_password = hashlib.md5(password.encode()).hexdigest()

        cursor.execute(
            "INSERT INTO users (username, password, role) VALUES (%s, %s, %s)",
            (username, hashed_password, role)
        )
        connection.commit()
        print("Utilizator adaugat cu succes!")
    except mysql.connector.Error as err:
        print(f"Eroare la baza de date: {err}")
    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()


if __name__ == "__main__":
    username = input("Introduceti username-ul: ")
    password = input("Introduceti parola: ")
    role = input("Introduceti rolul (student, profesor): ")

    create_user(username, password, role)