# Proyecto Demo de Mockito

Este es un proyecto básico que demuestra el uso de **Mockito** para testing en Java sin Spring Framework.

## Estructura del Proyecto

```
mockito-project/
├── src/
│   ├── main/java/com/example/
│   │   ├── User.java          # Modelo de datos
│   │   ├── UserDAO.java       # Interfaz de acceso a datos
│   │   └── UserService.java   # Lógica de negocio
│   └── test/java/com/example/
│       └── UserServiceTest.java # Tests con Mockito
├── pom.xml                    # Configuración Maven
└── README.md
```

## Funcionalidades Implementadas

### Clases Principales:
- **User**: Modelo básico con id, nombre, email y edad
- **UserDAO**: Interfaz que simula operaciones de base de datos
- **UserService**: Servicio con lógica de negocio que depende de UserDAO

### Tests con Mockito:
- **@Mock**: Para crear mocks del UserDAO
- **@InjectMocks**: Para inyectar mocks en UserService
- **when().thenReturn()**: Para simular comportamientos
- **verify()**: Para verificar interacciones
- **times()**: Para verificar número de llamadas
- **never()**: Para verificar que no se llamó un método
- **any()**: Para argumentos genéricos

## Cómo Ejecutar

### Prerrequisitos:
- Java 17 o superior
- Maven 3.6 o superior

### Comandos:
```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Compilar y ejecutar tests
mvn clean test
```

## Características de Mockito Demostradas

1. **Mocking básico**: Crear mocks de dependencias
2. **Stubbing**: Definir comportamientos con `when().thenReturn()`
3. **Verificación**: Comprobar interacciones con `verify()`
4. **Manejo de excepciones**: Tests que verifican lanzamiento de excepciones
5. **Validación de argumentos**: Uso de `any()`, `anyLong()`, etc.
6. **Control de invocaciones**: `times()`, `never()`

Este proyecto muestra cómo usar Mockito para aislar la unidad bajo test (UserService) de sus dependencias (UserDAO), permitiendo tests rápidos y confiables.