# Alke Wallet

**Alke Wallet** es una aplicación de billetera digital diseñada para facilitar la gestión de dinero y transacciones tanto para usuarios individuales como para terceros.

## Funcionalidades Principales

- **Registro de cuentas**: Crea cuentas asociadas a correos electrónicos y usernames únicos, validados para evitar duplicaciones.
- **Depósito y retiro de dinero**: Permite a los usuarios depositar y retirar dinero de sus cuentas.
- **Transferencias**:
  - Entre usuarios registrados sin recargos.
  - A terceros no registrados con un recargo adicional de 300 pesos.
  - Las transferencias entre usuarios registrados se reflejan inmediatamente.
- **Historial de transacciones**: Consulta el historial completo y filtra por tipo de transacción (depósito, retiro, transferencia interna, transferencia a terceros, etc.).
- **Estadísticas financieras**:
  - Consulta del saldo total.
  - Visualización de ganancias y gastos acumulados.
  - Conversión del saldo a dólares según el tipo de cambio actual.
- **Seguridad**:
  - **Spring Security**: Autenticación y autorización de usuarios.
  - **Encriptación de contraseñas**: Almacenamiento seguro de contraseñas.
  - Opción de ocultar saldo visible en la página principal.

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.3.1**
- **Spring Security**
- **MySQL**

