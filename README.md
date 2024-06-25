Alke Wallet
Alke Wallet es una aplicación de billetera digital diseñada para facilitar la gestión de dinero y
transacciones tanto para usuarios individuales como para terceros. Permite a los usuarios
crear cuentas asociadas a correos electrónicos y usernames únicos, asegurando la
integridad de la información y evitando duplicaciones. Los usuarios pueden depositar, retirar
y transferir dinero, así como consultar un historial detallado de todas las transacciones
realizadas en su cuenta, con la capacidad de filtrar por tipo de transacción.

Funcionalidades principales

● Registro de cuentas: Los usuarios pueden crear cuentas asociadas a un correo
electrónico y username únicos, validados para evitar duplicaciones en la base de
datos.

● Depósito y retiro de dinero: Los usuarios pueden depositar dinero en sus cuentas
y retirarlo cuando lo necesiten.

● Transferencias:
○ Transferencias entre usuarios registrados en la aplicación no conlleva
recargos adicionales.
○ Transferencias a terceros no registrados en la aplicación tienen un recargo
adicional de 300 pesos.
○ Las transferencias entre usuarios registrados se reflejan inmediatamente en
la cuenta destino.

● Historial de transacciones: Los usuarios pueden revisar un historial completo de
todas las transacciones realizadas en su cuenta, con la capacidad de filtrar por tipo
de transacción (depósito, retiro, transferencia interna, transferencia a terceros, etc.).

● Estadísticas financieras:
○ Los usuarios pueden consultar el saldo total de su cuenta.
○ Visualización de las ganancias y gastos acumulados en la cuenta.
○ Conversión del saldo a dólares basado en el tipo de cambio actual.

● Seguridad:
○ Spring Security: Utilizamos Spring Security para la autenticación y
autorización de usuarios, asegurando que solo usuarios autorizados puedan
acceder a sus cuentas y realizar operaciones.
○ Encriptación de contraseñas: Las contraseñas de los usuarios se
almacenan en la base de datos de manera segura mediante encriptación,
utilizando técnicas robustas para proteger la información sensible.
○ Ocultar saldo: Los usuarios tienen la opción de ocultar el saldo visible en la
página principal por razones de seguridad.

Tecnologías utilizadas
● Java 21
● Spring Boot 3.3.1
● Spring Security
● Mysql
